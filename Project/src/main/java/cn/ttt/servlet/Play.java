package cn.ttt.servlet;

import cn.ttt.dao.mdao.Moviedao;
import cn.ttt.entity.Movie;
import cn.ttt.entity.user.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/PlayServlet")
public class Play extends HttpServlet {
    private Moviedao movieDao = new Moviedao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取登录会话
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        // 未登录直接跳登录页
        if (loginUser == null) {
            response.sendRedirect("login.jsp?msg=请先登录后才可观看影片");
            return;
        }

        // 获取电影ID，查询影片数据
        Integer movieId = Integer.parseInt(request.getParameter("id"));
        Movie movie = movieDao.selectById(movieId);

        // 把电影对象传给播放页面
        request.setAttribute("movie", movie);
        // 转发到你的播放页面 html/play.jsp
        request.getRequestDispatcher("html/play.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}