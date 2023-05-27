package art.oceanpresent.www.chatjavaee.controller;

import art.oceanpresent.www.chatjavaee.entity.Chat;
import art.oceanpresent.www.chatjavaee.entity.Comment;
import art.oceanpresent.www.chatjavaee.service.ChatService;
import art.oceanpresent.www.chatjavaee.service.CommentService;
import art.oceanpresent.www.chatjavaee.util.CustomException;
import art.oceanpresent.www.chatjavaee.util.CustomResponse;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CommentListServlet", value = "/comment/list")
public class CommentListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Integer userid = Integer.parseInt(request.getParameter("user_id"));
        ServletOutputStream out = response.getOutputStream();
        JsonObject res = new JsonObject();
        try {
            List<Comment> list = CommentService.getCommentList(userid);
            res.add("list", CustomResponse.convert2Array(list));
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        }
    }
}
