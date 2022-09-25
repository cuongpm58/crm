package cybersoft.java18.crm.api;

import cybersoft.java18.crm.model.StatusModel;
import cybersoft.java18.crm.service.StatusService;
import cybersoft.java18.crm.util.PrintUtil;
import cybersoft.java18.crm.util.UrlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "status",urlPatterns = {
        UrlUtil.URL_STATUS
})
public class StatusController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<StatusModel> statusMModels = StatusService.getInstance().getAllStatus();
        PrintUtil.printJsonFromObject(resp, statusMModels);

    }
}
