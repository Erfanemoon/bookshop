package erfan.codes.bookshop.general;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import erfan.codes.bookshop.enums.IReturn_Status_Codes;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Output {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private List<MediaType> acceptedMediaTypes;
    private final String APPLICATION_X_PROTOBUF_VALUE = "application/x-protobuf";
    private final MediaType APPLICATION_X_PROTOBUF = MediaType.parseMediaType(APPLICATION_X_PROTOBUF_VALUE);
    private String requestId = "";

    public Output(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.acceptedMediaTypes = General.getAcceptHeaderMediaTypes(this.request);
    }

    public <T> void write(T t) {
        for (MediaType mediaType : this.acceptedMediaTypes) {
            if (mediaType.includes(MimeTypeUtils.APPLICATION_JSON)) {
                try {
                    writeJson(t);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (mediaType.includes(APPLICATION_X_PROTOBUF)) {
                try {
                    writeProtocolBuffer(t);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (mediaType.includes(MimeTypeUtils.APPLICATION_OCTET_STREAM)) {
                try {
                    writeOctetStream(t);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            writeJson(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> void writeOctetStream(T t) throws IOException {
        if (t != null) {
            Message message;
            if (t instanceof Message) {
                message = (Message) t;
            } else {
                message = ((Message.Builder) t).build();
            }
            byte[] testbyte = message.toByteArray();
            writeOut(testbyte, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        } else {
            writeNotFound();
            return;
        }
    }

    private <T> void writeJson(T t) throws IOException {
        if (t == null) {
            writeNotFound();
            return;
        }
        String ret = getJson(t);

        writeOut(ret);
    }

    private <T> void writeProtocolBuffer(T t) throws IOException {
        if (t == null) {
            writeNotFound();
            return;
        }

        Message message;
        if (t instanceof Message) {
            message = (Message) t;
        } else {
            message = ((Message.Builder) t).build();
        }
        byte[] testbyte = message.toByteArray();
        writeOut(testbyte, APPLICATION_X_PROTOBUF_VALUE);
    }

    private void writeOut(String out) throws IOException {
        byte[] bytes = out.getBytes("UTF-8");
        writeOut(bytes, MediaType.APPLICATION_JSON_VALUE);
    }

    private void writeOut(byte[] bytes, String mediatype) throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(mediatype);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentLength(bytes.length);
        response.setHeader("Content-Length", bytes.length + "");
        response.setHeader("requestId", this.requestId);

        OutputStream outStream = null;
        try {
            outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (outStream != null) {
                outStream.close();
            }
        }

    }

    private void writeNotFound() {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//        closeDatabase();
//        closeDatabase_app();
    }


    public <T> String getJson(T t) {
        Message message;
        if (t instanceof Message) {
            message = (Message) t;
        } else {
            message = ((Message.Builder) t).build();
        }

        JsonObject asJson = new JsonParser().parse(JsonFormat.printToString(message)).getAsJsonObject();

        if (!asJson.has("status")) {
            asJson.addProperty("status", 0);
        }


        asJson = General.fixRequiredFields(asJson, message.getDescriptorForType());
        String ret = asJson.toString();

        return ret;
    }

    public <T> T returnResponseObject(T t, IReturn_Status_Codes return_status_codes) {
        return this.returnResponseObject(t, return_status_codes.getStatus(), return_status_codes.getMessageKey());
    }

    public <T> T returnResponseObject(T t, int status, String msg) {
        com.google.protobuf.Message.Builder builder = (com.google.protobuf.Message.Builder) t;
        builder.setField(builder.getDescriptorForType().findFieldByName("status"), status);
        builder.setField(builder.getDescriptorForType().findFieldByName("message"), msg);
        return (T) builder;
    }
}


