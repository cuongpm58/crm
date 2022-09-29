package cybersoft.java18.crm.api;

import cybersoft.java18.crm.model.*;
import cybersoft.java18.crm.service.*;
import cybersoft.java18.crm.util.JspUtil;
import cybersoft.java18.crm.util.PrintUtil;
import cybersoft.java18.crm.util.RoleUtil;
import cybersoft.java18.crm.util.UrlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "user", urlPatterns = {
        UrlUtil.URL_USER,
        UrlUtil.URL_USER_DETAIL,
        UrlUtil.URL_USER_MODIFY,
        UrlUtil.URL_USER_DELETE,
        UrlUtil.URL_USER_ADD,
        UrlUtil.URL_USER_PROFILE
})

public class UserController extends HttpServlet {
    private List<RoleModel> roles = RoleService.getInstance().getAllRole();
    private List<StatusModel> status = StatusService.getInstance().getAllStatus();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = (UserModel) req.getSession().getAttribute("currentUser");
        switch (req.getServletPath()) {
            case UrlUtil.URL_USER_PROFILE -> showUserProfile(req, resp, user);
            default -> showUserTable(req, resp, user);
        }
    }

    private void showUserProfile(HttpServletRequest req, HttpServletResponse resp, UserModel user) throws ServletException, IOException {
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

        req.setAttribute("tasks", tasks);
        req.setAttribute("listPercentTask", listPercentTask);
        req.setAttribute("user", user);
        req.getRequestDispatcher(JspUtil.JSP_USER_PROFILE).forward(req, resp);
    }

    private void showUserTable(HttpServletRequest req, HttpServletResponse resp, UserModel user) throws ServletException, IOException {
        switch (user.getRole().getName()) {
            case RoleUtil.ROLE_ADMIN -> showAllMember(req, resp);
            case RoleUtil.ROLE_MANAGER -> showSubordinate(req, resp);
            default -> showNothing(req, resp);
        }
    }

    private void showSubordinate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserModel> users = UserService.getInstance().getUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher(JspUtil.JSP_USER).forward(req, resp);
    }

    private void showNothing(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void showAllMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserModel> users = UserService.getInstance().getUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher(JspUtil.JSP_USER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtil.URL_USER_ADD -> addUser(req, resp);
            default -> getUsers(req, resp);
        }
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel newUser = UserModel.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .fullname(req.getParameter("fullname"))
                .avatar(req.getParameter("avatar"))
                .role(roles.get(0))
                .build();

        boolean isSuccess = UserService.getInstance().addNewUser(newUser);
        PrintUtil.printJsonFromObject(resp, isSuccess);
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = UserModel.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .fullname(req.getParameter("fullname"))
                .avatar(req.getParameter("avatar"))
                .role(roles.get(0))
                .build();

        UserService.getInstance().updateUser(user);
    }

    private void getUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserModel> users = UserService.getInstance().getUsers();
        PrintUtil.printJsonFromObject(resp, users);
    }
}
