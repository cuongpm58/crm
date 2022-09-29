package cybersoft.java18.crm.repository;

import cybersoft.java18.crm.model.StatusModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatusRepository extends AbstractRepository<StatusModel> {
    public List<StatusModel> getAllStatus() {
        return executeQuery((connection -> {
            List<StatusModel> statusModels = new ArrayList<StatusModel>();
            String query = "SELECT * FROM status";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                statusModels.add(StatusModel.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build());
            }
            return statusModels;
        }));
    }

    public Integer deleteStatus(String id) {
        return excuteSaveAndUpdate((connection -> {
            String query = "DELETE FROM status WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            return statement.executeUpdate();
        }));
    }

//    public Integer updateStatus(StatusModel statusModel) {
//        return excuteSaveAndUpdate((connection -> {
//            String query = "UPDATE roles SET name = ?, description = ? WHERE id = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, statusModel.getName());
//            statement.setInt(2, statusModel.getId());
//            return statement.executeUpdate();
//        }));
//    }
}
