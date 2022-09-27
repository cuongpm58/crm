package cybersoft.java18.crm.filter;

import cybersoft.java18.crm.util.UrlUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse resp = (HttpServletResponse) servletResponse;
//        resp.addHeader("Access-Control-Allow-Origin", "*");
//        resp.addHeader("Access-Control-Allow-Headers", "*");
//        resp.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");
//
//        filterChain.doFilter(servletRequest,servletResponse);


        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        // process before the request get in servlet
        // filterChain.doFilter(req, resp);
        // process response from servlet
        if (isLoginUser(req) || isAuthUrl(req)) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + UrlUtil.URL_LOGIN);
        }
    }

    private boolean isAuthUrl(HttpServletRequest req) {
        var path = req.getServletPath();
        return path.startsWith(UrlUtil.URL_LOGIN);
    }

    private boolean isLoginUser(HttpServletRequest req) {
        var currentUser = req.getSession().getAttribute("currentUser");
        return currentUser != null;
    }
}
