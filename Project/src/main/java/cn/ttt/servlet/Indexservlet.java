package cn.ttt.servlet;

import cn.ttt.dao.mdao.Moviedao;
import cn.ttt.entity.Movie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class Indexservlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 局部创建Dao对象
        Moviedao movieDao = new Moviedao();

        // 1. 获取当前页码，默认第1页
        String pageNumStr = request.getParameter("pageNum");
        int pageNum = 1;
        if (pageNumStr != null && !pageNumStr.trim().isEmpty()) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        // 每页展示10条电影
        int pageSize = 10;
        // 计算分页起始下标
        int start = (pageNum - 1) * pageSize;

        // 分页查询电影数据
        List<Movie> movieList = movieDao.getMovieByPage(start, pageSize);
        // 查询电影总条数，计算总页数
        int totalCount = movieDao.getMovieCount();
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;

        // 控制台打印日志，查看当前页查到多少电影
        System.out.println("当前页：" + pageNum + "，查询到电影数量：" + movieList.size());

        // 将分页数据传递给前端页面
        request.setAttribute("movieList", movieList);
        request.setAttribute("pageNum", pageNum);
        request.setAttribute("totalPage", totalPage);

        // 转发到首页jsp（你的index.jsp在webapp根目录）
        request.getRequestDispatcher("/html/index.jsp").forward(request, response);
    }
}