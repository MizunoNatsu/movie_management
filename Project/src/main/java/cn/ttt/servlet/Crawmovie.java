package cn.ttt.servlet;

import cn.ttt.dao.mdao.Moviedao;
import cn.ttt.entity.Movie;
import cn.ttt.util.PythonHttpUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/crawlMovie")
public class Crawmovie extends HttpServlet {
    private Moviedao movieDao = new Moviedao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用Python接口获取电影数据
        List<Movie> crawlMovies = PythonHttpUtil.getCrawlMovieData();
        // 批量插入数据库
        int addNum = movieDao.batchAddMovie(crawlMovies);
        // 重定向回首页
        response.sendRedirect(request.getContextPath() + "/index");
    }
}