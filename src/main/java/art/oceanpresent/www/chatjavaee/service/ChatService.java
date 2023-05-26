package art.oceanpresent.www.chatjavaee.service;

import art.oceanpresent.www.chatjavaee.ejb.ChatEJB;
import art.oceanpresent.www.chatjavaee.entity.Chat;
import art.oceanpresent.www.chatjavaee.entity.User;
import art.oceanpresent.www.chatjavaee.util.CustomException;

import java.util.List;

public class ChatService {
    static ChatEJB chatEJB = new ChatEJB();

    public static Chat createChat(Integer user_id, Chat chat) {
        User user = UserService.getUser(user_id);
        chat.setUser(user);
        return chatEJB.create(chat);
    }

    public static Chat updateChat(Integer id, Chat chat) {
        Chat u = getChat(id);
        chat.setId(u.getId());
        return chatEJB.update(chat);
    }

    public static Chat getChat(Integer id) {
        Chat u = chatEJB.findById(id);
        if (u == null) {
            throw new CustomException("Chat not found");
        }
        return u;
    }


    public static void deleteChat(Integer id) {
        Chat u = getChat(id);
        chatEJB.delete(u);
    }

    public static List<Chat> getChatList(Integer userId) {
        return chatEJB.findByUser(userId);
    }
}
