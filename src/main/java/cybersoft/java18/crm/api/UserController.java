package cybersoft.java18.crm.api;

import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.model.UserModel;
import cybersoft.java18.crm.service.RoleService;
import cybersoft.java18.crm.service.UserService;
import cybersoft.java18.crm.util.PrintUtil;
import cybersoft.java18.crm.util.UrlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "user", urlPatterns = {
        UrlUtil.URL_USER,
        UrlUtil.URL_USER_ADD
})
public class UserController extends HttpServlet {
    private List<RoleModel> roles = RoleService.getInstance().getAllRole();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getUsers(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()){
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

    private void getUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<UserModel> users = UserService.getInstance().getUsers();
        PrintUtil.printJsonFromObject(resp, users);
    }
}
