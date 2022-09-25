package cybersoft.java18.crm.api;

import cybersoft.java18.crm.model.TaskModel;
import cybersoft.java18.crm.service.TaskService;
import cybersoft.java18.crm.util.PrintUtil;
import cybersoft.java18.crm.util.UrlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "task", urlPatterns = {
        UrlUtil.URL_TASK,
        UrlUtil.URL_TASK_ADD
})
public class TaskController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getAllTask(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()){
            case UrlUtil.URL_TASK_ADD -> addNewTask(req, resp);
            default -> getAllTask(req, resp);
        }
    }

    private void addNewTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean result = TaskService.getInstance().addTask(TaskModel.builder()
                        .name(req.getParameter("name"))
                        .startTime(LocalDateTime.now())
                        .endTime(LocalDateTime.now())
                        .userId(Integer.parseInt(req.getParameter("userId")))
                        .jobId(Integer.parseInt(req.getParameter("jobId")))
                        .statusId(Integer.parseInt(req.getParameter("statusId")))
                .build());
        PrintUtil.printJsonFromObject(resp, result);
    }

    private void getAllTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TaskModel> tasks = TaskService.getInstance().getTasks();
        PrintUtil.printJsonFromObject(resp, tasks);
    }
}
