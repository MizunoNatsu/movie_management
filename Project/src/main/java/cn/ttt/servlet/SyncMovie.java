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

@WebServlet("/syncMovie")
public class SyncMovie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 调用工具类请求Python爬虫接口
        List<Movie> crawlData = PythonHttpUtil.getCrawlMovieData();
        int addCount = 0;
        if(crawlData != null && crawlData.size() > 0){
            Moviedao dao = new Moviedao();
            addCount = dao.batchAddMovie(crawlData);
        }
        // 存入提示信息到session，重定向后页面还能显示
        request.getSession().setAttribute("msg", "同步完成，新增"+addCount+"条电影");
        // 重定向回首页
        response.sendRedirect("index");
    }
}