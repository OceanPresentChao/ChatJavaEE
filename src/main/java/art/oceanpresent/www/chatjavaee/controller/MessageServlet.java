package art.oceanpresent.www.chatjavaee.controller;

import art.oceanpresent.www.chatjavaee.entity.Message;
import art.oceanpresent.www.chatjavaee.util.CustomException;
import art.oceanpresent.www.chatjavaee.util.CustomResponse;
import com.google.gson.JsonObject;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MessageServlet", value = "/message")
public class MessageServlet extends HttpServlet {
    @EJB
    private art.oceanpresent.www.chatjavaee.ejb.MessageBean messageBean;

    @EJB
    private art.oceanpresent.www.chatjavaee.ejb.TalkBean talkBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Integer chatId = Integer.parseInt(request.getParameter("chat_id"));
        ServletOutputStream out = response.getOutputStream();
        JsonObject res = new JsonObject();
        try {
            List<Message> list = messageBean.getMessageByChat(chatId);
            res.add("list", CustomResponse.convert2Array(list));
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Integer chatId = Integer.parseInt(request.getParameter("chat_id"));
        String content = request.getParameter("content");
        ServletOutputStream out = response.getOutputStream();
        JsonObject res = new JsonObject();
        try {
            talkBean.makeInteraction(content, chatId);
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        }
    }
}
