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
                select t.name, t.start_date, t.end_date, s.name, u.fullname, j.name from tasks t\s
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
            statement.setDate(3, Date.valueOf(newTask.getStartTime().toLocalDate()));
            statement.setInt(4, newTask.getUserId());
            statement.setInt(5, newTask.getJobId());
            statement.setInt(6, newTask.getStatusId());
            return statement.executeUpdate();
        }) != 0;
    }

    public List<TaskModel> findTaskByUserIdStatusId(int userId, int statusId) {
        String query = """
                    select * from tasks
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
                    select * from task where user_id = ?
                """;
        return executeQuery(connection -> {
            List<TaskModel> tasks = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                tasks.add(TaskModel.builder()
                        .name(results.getString("name"))
                        .startTime(results.getDate("start_date").toLocalDate().atStartOfDay())
                        .startTime(results.getDate("end_date").toLocalDate().atStartOfDay())
                        .build());
            }
            return tasks;
        });
    }
}
