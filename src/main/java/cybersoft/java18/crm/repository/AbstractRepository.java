package cybersoft.java18.crm.repository;



import cybersoft.java18.crm.jdbc.MySqlConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractRepository<T> {
    protected List<T> executeQuery(JdbcExecute<List<T>> process) {
        try {
            Connection connection = MySqlConnection.getConnection();
            return process.process(connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Integer excuteSaveAndUpdate(JdbcExecute<Integer> process){
        try{
            Connection connection = MySqlConnection.getConnection();
            //Lamda Function
            return process.process(connection);
        }catch (Exception e){
            throw new RuntimeException("Error connect database");
//            System.out.println("Error connect database " + e.getMessage());
        }
    }

    public T executeQuerySingle(JdbcExecute<T> processor) {
        try (Connection connection = MySqlConnection.getConnection()) {
            return processor.process(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Boolean existedBy(JdbcExecute<Boolean> processor) {
        try (Connection connection = MySqlConnection.getConnection()) {
            return processor.process(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
