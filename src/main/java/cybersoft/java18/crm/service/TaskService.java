package cybersoft.java18.crm.service;

import cybersoft.java18.crm.model.TaskModel;
import cybersoft.java18.crm.repository.TaskRepository;

import java.util.List;

public class TaskService {
    private static TaskService INSTANCE;
    private static TaskRepository repository = new TaskRepository();

    public static TaskService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TaskService();
        }
        return INSTANCE;
    }

    public List<TaskModel> getAllTasks() {
        return repository.findAll();
    }

    public boolean addTask(TaskModel newTask) {
        return repository.save(newTask);
    }

    public List<TaskModel> getTaskByUserIdStatusId(int userId, int statusId) {
        return repository.findTaskByUserIdStatusId(userId, statusId);
    }

    public List<TaskModel> getTaskByUserId(int userId) {
        return repository.findTaskByUserId(userId);
    }

    public List<TaskModel> getTaskByJobId(int jobId) {
        return repository.findTaskByJobId(jobId);
    }

    public boolean deleteTaskById(int taskId) {
        return repository.deleteTaskById(taskId);
    }

    public TaskModel getTaskById(int taskId) {
        return repository.findTaskById(taskId);
    }

    public boolean updateTask(TaskModel task) {
        return repository.update(task);
    }
}
