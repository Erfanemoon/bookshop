package erfan.codes.bookshop.general.common.global;

import com.google.protobuf.InvalidProtocolBufferException;
import erfan.codes.bookshop.general.General;
import erfan.codes.bookshop.general.common.global.config.ConfigReader;
import erfan.codes.bookshop.proto.holder.Global;
import erfan.codes.bookshop.repositories.redis_repo.IRedisSessionRepo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

@Component
public class SessionUtil {

    private static IRedisSessionRepo redisSessionRepo;

    @Autowired
    public SessionUtil(IRedisSessionRepo redisSessionRepo) {
        SessionUtil.redisSessionRepo = redisSessionRepo;
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null)
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        return null;
    }

    public static HttpServletResponse getResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null)
            return ((ServletRequestAttributes) requestAttributes).getResponse();
        return null;
    }

    public static void getSessionModelSubscriber(HttpServletRequest request) {

        getSessionId(request);
    }

    public static String getSessionId(HttpServletRequest request) {
        if (request == null)
            return null;
        String sessionId = request.getHeader("sessionId");
//        String[] sessionIds = parameterMap.get("sessionId");
//        String sessionId = "";
//        if (sessionIds != null && sessionIds.length > 0)
//            sessionId = sessionIds[0];

        if (!sessionId.isEmpty())
            return sessionId;

        return null;
    }

    public static Global.SessionModel saveUserInfo(long userId, String type) {

        Global.SessionModel.Builder sessionModelBuilder = Global.SessionModel.newBuilder();
        Date now = new Date();
        String randomID = General.generateRandomID();
        String sessionId = DigestUtils.sha1DigestAsHex(userId + randomID);
        int subscribers_login_session_valid_for_hour = ConfigReader.getConfig().getInt("Subscribers_login_session_valid_for_hour");
        Date sessionExpireDate = sessionExpireDate(now, Calendar.HOUR, subscribers_login_session_valid_for_hour);
        long sessionExpireInSeconds = (sessionExpireDate.getTime() - now.getTime()) / 1000;

        sessionModelBuilder.setSessionId(sessionId);
        if (type.equals(SessionType.Subscribers.getValue()))
            sessionModelBuilder.setSessionType(SessionType.Subscribers.getValue());
        else if (type.equals(SessionType.ADMINS.getValue()))
            sessionModelBuilder.setSessionType(SessionType.ADMINS.getValue());
        sessionModelBuilder.setRegisterDate(now.getTime());
        sessionModelBuilder.setUserId(userId);
        sessionModelBuilder.setValidUntil(sessionExpireDate.getTime());

        Global.SessionModel sessionModel = sessionModelBuilder.build();

        String redisKey = getSessionKey(sessionId);
        String sessionModelByteString = Base64.encodeBase64String(sessionModelBuilder.build().toByteArray());
        redisSessionRepo.put(redisKey, sessionModelByteString);
        redisSessionRepo.expire(redisKey, sessionExpireInSeconds);
        return sessionModel;
    }

    public static Date sessionExpireDate(Date now, int hour, int subscribers_login_session_valid_for_hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(hour, subscribers_login_session_valid_for_hour);
        return calendar.getTime();
    }

    public static void updateSessionExpireDate(Global.SessionModel sessionModel) {
        Date now = new Date();
        int subscribers_login_session_valid_for_hour = ConfigReader.getConfig().getInt("Subscribers_login_session_valid_for_hour");
        Date sessionExpireDate = SessionUtil.sessionExpireDate(now, Calendar.HOUR, subscribers_login_session_valid_for_hour);
        long sessionExpireInSeconds = (sessionExpireDate.getTime() - now.getTime()) / 1000;

        Global.SessionModel.Builder sessBuilder = Global.SessionModel.newBuilder();
        sessBuilder.setSessionId(sessionModel.getSessionId());
        sessBuilder.setSessionType(SessionType.Subscribers.getValue());
        sessBuilder.setRegisterDate(sessionModel.getRegisterDate());
        sessBuilder.setUserId(sessionModel.getUserId());
        sessBuilder.setValidUntil(sessionExpireDate.getTime());

        String redisKey = getSessionKey(sessionModel.getSessionId());
        String sessionModelByteString = Base64.encodeBase64String(sessBuilder.build().toByteArray());
        redisSessionRepo.put(redisKey, sessionModelByteString);
        redisSessionRepo.expire(redisKey, sessionExpireInSeconds);
    }

    public static Global.SessionModel getAndValidateSession(String sessionId) {

        String sessionStringData = redisSessionRepo.get(getSessionKey(sessionId));
        byte[] bytes = Base64.decodeBase64(sessionStringData);
        Global.SessionModel sessionModel = null;
        try {
            sessionModel = Global.SessionModel.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return sessionModel;
    }

    private static String getSessionKey(String sessionId) {
//        byte[] bytes = Base64.decodeBase64(sessionId);
//        try {
//            Global.SessionModel sessionModel = Global.SessionModel.parseFrom(bytes);
//            System.out.println(sessionModel.getSessionId());
//            sessionModel.getSessionId();
//        } catch (InvalidProtocolBufferException e) {
//            e.printStackTrace();
//        }
        return SessionType.Subscribers.getValue() + ":sessions:" + sessionId;
    }


}
