package art.oceanpresent.www.chatjavaee.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomResponse {

    public static Gson getCustomGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .setLenient()
                .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                    @Override
                    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }
                })
                .serializeNulls()
                .create();
    }

    public static JsonObject convert2Object(Object data) {
        Gson gson = getCustomGson();
        return gson.toJsonTree(data).getAsJsonObject();
    }

    public static JsonArray convert2Array(Object data) {
        Gson gson = getCustomGson();
        return gson.toJsonTree(data).getAsJsonArray();
    }

    public static JsonObject parseAsObject(String str) {
        return getCustomGson().fromJson(str, JsonObject.class);
    }

    public static JsonArray parseAsArray(String str) {
        return getCustomGson().fromJson(str, JsonArray.class);
    }

    public static JsonObject response(Object data, String message, int code) {
        Gson gson = getCustomGson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", code);
        jsonObject.addProperty("message", message);
        jsonObject.add("data", gson.toJsonTree(data));
        return jsonObject;
    }

    public static JsonObject success(Object data, String message) {
        return response(data, message, 200);
    }

    public static JsonObject success(Object data) {
        return success(data, "success");
    }

    public static JsonObject error(Object data, String message) {
        return response(data, message, 400);
    }

    public static JsonObject error(Object data) {
        return error(data, "error");
    }
}
