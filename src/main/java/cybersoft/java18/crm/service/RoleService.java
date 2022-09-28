package cybersoft.java18.crm.service;

import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.repository.RoleRepository;

import java.util.List;

public class RoleService {
    private static RoleService INSTANCE = new RoleService();

    private RoleRepository roleRepository = new RoleRepository();

    public static RoleService getInstance(){
        if(INSTANCE == null){
            INSTANCE = new RoleService();
        }

        return INSTANCE;
    }

    public List<RoleModel> getAllRole(){
        return roleRepository.getAllRole();
    }

    public Integer deleteRoleById(String id){
        return roleRepository.deleteRole(id);
    }

    public Integer updateRoleById(RoleModel roleModel){
        return  roleRepository.updateRole(roleModel);
    }

    public RoleModel getRoleByEmail(String email) {
        return roleRepository.findRoleByEmail(email);
    }
}
