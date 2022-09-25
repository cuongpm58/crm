package cybersoft.java18.crm.service;

import cybersoft.java18.crm.model.StatusModel;
import cybersoft.java18.crm.model.StatusModel;
import cybersoft.java18.crm.repository.StatusRepository;
import cybersoft.java18.crm.repository.StatusRepository;

import java.util.List;

public class StatusService {
    private static StatusService INSTANCE = new StatusService();

    private StatusRepository StatusRepository = new StatusRepository();

    public static StatusService getInstance(){
        if(INSTANCE == null){
            INSTANCE = new StatusService();
        }

        return INSTANCE;
    }

    public List<StatusModel> getAllStatus(){
        return StatusRepository.getAllStatus();
    }

//    public Integer deleteStatusById(String id){
//        return StatusRepository.deleteStatus(id);
//    }
//
//    public Integer updateStatusById(StatusModel statusModel){
//        return  StatusRepository.updateStatus(statusModel);
//    }
}
