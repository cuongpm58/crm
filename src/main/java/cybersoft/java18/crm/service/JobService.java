package cybersoft.java18.crm.service;

import cybersoft.java18.crm.model.JobModel;
import cybersoft.java18.crm.model.UserModel;
import cybersoft.java18.crm.repository.JobRepository;
import cybersoft.java18.crm.repository.UserRepository;

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
}
