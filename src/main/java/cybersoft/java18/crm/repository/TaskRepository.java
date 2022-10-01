package cybersoft.java18.crm.repository;

import cybersoft.java18.crm.model.TaskModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository extends AbstractRepository<TaskModel> {
    public List<TaskModel> findAll() {
        String query = """
                select t.id, t.name, t.start_date, t.end_date, s.name, u.fullname, j.name from tasks t\s
                inner join status s on t.status_id = s.id
                inner join users u on t.user_id = u.id
                inner join jobs j on t.job_id = j.id
                """;
        return executeQuery(connection -> {
            List<TaskModel> tasks = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                tasks.add(TaskModel.builder()
                        .id(results.getInt("t.id"))
                        .name(results.getString("name"))
                        .startTime(results.getDate("start_date").toLocalDate().atStartOfDay())
                        .endTime(results.getDate("end_date").toLocalDate().atStartOfDay())
                        .statusName(results.getString("s.name"))
                        .personInCharge(results.getString("u.fullname"))
                        .jobName(results.getString("j.name"))
                        .build());
            }
            return tasks;
        });
    }

    public boolean save(TaskModel newTask) {
        String query = """
                    insert into tasks(name, start_date, end_date, user_id, job_id, status_id)
                    value (?, ?, ?, ?, ?, ?)  
                """;
        return excuteSaveAndUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newTask.getName());
            statement.setDate(2, Date.valueOf(newTask.getStartTime().toLocalDate()));
            statement.setDate(3, Date.valueOf(newTask.getEndTime().toLocalDate()));
            statement.setInt(4, newTask.getUserId());
            statement.setInt(5, newTask.getJobId());
            statement.setInt(6, newTask.getStatusId());
            return statement.executeUpdate();
        }) != 0;
    }

    public boolean update(TaskModel task) {
        String query = """
                    update tasks set 
                    name = ?,
                    start_date = ?,
                    end_date = ?,
                    user_id = ?,
                    job_id = ?,
                    status_id = ?
                    where id = ?
                """;
        return excuteSaveAndUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, task.getName());
            statement.setDate(2, Date.valueOf(task.getStartTime().toLocalDate()));
            statement.setDate(3, Date.valueOf(task.getEndTime().toLocalDate()));
            statement.setInt(4, task.getUserId());
            statement.setInt(5, task.getJobId());
            statement.setInt(6, task.getStatusId());
            statement.setInt(7, task.getId());
            return statement.executeUpdate();
        }) != 0;
    }

    public List<TaskModel> findTaskByUserIdStatusId(int userId, int statusId) {
        String query = """
                    select t.id, t.name, t.start_date, t.end_date, s.name, u.fullname, j.name from tasks t
                    inner join status s on t.status_id = s.id
                    inner join users u on t.user_id = u.id
                    inner join jobs j on t.job_id = j.id
                    where user_id = ? and status_id=?
                """;
        return executeQuery(connection -> {
            List<TaskModel> tasks = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, statusId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                tasks.add(TaskModel.builder()
                        .id(results.getInt("id"))
                        .name(results.getString("name"))
                        .startTime(results.getDate("start_date").toLocalDate().atStartOfDay())
                        .startTime(results.getDate("end_date").toLocalDate().atStartOfDay())
                        .build());
            }
            return tasks;
        });
    }

    public List<TaskModel> findTaskByUserId(int userId) {
        String query = """
                    select t.id, t.name, t.start_date, t.end_date, s.name, u.fullname, j.name from tasks t
                    inner join status s on t.status_id = s.id
                    inner join users u on t.user_id = u.id
                    inner join jobs j on t.job_id = j.id
                    where user_id = ?
                """;
        return executeQuery(connection -> {
            List<TaskModel> tasks = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                tasks.add(TaskModel.builder()
                        .id(results.getInt("t.id"))
                        .name(results.getString("name"))
                        .startTime(results.getDate("start_date").toLocalDate().atStartOfDay())
                        .endTime(results.getDate("end_date").toLocalDate().atStartOfDay())
                        .statusName(results.getString("s.name"))
                        .personInCharge(results.getString("u.fullname"))
                        .jobName(results.getString("j.name"))
                        .build());
            }
            return tasks;
        });
    }

    public List<TaskModel> findTaskByJobId(int jobId) {
        String query = """
                    select t.id, t.name, t.start_date, t.end_date, s.name, u.fullname, j.name from tasks t
                    inner join status s on t.status_id = s.id
                    inner join users u on t.user_id = u.id
                    inner join jobs j on t.job_id = j.id
                    where job_id = ?
                """;
        return executeQuery(connection -> {
            List<TaskModel> tasks = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, jobId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                tasks.add(TaskModel.builder()
                        .id(results.getInt("t.id"))
                        .name(results.getString("name"))
                        .startTime(results.getDate("start_date").toLocalDate().atStartOfDay())
                        .endTime(results.getDate("end_date").toLocalDate().atStartOfDay())
                        .statusName(results.getString("s.name"))
                        .personInCharge(results.getString("u.fullname"))
                        .jobName(results.getString("j.name"))
                        .build());
            }
            return tasks;
        });
    }

    public boolean deleteTaskById(int taskId) {
        String query = """
                    delete from tasks where id = ?
                """;
        return executeDeleteQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, taskId
            );
            return statement.executeUpdate() > 0;
        });
    }

    public TaskModel findTaskById(int taskId) {
        String query = """
                    select t.id, t.name, t.start_date, t.end_date, s.name, u.fullname, j.name from tasks t
                    inner join status s on t.status_id = s.id
                    inner join users u on t.user_id = u.id
                    inner join jobs j on t.job_id = j.id
                    where t.id = ?
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, taskId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return TaskModel.builder()
                        .id(result.getInt("t.id"))
                        .name(result.getString("name"))
                        .startTime(result.getDate("start_date").toLocalDate().atStartOfDay())
                        .endTime(result.getDate("end_date").toLocalDate().atStartOfDay())
                        .statusName(result.getString("s.name"))
                        .personInCharge(result.getString("u.fullname"))
                        .jobName(result.getString("j.name"))
                        .build();
            }
            return null;
        });
    }
}
