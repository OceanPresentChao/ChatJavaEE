package art.oceanpresent.www.chatjavaee.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import art.oceanpresent.www.chatjavaee.service.UserService;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("username");
        String password = request.getParameter("password");
//        try{
            response.getWriter().write(UserService.register("name", "password").toString());
//        }catch (Exception e){
//            response.getWriter().write(e.getMessage());
//        }

    }
}
