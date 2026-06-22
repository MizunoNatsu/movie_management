package cn.ttt.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",urlPatterns = {"/index","/crawlMovie"})
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String path = req.getServletPath();
        // 判断是否登录
        if (path.equals("/Play") || path.equals("/html/play.jsp")){
            if(session.getAttribute("loginUser") == null){
                resp.sendRedirect(req.getContextPath()+"/login.jsp?msg=请先登录后才可观看影片");
                return;
            }
        }
        if(session.getAttribute("loginUser") == null){
            // 未登录跳登录页
            resp.sendRedirect(req.getContextPath()+"/login");
            return;
        }
        // 已登录放行
        chain.doFilter(request,response);
    }
}