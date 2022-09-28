package cybersoft.java18.crm.api;

import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.model.TaskModel;
import cybersoft.java18.crm.model.UserModel;
import cybersoft.java18.crm.service.RoleService;
import cybersoft.java18.crm.service.TaskService;
import cybersoft.java18.crm.util.JspUtil;
import cybersoft.java18.crm.util.PrintUtil;
import cybersoft.java18.crm.util.UrlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "home", urlPatterns = {
        UrlUtil.URL_HOME
})
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = (UserModel) req.getSession().getAttribute("currentUser");
        List<RoleModel> roles = RoleService.getInstance().getAllRole();
        List<TaskModel> todoTask = TaskService.getInstance().getTaskByUserIdStatusId(user.getId(), roles.get(0).getId());
        List<TaskModel> inProgressTask = TaskService.getInstance().getTaskByUserIdStatusId(user.getId(), roles.get(1).getId());
        List<TaskModel> completedTask = TaskService.getInstance().getTaskByUserIdStatusId(user.getId(), roles.get(2).getId());
        req.setAttribute("currentUser", user);
        req.setAttribute("todoTask", todoTask);
        req.setAttribute("inProgressTask", inProgressTask);
        req.setAttribute("completedTask", completedTask);
        req.getRequestDispatcher(JspUtil.JSP_HOME).forward(req, resp);
//        UserModel user = (UserModel) req.getSession().getAttribute("currentUser");
//        user.getFullname()
//        PrintUtil.printJsonFromObject(resp, tasks);
    }


}
