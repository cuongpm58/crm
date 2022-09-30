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
import java.util.Optional;

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
        switch (req.getServletPath()) {
            case UrlUtil.URL_USER_PROFILE -> showUserProfile(req, resp);
            case UrlUtil.URL_USER_MODIFY -> showModifyUser(req, resp);
            case UrlUtil.URL_USER_DELETE -> deleteUser(req, resp);
            case UrlUtil.URL_USER_ADD -> showAddUser(req, resp);
            default -> showUserTable(req, resp);
        }
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = getUser(req);
        UserService.getInstance().deleteUser(user.getId());
        resp.sendRedirect(req.getContextPath() + UrlUtil.URL_USER);
//        resp.setHeader("REFRESH", "0");
//        req.getRequestDispatcher(JspUtil.JSP_USER).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtil.URL_USER_ADD -> addUser(req, resp);
            case UrlUtil.URL_USER_MODIFY -> modifyUser(req, resp);
//            case UrlUtil.URL_USER_DELETE -> deleteUser(req, resp);
            default -> getUsers(req, resp);
        }
    }

    private void showModifyUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = getUser(req);
        if (user.getRole().getName().equals(RoleUtil.ROLE_ADMIN)) {
            req.setAttribute("user", user);
            req.setAttribute("roles", roles);
            req.getRequestDispatcher(JspUtil.JSP_USER_MODIFY).forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + UrlUtil.URL_BLANK);
        }
    }

    private void modifyUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(Optional.ofNullable(req.getParameter("userId")).orElse("-1"));
//        String roleId = req.getParameter("roleId");
        if (userId > 0) {
            UserService.getInstance().updateUser(UserModel.builder()
                    .id(userId)
                    .fullname(req.getParameter("fullname"))
                    .email(req.getParameter("email"))
                    .password(req.getParameter("password"))
                    .role(roles.stream().filter(
                                    role -> role.getId() == Integer.valueOf(req.getParameter("roleId")))
                            .findFirst().get())
                    .build());
        }
        resp.sendRedirect(req.getContextPath() + UrlUtil.URL_USER);
    }

    private void showUserProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = getUser(req);
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

    private void showUserTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = getUser(req);
        switch (user.getRole().getName()) {
            case RoleUtil.ROLE_ADMIN -> showAllMember(req, resp);
            case RoleUtil.ROLE_MANAGER -> showSubordinate(req, resp);
            default -> showNothing(req, resp);
        }
    }

    private void showAddUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", roles);
        req.getRequestDispatcher(JspUtil.JSP_USER_ADD).forward(req, resp);
    }

    private void showSubordinate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserModel> users = UserService.getInstance().getUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher(JspUtil.JSP_USER).forward(req, resp);
    }

    private void showNothing(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + UrlUtil.URL_BLANK);
    }

    private void showAllMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserModel> users = UserService.getInstance().getUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher(JspUtil.JSP_USER).forward(req, resp);
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
        resp.sendRedirect(req.getContextPath() + UrlUtil.URL_USER);
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

    private UserModel getUser(HttpServletRequest req) {
        int userId = Integer.parseInt(Optional.ofNullable(req.getParameter("userId")).orElse("-1"));
        return userId < 0 ?
                (UserModel) req.getSession().getAttribute("currentUser") :
                UserService.getInstance().getUserById(userId);
    }
}
