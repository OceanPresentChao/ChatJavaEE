package art.oceanpresent.www.chatjavaee.ejb;

import art.oceanpresent.www.chatjavaee.entity.Message;
import art.oceanpresent.www.chatjavaee.jpa.MessageJPA;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@LocalBean
public class MessageBean {
    private static MessageJPA messageJPA = new MessageJPA();

    public List<Message> getMessageByChat(Integer chatId) {
        return messageJPA.findByChat(chatId);
    }
}
