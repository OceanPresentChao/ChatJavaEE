package art.oceanpresent.www.chatjavaee.controller;

import art.oceanpresent.www.chatjavaee.service.UserService;
import art.oceanpresent.www.chatjavaee.util.CustomException;
import art.oceanpresent.www.chatjavaee.util.CustomResponse;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        ServletOutputStream out = response.getOutputStream();
        JsonObject res = new JsonObject();
        try {
            UserService.login(name, password);
            out.print(CustomResponse.success(res).toString());
        } catch (CustomException e) {
            out.print(CustomResponse.error(e.getMessage()).toString());
        }
    }
}
