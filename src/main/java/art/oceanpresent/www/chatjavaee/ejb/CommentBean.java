package art.oceanpresent.www.chatjavaee.ejb;

import art.oceanpresent.www.chatjavaee.entity.Comment;
import art.oceanpresent.www.chatjavaee.jpa.CommentJPA;
import art.oceanpresent.www.chatjavaee.service.CommentService;
import art.oceanpresent.www.chatjavaee.util.CustomException;
import art.oceanpresent.www.chatjavaee.util.CustomResponse;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.jms.*;
import java.lang.IllegalStateException;

@MessageDriven
public class CommentBean implements MessageListener {

    @Resource
    private ConnectionFactory connectionFactory;

    @Resource(name = "AnswerQueue")
    private Queue answerQueue;

    private CommentJPA commentJPA = new CommentJPA();

    public void onMessage(Message message) {
        try {
            Session session = getSession();
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                text = "MDB:I have received your message: " + text;
                TextMessage msg = session.createTextMessage(text);
                respond(session, msg);
            } else if (message instanceof MapMessage) {
                MapMessage mapMessage = (MapMessage) message;
                Integer userId = mapMessage.getInt("userId");
                Integer chatId = mapMessage.getInt("chatId");
                String content = mapMessage.getString("content");

                Comment cmt = new Comment();
                cmt.setContent(content);
                cmt = CommentService.createComment(userId, chatId, cmt);

                TextMessage msg = session.createTextMessage();
                msg.setText(CustomResponse.convert2Object(cmt).toString());
                respond(session, msg);
            }
        } catch (JMSException e) {
            throw new IllegalStateException(e);
        }
    }

    private void respond(Session session, Message message) {
        try {
            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(answerQueue);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Tell the producer to send the message
            producer.send(message);

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    private Session getSession() throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            return session;
        } finally {
            // Clean up
            if (session != null) session.close();
            if (connection != null) connection.close();
        }
    }
}
