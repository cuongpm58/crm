package cybersoft.java18.crm.api;

import cybersoft.java18.crm.model.UserModel;
import cybersoft.java18.crm.util.JspUtil;
import cybersoft.java18.crm.util.PrintUtil;
import cybersoft.java18.crm.util.UrlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "home", urlPatterns = {
        UrlUtil.URL_HOME
})
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspUtil.JSP_HOME).forward(req, resp);
//        UserModel user = (UserModel) req.getSession().getAttribute("currentUser");
//        user.getFullname()
//        PrintUtil.printJsonFromObject(resp, user);
    }


}
