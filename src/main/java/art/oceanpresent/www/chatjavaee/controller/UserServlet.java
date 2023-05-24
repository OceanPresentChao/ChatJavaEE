package art.oceanpresent.www.chatjavaee.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import art.oceanpresent.www.chatjavaee.entity.User;
import art.oceanpresent.www.chatjavaee.service.UserService;
import art.oceanpresent.www.chatjavaee.util.CustomException;
import art.oceanpresent.www.chatjavaee.util.CustomResponse;
import com.google.gson.JsonObject;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        ServletOutputStream out = response.getOutputStream();
        JsonObject res = new JsonObject();
        try {
            User user = UserService.getUser(id);
            res.add("user", CustomResponse.convert2Object(user));
            out.print(CustomResponse.success(res).toString());

        } catch (CustomException e) {
            out.print(CustomResponse.error(e.getMessage()).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        ServletOutputStream out = response.getOutputStream();
        JsonObject res = new JsonObject();
        try {
            User user = UserService.register(name, password);
            res.add("user", CustomResponse.convert2Object(user));
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(e.getMessage()).toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        String password = request.getParameter("password");
        ServletOutputStream out = response.getOutputStream();
        JsonObject res = new JsonObject();
        try {
            User data = UserService.getUser(id);
            data.setPassword(password);
            User user = UserService.updateUser(id, data);
            res.add("user", CustomResponse.convert2Object(user));
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(e.getMessage()).toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        ServletOutputStream out = response.getOutputStream();
        JsonObject res = new JsonObject();
        try {
            UserService.deleteUser(id);
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(e.getMessage()).toString());
        }
    }
}
