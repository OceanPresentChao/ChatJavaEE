package art.oceanpresent.www.chatjavaee.ejb;

import art.oceanpresent.www.chatjavaee.entity.Chat;
import art.oceanpresent.www.chatjavaee.entity.Message;
import art.oceanpresent.www.chatjavaee.entity.User;
import art.oceanpresent.www.chatjavaee.jpa.ChatJPA;
import art.oceanpresent.www.chatjavaee.jpa.MessageJPA;
import art.oceanpresent.www.chatjavaee.jpa.UserJPA;
import art.oceanpresent.www.chatjavaee.util.CustomException;
import art.oceanpresent.www.chatjavaee.util.CustomResponse;
import art.oceanpresent.www.chatjavaee.util.Tool;
import com.google.gson.JsonObject;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateful
@LocalBean
public class TalkBean {
    private ChatJPA chatJPA = new ChatJPA();
    private MessageJPA messageJPA = new MessageJPA();
    private UserJPA userJPA = new UserJPA();
    private List<Message> messageList = new java.util.ArrayList<>();

    public void makeInteraction(String message, Integer chatId) {
        Chat c = chatJPA.findById(chatId);
        if (c == null) {
            throw new CustomException("Chat not found");
        }
        User u = c.getUser();
        User r = userJPA.findById(Tool.ROBOT_ID);
        if (u == null || r == null) {
            throw new CustomException("User not found");
        }
        saveRequest(message, c, u, r);
        saveResponse(message, c, r, u);
    }

    private void saveRequest(String message, Chat chat, User from, User to) {
        Message msg = new Message();

        msg.setContent(message);
        msg.setChatId(chat.getId());
        msg.setUserFromId(from.getId());
        msg.setUserToId(to.getId());

        Message m = messageJPA.create(msg);
        messageList.add(m);
    }

    private void saveResponse(String message, Chat chat, User from, User to) {
        Message msg = new Message();

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Tool.WEB_SERVICE_URL).path("talk").queryParam("content", message);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        JsonObject obj = CustomResponse.parseAsObject(response.readEntity(String.class));
        if (obj.get("code").getAsInt() < 200 || obj.get("code").getAsInt() >= 300) {
            throw new CustomException(obj.get("message").getAsString());
        }
        msg.setContent(obj.get("data").getAsJsonObject().get("content").toString());
        msg.setChatId(chat.getId());
        msg.setUserFromId(from.getId());
        msg.setUserToId(to.getId());

        Message m = messageJPA.create(msg);
        messageList.add(m);
    }

    public void endChat() {
        messageList.clear();
    }

    public List<Message> getMessageList() {
        return messageList;
    }
}
