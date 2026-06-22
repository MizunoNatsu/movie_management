package cn.ttt.servlet;

import cn.ttt.dao.mdao.UserDao;
import cn.ttt.entity.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/html/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userDao.login(username,password);
        if(user != null){
            HttpSession session = request.getSession();
            session.setAttribute("loginUser",user);
            response.sendRedirect(request.getContextPath()+"/index");
        }else{
            request.setAttribute("msg","账号密码错误");
            request.getRequestDispatcher("/html/login.jsp").forward(request,response);
        }
    }
}