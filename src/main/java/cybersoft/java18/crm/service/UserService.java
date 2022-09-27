package cybersoft.java18.crm.service;

import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.model.UserModel;
import cybersoft.java18.crm.repository.StatusRepository;
import cybersoft.java18.crm.repository.UserRepository;

import java.util.List;

public class UserService {
    private static UserService INSTANCE = new UserService();

    private UserRepository repository = new UserRepository();

    public static UserService getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UserService();
        }

        return INSTANCE;
    }

    public List<UserModel> getUsers() {
        return repository.findAll();
    }

    public boolean addNewUser(UserModel newUser) {
        if (!isValidUser(newUser))
            return false;

        boolean userExisted = repository.existedByEmail(newUser.getEmail());

        if (userExisted)
            return false;

        return repository.save(newUser);
    }

    public boolean updateUser(UserModel userModel) {
        return repository.updateUser(userModel);
    }

    public UserModel login(String email, String password) {
        UserModel user = repository.findByEmail(email);

        if (user == null)
            return null;

        if (user.getPassword().equals(password))
            return user;

        return null;
    }

    private boolean isValidUser(UserModel userModel) {
        if (userModel.getEmail() == null || "".equals(userModel.getEmail().trim()))
            return false;

        if (userModel.getPassword() == null || "".equals(userModel.getPassword().trim()))
            return false;

        return userModel.getFullname() != null && !"".equals(userModel.getFullname().trim());
    }
}
