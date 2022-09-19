package cybersoft.java18.crm.repository;



import cybersoft.java18.crm.jdbc.MySqlConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractRepository<T> {
    protected List<T> executeQuery(JdbcExcute<List<T>> process) {
        try {
            Connection connection = MySqlConnection.getConnection();
            return process.processor(connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Integer excuteSaveAndUpdate(JdbcExcute<Integer> process){
        try{
            Connection connection = MySqlConnection.getConnection();
            //Lamda Function
            return process.processor(connection);
        }catch (Exception e){
            throw new RuntimeException("Error connect database");
//            System.out.println("Error connect database " + e.getMessage());
        }
    }
}
