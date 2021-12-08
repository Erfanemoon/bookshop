package erfan.codes.bookshop.general;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.protobuf.Descriptors;
import erfan.codes.bookshop.enums.Return_Status_Codes;
import erfan.codes.bookshop.general.common.global.SessionUtil;
import erfan.codes.bookshop.proto.holder.Global;
import org.hibernate.Session;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class General {

    public static List<MediaType> getAcceptHeaderMediaTypes(HttpServletRequest request) {

        if (request == null) {
            List<MediaType> list = new ArrayList<>();
            list.add(MediaType.APPLICATION_JSON);
            return list;
        }
        try {
            String accept = request.getHeader("accept");
            return MediaType.parseMediaTypes(accept);
        } catch (Exception e) {
            System.out.println("Accepted Media Headers : " + e.getMessage());
            List<MediaType> list = new ArrayList<>();
            list.add(MediaType.APPLICATION_JSON);
            return list;
        }
    }

    public static JsonObject fixRequiredFields(JsonObject jobj, Descriptors.Descriptor descs) {
        for (Descriptors.FieldDescriptor descriptor : descs.getFields()) {
            String field_name = descriptor.getName();
            String field_type = descriptor.getJavaType().name().toLowerCase();
            switch (field_type) {
                case "string": {
                    if (!jobj.has(field_name)) {
                        jobj.addProperty(field_name, "");
                    }
                }
                break;
                case "boolean":
                case "bool": {
                    if (!jobj.has(field_name)) {
                        jobj.addProperty(field_name, false);
                    }
                }
                break;
                case "int":
                case "long":
                case "double":
                case "float": {
                    if (!jobj.has(field_name)) {
                        jobj.addProperty(field_name, 0);
                    }
                }
                break;
                case "message": {
                    JsonElement jsonElement = jobj.get(field_name);
                    if (jsonElement != null) {
                        if (jsonElement.isJsonArray()) {

                            JsonArray jsonArray = jsonElement.getAsJsonArray();
                            int i = 0;
                            for (JsonElement je : jsonArray) {
                                if (je.isJsonObject()) {
                                    jsonArray.set(i, fixRequiredFields(je.getAsJsonObject(), descriptor.getMessageType()));
                                }
                                i++;
                            }
                        } else if (jsonElement.isJsonObject()) {
                            jobj.add(field_name, fixRequiredFields(jsonElement.getAsJsonObject(), descriptor.getMessageType()));
                        }
                    }

                }
            }
        }

        return jobj;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    public static String generateRandomID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void writeErrorOutput(Return_Status_Codes return_status_code) {

        writeErrorOutput(SessionUtil.getRequest(), SessionUtil.getResponse(), return_status_code);
    }

    public static void writeErrorOutput(HttpServletRequest request, HttpServletResponse response, Return_Status_Codes return_status_code) {

        Global.service_return.Builder ret = Global.service_return.newBuilder();
        ret.setStatus(return_status_code.getStatus());
        ret.setCode(0);
        ret.setMessage(return_status_code.getMessageKey());
        Output output = new Output(request, response);
        output.write(ret.build());
    }
}
