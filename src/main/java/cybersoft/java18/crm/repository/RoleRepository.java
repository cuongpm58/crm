package cybersoft.java18.crm.repository;

import javax.management.relation.Role;

import cybersoft.java18.crm.model.RoleModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository extends AbstractRepository<RoleModel> {

    public List<RoleModel> getAllRole() {
        return executeQuery((connection -> {
            List<RoleModel> roleModels = new ArrayList<RoleModel>();
            String query = "SELECT * FROM roles";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                RoleModel roleModel = new RoleModel();
                roleModel.setId(resultSet.getInt("id"));
                roleModel.setName(resultSet.getString("name"));
                roleModel.setDescription(resultSet.getString("description"));

                roleModels.add(roleModel);
            }
            return roleModels;
        }));
    }

    public Integer deleteRole(String id) {
        return excuteSaveAndUpdate((connection -> {
            String query = "DELETE FROM roles WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            return statement.executeUpdate();
        }));
    }

    public Integer updateRole(RoleModel roleModel) {
        return excuteSaveAndUpdate((connection -> {
            String query = "UPDATE roles SET name = ?, description = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, roleModel.getName());
            statement.setString(2, roleModel.getDescription());
            statement.setInt(3, roleModel.getId());
            return statement.executeUpdate();
        }));
    }
}
