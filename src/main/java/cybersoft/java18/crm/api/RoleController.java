package cybersoft.java18.crm.api;

import com.google.gson.Gson;
import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.service.RoleService;
import cybersoft.java18.crm.util.UrlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "role",urlPatterns = {
        UrlUtil.URL_ROLE,
        UrlUtil.URL_ROLE_ADD
})
public class RoleController extends HttpServlet {
    Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RoleModel> listRoles =  RoleService.getInstance().getAllRole();

        String json = gson.toJson(listRoles);
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
