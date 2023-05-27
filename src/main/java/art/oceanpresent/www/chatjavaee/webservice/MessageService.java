package art.oceanpresent.www.chatjavaee.webservice;

import art.oceanpresent.www.chatjavaee.util.CustomResponse;
import art.oceanpresent.www.chatjavaee.util.Tool;
import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/talk")
@Produces(MediaType.APPLICATION_JSON)
public class MessageService {

    @GET
    public JsonObject talkWithAI(@DefaultValue(" ") @QueryParam("content") String msg) {
        JsonObject robotDB = new JsonObject();
        try {
            String path = Tool.getFilePath("RobotDB.json");
            String jsonStr = Tool.readJsonFile(path);
            robotDB = CustomResponse.parseAsObject(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObject obj = new JsonObject();
        try {
            String res = "";
            if (robotDB != null && robotDB.get(msg) != null) {
                res = robotDB.get(msg).getAsString();
            } else {
                res = "I have received your message: " + msg;
            }
            obj.addProperty("content", res);
            return CustomResponse.success(obj);
        } catch (Exception e) {
            return CustomResponse.error(obj, e.getMessage());
        }
    }
}