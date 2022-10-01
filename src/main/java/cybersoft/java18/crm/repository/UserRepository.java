package cybersoft.java18.crm.repository;

import cybersoft.java18.crm.model.RoleModel;
import cybersoft.java18.crm.model.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends AbstractRepository<UserModel> {
    public List<UserModel> findAll() {
        final String query = """
                    select * from users u inner join roles r on u.role_id = r.id
                """;
        return executeQuery(connection -> {
            List<UserModel> users = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                UserModel user = UserModel.builder()
                        .id(result.getInt("id"))
                        .email(result.getString("email"))
                        .password(result.getString("password"))
                        .fullname(result.getString("fullname"))
                        .avatar(result.getString("avatar"))
                        .role(RoleModel.builder()
                                .id(result.getInt("role_id"))
                                .name(result.getString("name"))
                                .description(result.getString("description"))
                                .build())
                        .build();
                users.add(user);
            }
            return users;
        });
    }


    public UserModel findByEmail(String mail) {
        final String query = """
                select *
                from users u inner join roles r on u.role_id = r.id
                where email = ?
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new UserModel(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("fullname"),
                        resultSet.getString("avatar"),
                        new RoleModel(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("description")
                        )
                );
            }
            return null;
        });
    }

    public boolean existedByEmail(String email) {
        String query = """
                    select * from users where email = ?
                """;
        return existedBy(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        });
    }

    public boolean save(UserModel user) {
        // write a query to save new player
        final String query = """
                insert into users(email, password, fullname, avatar, role_id)
                values(?, ?, ?, ?, ?)
                """;
        return excuteSaveAndUpdate(connection -> {
            // create a statement to execute the query
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullname());
            statement.setString(4, user.getAvatar());
            statement.setInt(5, user.getRole().getId());

            return statement.executeUpdate();
        }) != 0;
    }

    public boolean updateUser(UserModel userModel) {
        String query = """
                    update users set 
                    email = ?,
                    password = ?,
                    fullname = ?,
                    avatar = ?,
                    role_id = ?
                    where id = ?
                """;
        return excuteSaveAndUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userModel.getEmail());
            statement.setString(2, userModel.getPassword());
            statement.setString(3, userModel.getFullname());
            statement.setString(4, userModel.getAvatar());
            statement.setInt(5, userModel.getRole().getId());
            statement.setInt(6, userModel.getId());
            return statement.executeUpdate();
        }) != 0;
//        String query = "UPDATE roles SET name = ?, description = ? WHERE id = ?";
    }

    public UserModel findUserById(int userId) {
        String query = """
                    select * from
                    users u inner join roles r on u.role_id = r.id 
                    where u.id = ?
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            return !result.next() ? null :
                    UserModel.builder()
                            .id(result.getInt("id"))
                            .email(result.getString("email"))
                            .password(result.getString("password"))
                            .fullname(result.getString("fullname"))
                            .avatar(result.getString("avatar"))
                            .role(RoleModel.builder()
                                    .id(result.getInt("role_id"))
                                    .name(result.getString("name"))
                                    .description(result.getString("description"))
                                    .build())
                            .build();
        });
    }

    public boolean deleteUser(int userId) {
        String query = """
                    delete from users where id = ?
                """;
        return executeDeleteQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            return statement.executeUpdate() > 0;
        });
    }
}
