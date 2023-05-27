package art.oceanpresent.www.chatjavaee.controller;

import art.oceanpresent.www.chatjavaee.entity.Comment;
import art.oceanpresent.www.chatjavaee.service.CommentService;
import art.oceanpresent.www.chatjavaee.util.CustomException;
import art.oceanpresent.www.chatjavaee.util.CustomResponse;
import com.google.gson.JsonObject;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CommentServlet", value = "/comment")
public class CommentServlet extends HttpServlet {
    @Resource
    private ConnectionFactory connectionFactory;

    @Resource(name = "CommentBean")
    private Queue questionQueue;

    @Resource(name = "AnswerQueue")
    private Queue answerQueue;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        PrintWriter out = response.getWriter();
        JsonObject res = new JsonObject();
        try {
            Comment cmt = CommentService.getComment(id);
            res.add("comment", CustomResponse.convert2Object(cmt));
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        String method = request.getParameter("method");
        if (method != null && method.equals("put")) {
            this.doPut(request, response);
            return;
        }
        if (method != null && method.equals("delete")) {
            this.doDelete(request, response);
            return;
        }
        JsonObject res = new JsonObject();
        PrintWriter out = response.getWriter();
        Integer userid = Integer.parseInt(request.getParameter("user_id"));
        Integer chatid = Integer.parseInt(request.getParameter("chat_id"));
        String content = request.getParameter("content");

        final Connection connection;
        try {
            connection = connectionFactory.createConnection();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

        try {
            connection.start();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

        final Session session;
        try {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            final MessageProducer questions = session.createProducer(questionQueue);
            final MessageConsumer answers = session.createConsumer(answerQueue);
            MapMessage msg = session.createMapMessage();
            msg.setInt("userId", userid);
            msg.setInt("chatId", chatid);
            msg.setString("content", content);
            questions.send(msg);
            TextMessage resMsg = (TextMessage) answers.receive(3000);
            if (resMsg != null) {
                res.add("comment", CustomResponse.parseAsObject(resMsg.getText()));
            }
            out.print(CustomResponse.success(res).toString());
        } catch (JMSException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        String content = request.getParameter("content");
        PrintWriter out = response.getWriter();
        JsonObject res = new JsonObject();
        try {
            Comment data = CommentService.getComment(id);
            data.setContent(content);
            Comment cmt = CommentService.updateComment(id, data);
            res.add("comment", CustomResponse.convert2Object(cmt));
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        PrintWriter out = response.getWriter();
        JsonObject res = new JsonObject();
        try {
            CommentService.deleteComment(id);
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        }
    }
}
