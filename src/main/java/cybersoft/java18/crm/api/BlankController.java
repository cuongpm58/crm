package cybersoft.java18.crm.api;

import cybersoft.java18.crm.util.JspUtil;
import cybersoft.java18.crm.util.UrlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "error", urlPatterns = {
        UrlUtil.URL_BLANK,
        UrlUtil.URL_ERROR
})
public class BlankController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtil.URL_BLANK:
                req.getRequestDispatcher(JspUtil.JSP_BLANK).forward(req, resp);
                break;
            case UrlUtil.URL_ERROR:
                req.getRequestDispatcher(JspUtil.JSP_ERROR).forward(req, resp);
                break;
        }
    }
}
