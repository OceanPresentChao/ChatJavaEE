package art.oceanpresent.www.chatjavaee.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

import art.oceanpresent.www.chatjavaee.entity.User;
import art.oceanpresent.www.chatjavaee.service.UserService;
import art.oceanpresent.www.chatjavaee.util.CustomException;
import art.oceanpresent.www.chatjavaee.util.CustomResponse;
import art.oceanpresent.www.chatjavaee.util.Tool;
import com.google.gson.JsonObject;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        PrintWriter out = response.getWriter();
        JsonObject res = new JsonObject();
        try {
            User user = UserService.getUser(id);
            res.add("user", CustomResponse.convert2Object(user));
            out.print(CustomResponse.success(res).toString());

        } catch (CustomException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String method = request.getParameter("method");
        if (method != null && method.equals("put")) {
            this.doPut(request, response);
            return;
        }
        if (method != null && method.equals("delete")) {
            this.doDelete(request, response);
            return;
        }
        String name = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        JsonObject res = new JsonObject();
        try {
            User user = UserService.register(name, password);
            res.add("user", CustomResponse.convert2Object(user));
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        PrintWriter out = response.getWriter();
        JsonObject res = new JsonObject();
        try {
            User data = UserService.getUser(id);
            if (password != null) {
                data.setPassword(Tool.getMD5(password));
            }
            if (email != null) {
                data.setEmail(email);
            }
            if (phone != null) {
                data.setPhone(phone);
            }
            if (address != null) {
                data.setAddress(address);
            }
            User user = UserService.updateUser(id, data);
            res.add("user", CustomResponse.convert2Object(user));
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
            UserService.deleteUser(id);
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(res, e.getMessage()).toString());
        }
    }
}
