package cybersoft.java18.crm.api;

import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.model.StatusModel;
import cybersoft.java18.crm.model.TaskModel;
import cybersoft.java18.crm.model.UserModel;
import cybersoft.java18.crm.service.RoleService;
import cybersoft.java18.crm.service.StatusService;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "home", urlPatterns = {
        UrlUtil.URL_HOME
})
public class HomeController extends HttpServlet {
    List<RoleModel> roles = RoleService.getInstance().getAllRole();
    private List<StatusModel> status = StatusService.getInstance().getAllStatus();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = (UserModel) req.getSession().getAttribute("currentUser");
        setTaskInfo(req, user);
        req.getRequestDispatcher(JspUtil.JSP_HOME).forward(req, resp);
//        UserModel user = (UserModel) req.getSession().getAttribute("currentUser");
//        user.getFullname()
//        PrintUtil.printJsonFromObject(resp, tasks);
    }

    private void setTaskInfo(HttpServletRequest req, UserModel user) {
        List<TaskModel> tasks = TaskService.getInstance().getTaskByUserId(user.getId());
        int sumTask = tasks.size();

        List<Integer> listPercentTask = new ArrayList<Integer>();
        if (sumTask > 0) {
            for (StatusModel statusModel : status) {
                long count = tasks.stream()
                        .filter(task -> task.getStatusName().equals(statusModel.getName())).count();
                listPercentTask.add((int) (count * 100 / sumTask));
            }
        } else {
            for (StatusModel statusModel : status) {
                listPercentTask.add(0);
            }
        }
        req.setAttribute("listPercentTask", listPercentTask);
    }
}
