package erfan.codes.bookshop.general;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.protobuf.Descriptors;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
}
