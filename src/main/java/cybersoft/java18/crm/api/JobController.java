package cybersoft.java18.crm.api;

import cybersoft.java18.crm.model.JobModel;
import cybersoft.java18.crm.model.TaskModel;
import cybersoft.java18.crm.model.UserModel;
import cybersoft.java18.crm.service.JobService;
import cybersoft.java18.crm.service.TaskService;
import cybersoft.java18.crm.service.UserService;
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
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "job", urlPatterns = {
        UrlUtil.URL_JOB,
        UrlUtil.URL_JOB_ADD
})
public class JobController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = (UserModel) req.getSession().getAttribute("currentUser");
        switch (user.getRole().getName()) {
            case RoleUtil.ROLE_ADMIN -> showAllJob(req, resp);
            case RoleUtil.ROLE_MANAGER -> showManagedJob(req, resp);
            default -> showNothing(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtil.URL_JOB_ADD -> addNewJob(req, resp);
            default -> getAllJob(req, resp);
        }
    }

    private void showNothing(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    private void showManagedJob(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void showAllJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<JobModel> jobs = JobService.getInstance().getAllJob();
        req.setAttribute("jobs", jobs);
        req.getRequestDispatcher(JspUtil.JSP_JOB).forward(req, resp);
    }

    private void addNewJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean result = JobService.getInstance().addNewJob(JobModel.builder()
                .name(req.getParameter("name"))
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .createdUserId(Integer.parseInt(req.getParameter("createdUserId")))
                .build());
        PrintUtil.printJsonFromObject(resp, result);
    }

    private void getAllJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<JobModel> jobs = JobService.getInstance().getAllJob();
        PrintUtil.printJsonFromObject(resp, jobs);
    }

}
