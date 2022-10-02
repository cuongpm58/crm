package cybersoft.java18.crm.repository;

import cybersoft.java18.crm.model.JobModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobRepository extends AbstractRepository<JobModel> {
    public List<JobModel> findAll() {
        String query = "select * from jobs";
        return executeQuery(connection -> {
            List<JobModel> jobs = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                jobs.add(JobModel.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .startTime(result.getDate("start_date").toLocalDate().atStartOfDay())
                        .endTime(result.getDate("end_date").toLocalDate().atStartOfDay())
                        .build());
            }
            return jobs;
        });
    }

    public boolean save(JobModel newJob) {
        String query = """
                    insert into jobs(name, start_date, end_date, id_created_user)
                     values(?, ?, ?, ?)
                """;
        return excuteSaveAndUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newJob.getName());
//            statement.setTimestamp(2, Timestamp.from(
//                    newJob.getStartTime().toInstant(ZoneOffset.of("+07:00"))
//            ));
//            statement.setTimestamp(3, Timestamp.from(
//                    newJob.getEndTime().toInstant(ZoneOffset.of("+07:00"))
//            ));
            statement.setDate(2, Date.valueOf(newJob.getStartTime().toLocalDate()));
            statement.setDate(3, Date.valueOf(newJob.getEndTime().toLocalDate()));
            statement.setInt(4, newJob.getCreatedUserId());
            return statement.executeUpdate();
        }) != 0;
    }

    public boolean deleteJobById(int jobId) {
        String query = """
                    delete from jobs where id = ?
                """;
        return executeDeleteQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, jobId);
            return statement.executeUpdate() > 0;
        });
    }

    public JobModel findJobById(int jobId) {
        String query = """
                    select * from jobs where id = ?
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, jobId);
            ResultSet result = statement.executeQuery();
            return !result.next() ? null :
                    JobModel.builder()
                            .id(result.getInt("id"))
                            .name(result.getString("name"))
                            .startTime(result.getDate("start_date").toLocalDate().atStartOfDay())
                            .endTime(result.getDate("end_date").toLocalDate().atStartOfDay())
                            .build();
        });
    }

    public boolean updateJob(JobModel job) {
        String query = """
                    update jobs set 
                    name = ?,
                    start_date = ?,
                    end_date = ?
                    where id = ?
                """;
        return excuteSaveAndUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, job.getName());
            statement.setDate(2, Date.valueOf(job.getStartTime().toLocalDate()));
            statement.setDate(3, Date.valueOf(job.getEndTime().toLocalDate()));
            statement.setInt(4, job.getId());
            return statement.executeUpdate();
        }) != 0;
    }

    public List<JobModel> findJobByUserId(int userId) {
        String query = """
                    select * from jobs j
                    inner join users u on u.id = j.id_created_user
                     where j.id_created_user = ?
                """;
        return executeQuery(connection -> {
            List<JobModel> jobs = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                jobs.add(JobModel.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .startTime(result.getDate("start_date").toLocalDate().atStartOfDay())
                        .endTime(result.getDate("end_date").toLocalDate().atStartOfDay())
                        .build());
            }
            return jobs;
        });
    }
}
