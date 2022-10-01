package cybersoft.java18.crm.service;

import cybersoft.java18.crm.model.JobModel;
import cybersoft.java18.crm.repository.JobRepository;

import java.util.List;

public class JobService {
    private static JobService INSTANCE;

    private JobRepository repository = new JobRepository();

    public static JobService getInstance(){
        if(INSTANCE == null){
            INSTANCE = new JobService();
        }

        return INSTANCE;
    }

    public List<JobModel> getAllJob() {
        return repository.findAll();
    }

    public boolean addNewJob(JobModel newJob) {
        if (!isValidJob(newJob))
            return false;
        return repository.save(newJob);
    }

    private boolean isValidJob(JobModel jobModel) {
        return jobModel.getName() != null && !"".equals(jobModel.getName().trim());
    }

    public boolean deleteJobById(int jobId) {
        return repository.deleteJobById(jobId);
    }

    public JobModel getJobById(int jobId) {
        return repository.findJobById(jobId);
    }

    public boolean updateJob(JobModel job) {
        return repository.updateJob(job);
    }

    public List<JobModel> getJobByUser(int userId) {
        return repository.findJobByUserId(userId);
    }
}
