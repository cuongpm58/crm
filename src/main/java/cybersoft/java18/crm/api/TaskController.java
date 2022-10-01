package cybersoft.java18.crm.api;

import cybersoft.java18.crm.model.JobModel;
import cybersoft.java18.crm.model.StatusModel;
import cybersoft.java18.crm.model.TaskModel;
import cybersoft.java18.crm.model.UserModel;
import cybersoft.java18.crm.service.JobService;
import cybersoft.java18.crm.service.StatusService;
import cybersoft.java18.crm.service.TaskService;
import cybersoft.java18.crm.service.UserService;
import cybersoft.java18.crm.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "task", urlPatterns = {
        UrlUtil.URL_TASK,
        UrlUtil.URL_TASK_ADD,
        UrlUtil.URL_TASK_MODIFY,
        UrlUtil.URL_TASK_DELETE
})
public class TaskController extends HttpServlet {
    private List<StatusModel> statuses = StatusService.getInstance().getAllStatus();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtil.URL_TASK_DELETE -> deleteTask(req, resp);
            case UrlUtil.URL_TASK_MODIFY -> showModifyTask(req, resp);
            case UrlUtil.URL_TASK_ADD -> showAddTask(req, resp);
            default -> showTaskTable(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtil.URL_TASK_ADD -> addNewTask(req, resp);
            case UrlUtil.URL_TASK_MODIFY -> modifyTask(req, resp);
            default -> getAllTask(req, resp);
        }
    }


    private void showAssignedTask(HttpServletRequest req, HttpServletResponse resp) {
    }

    private void showManagedTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = getUser(req);
        List<TaskModel> tasks = TaskService.getInstance().getTaskByUserId(user.getId());
        req.setAttribute("tasks", tasks);
        req.getRequestDispatcher(JspUtil.JSP_TASK).forward(req, resp);
    }

    private void showAllTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TaskModel> tasks = TaskService.getInstance().getAllTasks();
        req.setAttribute("tasks", tasks);
        req.getRequestDispatcher(JspUtil.JSP_TASK).forward(req, resp);
    }

    private void addNewTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean result = TaskService.getInstance().addTask(TaskModel.builder()
                .name(req.getParameter("name"))
                .startTime(TimeUtil.getTimeFromStr(req.getParameter("startTime")))
                .endTime(TimeUtil.getTimeFromStr(req.getParameter("endTime")))
                .userId(Integer.parseInt(req.getParameter("userId")))
                .jobId(Integer.parseInt(req.getParameter("jobId")))
                .statusId(statuses.get(0).getId())
                .build());
        resp.sendRedirect(req.getContextPath() + UrlUtil.URL_TASK);
    }

    private void getAllTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TaskModel> tasks = TaskService.getInstance().getAllTasks();
        PrintUtil.printJsonFromObject(resp, tasks);
    }

    private void deleteTask(HttpServletRequest req, HttpServletResponse resp) {
        int taskId = Integer.parseInt(req.getParameter("taskId"));
        TaskService.getInstance().deleteTaskById(taskId);
    }

    private void showAddTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = getUser(req);
        if (user.getRole().getName().equals(RoleUtil.ROLE_ADMIN)
                || user.getRole().getName().equals(RoleUtil.ROLE_MANAGER)
        ) {
            List<UserModel> users = UserService.getInstance().getUsers();
            List<JobModel> jobs = user.getRole().getName().equals(RoleUtil.ROLE_ADMIN) ?
                    JobService.getInstance().getAllJob() :
                    JobService.getInstance().getJobByUser(user.getId());
            req.setAttribute("jobs", jobs);
            req.setAttribute("users", users);
            req.getRequestDispatcher(JspUtil.JSP_TASK_ADD).forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + UrlUtil.URL_BLANK);
        }
//        req.getRequestDispatcher(JspUtil.JSP_TASK_ADD).forward(req, resp);
    }

    private void showModifyTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = getUser(req);
        TaskModel task = getTask(req);
        if ((user.getRole().getName().equals(RoleUtil.ROLE_ADMIN)
                || user.getRole().getName().equals(RoleUtil.ROLE_MANAGER))
                && task != null) {
            List<UserModel> users = UserService.getInstance().getUsers();
            List<JobModel> jobs = user.getRole().getName().equals(RoleUtil.ROLE_ADMIN) ?
                    JobService.getInstance().getAllJob() :
                    JobService.getInstance().getJobByUser(user.getId());
            req.setAttribute("jobs", jobs);
            req.setAttribute("users", users);
            req.setAttribute("task", task);
            req.setAttribute("statuses", statuses);
            req.getRequestDispatcher(JspUtil.JSP_TASK_MODIFY).forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + UrlUtil.URL_BLANK);
        }
    }

    private void showTaskTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = (UserModel) req.getSession().getAttribute("currentUser");
        switch (user.getRole().getName()) {
            case RoleUtil.ROLE_ADMIN -> showAllTask(req, resp);
            case RoleUtil.ROLE_MANAGER -> showManagedTask(req, resp);
            default -> showAssignedTask(req, resp);
        }
    }

    private void modifyTask(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int statusId = Integer.parseInt(req.getParameter("statusId"));
        int taskId = Integer.parseInt(Optional.ofNullable(req.getParameter("taskId")).orElse("-1"));
        if (taskId > 0) {
            TaskService.getInstance().updateTask(TaskModel.builder()
                    .id(taskId)
                    .name(req.getParameter("name"))
                    .startTime(TimeUtil.getTimeFromStr(req.getParameter("startTime")))
                    .endTime(TimeUtil.getTimeFromStr(req.getParameter("endTime")))
                    .userId(Integer.parseInt(req.getParameter("userId")))
                    .jobId(Integer.parseInt(req.getParameter("jobId")))
                    .statusId(Integer.parseInt(req.getParameter("statusId")))
                    .build());
        }
        resp.sendRedirect(req.getContextPath() + UrlUtil.URL_TASK);
    }

    private UserModel getUser(HttpServletRequest req) {
        int userId = Integer.parseInt(Optional.ofNullable(req.getParameter("userId")).orElse("-1"));
        return userId < 0 ?
                (UserModel) req.getSession().getAttribute("currentUser") :
                UserService.getInstance().getUserById(userId);
    }

    private TaskModel getTask(HttpServletRequest req) {
        int taskId = Integer.parseInt(Optional.ofNullable(req.getParameter("taskId")).orElse("-1"));
        return taskId < 0 ? null : TaskService.getInstance().getTaskById(taskId);
    }

}
