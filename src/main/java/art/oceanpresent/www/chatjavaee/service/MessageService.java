package art.oceanpresent.www.chatjavaee.service;

import com.google.gson.JsonObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/message")
public class MessageService {
    @GET
    public JsonObject getMsg() {
        JsonObject obj = new JsonObject();
        obj.addProperty("message", "Hello World !! - Jersey 2");
        return obj;
    }
}