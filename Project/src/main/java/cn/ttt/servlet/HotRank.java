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

@WebServlet("/hotRank")
public class HotRank extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Moviedao movieDao = new Moviedao();
        // 分页参数
        String pageNumStr = request.getParameter("pageNum");
        int pageNum = 1;
        if(pageNumStr != null && !pageNumStr.trim().isEmpty()){
            pageNum = Integer.parseInt(pageNumStr);
        }
        int pageSize = 10;
        int start = (pageNum - 1) * pageSize;

        // 查询热度榜单
        List<Movie> hotList = movieDao.getHotRank(start, pageSize);
        int totalCount = movieDao.getMovieCount();
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;

        request.setAttribute("hotList", hotList);
        request.setAttribute("pageNum", pageNum);
        request.setAttribute("totalPage", totalPage);

        // 转发你的hot.jsp，注意路径
        request.getRequestDispatcher("/html/hot.jsp").forward(request,response);
    }
}