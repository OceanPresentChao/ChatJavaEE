package art.oceanpresent.www.chatjavaee.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mysql.cj.xdevapi.JsonString;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/message")
public class JerseyService {
    @GET
    public JsonObject getMsg() {
        JsonObject obj = new JsonObject();
        obj.addProperty("message", "Hello World !! - Jersey 2");
        return obj;
    }
}