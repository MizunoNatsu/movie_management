package cn.ttt.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",urlPatterns = {"/*"})
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String path = req.getServletPath();
        // 判断是否登录
        if (path.equals("/login") || path.equals("/Login")){
           chain.doFilter(request, response);
           return;
        }
        if (path.equals("/play")){
            if (session.getAttribute("loginUser") == null){
                resp.sendRedirect(req.getContextPath()+"/login?msg=请先登录后观看影片");
                return;
            }
        }
        // 已登录放行
        chain.doFilter(request,response);
    }
}
