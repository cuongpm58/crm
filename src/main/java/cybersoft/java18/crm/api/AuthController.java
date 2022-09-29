package cybersoft.java18.crm.api;

import cybersoft.java18.crm.model.UserModel;
import cybersoft.java18.crm.service.UserService;
import cybersoft.java18.crm.util.JspUtil;
import cybersoft.java18.crm.util.UrlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "auth", urlPatterns = {
        UrlUtil.URL_LOGIN,
        UrlUtil.URL_LOGOUT
})
public class AuthController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtil.URL_LOGIN -> req.getRequestDispatcher(JspUtil.JSP_LOGIN).forward(req, resp);
            case UrlUtil.URL_LOGOUT -> {
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + UrlUtil.URL_LOGIN);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processLogin(req, resp);
    }

    private void processLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = UserService.getInstance().login(req.getParameter("email"), req.getParameter("password"));

        if (user == null) {
            req.setAttribute("errors", "Username or password is incorrect!");
            this.doGet(req, resp);
        } else {
            req.getSession().setAttribute("currentUser", user);
            resp.sendRedirect(req.getContextPath() + UrlUtil.URL_HOME);
        }
    }
}
