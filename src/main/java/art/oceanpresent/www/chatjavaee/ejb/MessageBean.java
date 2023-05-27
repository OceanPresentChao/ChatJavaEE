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
        List<Message> list = messageJPA.findByChat(chatId);
        list.sort((p1, p2) -> p1.getCreateTime().compareTo(p2.getCreateTime()));
        return list;
    }
}
