package art.oceanpresent.www.chatjavaee.controller;

import art.oceanpresent.www.chatjavaee.entity.Chat;
import art.oceanpresent.www.chatjavaee.entity.User;
import art.oceanpresent.www.chatjavaee.service.ChatService;
import art.oceanpresent.www.chatjavaee.util.CustomException;
import art.oceanpresent.www.chatjavaee.util.CustomResponse;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ChatServlet", value = "/chat")
public class ChatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        PrintWriter out = response.getWriter();
        JsonObject res = new JsonObject();
        try {
            Chat chat = ChatService.getChat(id);
            res.add("chat", CustomResponse.convert2Object(chat));
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
        Integer userid = Integer.parseInt(request.getParameter("user_id"));
        String title = request.getParameter("title");
        PrintWriter out = response.getWriter();
        JsonObject res = new JsonObject();
        try {
            Chat el = new Chat();
            el.setTitle(title);
            Chat chat = ChatService.createChat(userid, el);
            res.add("chat", CustomResponse.convert2Object(chat));
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String status = request.getParameter("status");
        PrintWriter out = response.getWriter();
        JsonObject res = new JsonObject();
        try {
            Chat data = ChatService.getChat(id);
            if (title != null) {

                data.setTitle(title);
            }
            if (status != null) {
                data.setStatus(status);
            }
            Chat chat = ChatService.updateChat(id, data);
            res.add("chat", CustomResponse.convert2Object(chat));
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        PrintWriter out = response.getWriter();
        JsonObject res = new JsonObject();
        try {
            ChatService.deleteChat(id);
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        }
    }
}
