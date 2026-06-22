package cn.ttt.servlet;

import cn.ttt.dao.mdao.Moviedao;
import cn.ttt.entity.Movie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/detail")
public class Detail extends HttpServlet {
    private Moviedao movieDao = new Moviedao();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int movieId = Integer.parseInt(idParam);
        // 访问一次点击+1
        movieDao.addClickCount(movieId);
        Movie movie = movieDao.getMovieById(movieId);
        request.setAttribute("movie",movie);
        request.getRequestDispatcher("html/detail.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String movieName = request.getParameter("movieName");
        String type = request.getParameter("type");
        String year = request.getParameter("year");
        Double score = Double.parseDouble(request.getParameter("score"));
        String intro = request.getParameter("intro");
        // 获取B站播放链接
        String playUrl = request.getParameter("playUrl");
        String clickStr = request.getParameter("clickCount");
        String watchStr = request.getParameter("watchCount");
        System.out.println("链接："+playUrl);

        Movie movie = new Movie();
        movie.setId(Integer.parseInt(idParam));
        movie.setMovieName(movieName);
        movie.setType(type);
        movie.setYear(Integer.valueOf(year));
        movie.setScore(score);
        movie.setIntro(intro);
        movie.setPlayUrl(playUrl);
        movie.setClickCount(Integer.valueOf(request.getParameter("clickCount")));
        movie.setWatchCount(Integer.valueOf(request.getParameter("watchCount")));

        movieDao.update(movie);
        response.sendRedirect("index");
    }
}