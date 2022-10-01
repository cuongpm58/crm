package cybersoft.java18.crm.api;

import cybersoft.java18.crm.model.*;
import cybersoft.java18.crm.service.*;
import cybersoft.java18.crm.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "job", urlPatterns = {
        UrlUtil.URL_JOB,
        UrlUtil.URL_JOB_ADD,
        UrlUtil.URL_JOB_DELETE,
        UrlUtil.URL_JOB_MODIFY,
        UrlUtil.URL_JOB_DETAIL
})
public class JobController extends HttpServlet {
    private List<RoleModel> roles = RoleService.getInstance().getAllRole();
    private List<StatusModel> status = StatusService.getInstance().getAllStatus();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtil.URL_JOB_DETAIL -> showJobDetail(req, resp);
            case UrlUtil.URL_JOB_MODIFY -> showModifyJob(req, resp);
            case UrlUtil.URL_JOB_DELETE -> deleteUser(req, resp);
            case UrlUtil.URL_JOB_ADD -> showAddJob(req, resp);
            default -> showJobTable(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtil.URL_JOB_ADD -> addNewJob(req, resp);
            case UrlUtil.URL_JOB_MODIFY -> modifyJob(req, resp);
            default -> getAllJob(req, resp);
        }
    }


    private void showNothing(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + UrlUtil.URL_BLANK);
    }

    private void showManagedJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = getUser(req);
        List<JobModel> jobs = JobService.getInstance().getJobByUser(user.getId());
        req.setAttribute("jobs", jobs);
        req.getRequestDispatcher(JspUtil.JSP_JOB).forward(req, resp);
    }

    private void showAllJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<JobModel> jobs = JobService.getInstance().getAllJob();
        req.setAttribute("jobs", jobs);
        req.getRequestDispatcher(JspUtil.JSP_JOB).forward(req, resp);
    }

    private void addNewJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = getUser(req);
        boolean result = JobService.getInstance().addNewJob(JobModel.builder()
                .name(req.getParameter("name"))
                .startTime(TimeUtil.getTimeFromStr(req.getParameter("startTime")))
                .endTime(TimeUtil.getTimeFromStr(req.getParameter("endTime")))
                .createdUserId(user.getId())
                .build());
        resp.sendRedirect(req.getContextPath() + UrlUtil.URL_JOB);
    }

    private void getAllJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<JobModel> jobs = JobService.getInstance().getAllJob();
        PrintUtil.printJsonFromObject(resp, jobs);
    }

    private UserModel getUser(HttpServletRequest req) {
        int userId = Integer.parseInt(Optional.ofNullable(req.getParameter("userId")).orElse("-1"));
        return userId < 0 ?
                (UserModel) req.getSession().getAttribute("currentUser") :
                UserService.getInstance().getUserById(userId);
    }

    private void showJobTable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = getUser(req);
        switch (user.getRole().getName()) {
            case RoleUtil.ROLE_ADMIN -> showAllJob(req, resp);
            case RoleUtil.ROLE_MANAGER -> showManagedJob(req, resp);
            default -> showNothing(req, resp);
        }
    }

    private void showAddJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspUtil.JSP_JOB_ADD).forward(req, resp);
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int jobId = Integer.parseInt(req.getParameter("jobId"));
        JobService.getInstance().deleteJobById(jobId);
    }

    private void showModifyJob(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel user = getUser(req);
        JobModel job = getJob(req);
        if ((user.getRole().getName().equals(RoleUtil.ROLE_ADMIN)
                || user.getRole().getName().equals(RoleUtil.ROLE_MANAGER))
                && job != null) {
            req.setAttribute("job", job);
            req.getRequestDispatcher(JspUtil.JSP_JOB_MODIFY).forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + UrlUtil.URL_BLANK);
        }
    }

    private JobModel getJob(HttpServletRequest req) {
        int jobId = Integer.parseInt(Optional.ofNullable(req.getParameter("jobId")).orElse("-1"));
        return jobId < 0 ? null : JobService.getInstance().getJobById(jobId);
    }

    private void modifyJob(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int jobId = Integer.parseInt(Optional.ofNullable(req.getParameter("jobId")).orElse("-1"));
        if (jobId > 0) {
            JobService.getInstance().updateJob(JobModel.builder()
                    .id(jobId)
                    .name(req.getParameter("name"))
                    .startTime(TimeUtil.getTimeFromStr(req.getParameter("startTime")))
                    .endTime(TimeUtil.getTimeFromStr(req.getParameter("endTime")))
                    .build());
        }
        resp.sendRedirect(req.getContextPath() + UrlUtil.URL_JOB);
    }

    private void showJobDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int jobId = Integer.parseInt(Optional.ofNullable(req.getParameter("jobId")).orElse("-1"));
        if (jobId > 0) {
            List<TaskModel> tasks = TaskService.getInstance().getTaskByJobId(jobId);
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
            Map<String, List<TaskModel>> userTaskPair = new HashMap<>();
            tasks.forEach(task -> {
                if (userTaskPair.containsKey(task.getPersonInCharge())) {
                    userTaskPair.get(task.getPersonInCharge()).add(task);
                } else {
                    userTaskPair.put(task.getPersonInCharge(), Arrays.asList(task));
                }
            });
            userTaskPair.forEach((user,list)->{

            });
            Map<String, List<List<TaskModel>>> userTaskMap = new HashMap<>();
            userTaskPair.entrySet().stream().map(entry->{
                List<List<TaskModel>> lists = Arrays.asList(
                        new ArrayList<TaskModel>(),
                        new ArrayList<TaskModel>(),
                        new ArrayList<TaskModel>()
                );

                entry.getValue().forEach(task -> {
                    if(task.getStatusName().equals(StatusUtil.STATUS_TODO)) {
                        lists.get(0).add(task);
                    } else if(task.getStatusName().equals(StatusUtil.STATUS_IN_PROGRESS)){
                        lists.get(1).add(task);
                    } else {
                        lists.get(2).add(task);
                    }
                });
                return Map.of(entry.getKey(), lists);
            }).forEach(map -> userTaskMap.putAll(map));
            req.setAttribute("userTaskMap", userTaskMap);
            req.setAttribute("listPercentTask", listPercentTask);
            req.getRequestDispatcher(JspUtil.JSP_JOB_DETAIL).forward(req, resp);
        }
    }
}
