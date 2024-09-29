package uk.ac.edgehill.mres;

import java.sql.*;

public class GameEventRepository {

    private String databaseURI = "jdbc:postgresql://localhost:5432/test";
    private String user="user";
    private String password="210703";

    public GameEventRepository()
    {
        try {
            Connection connection = DriverManager.getConnection(databaseURI, user, password);
            System.out.println("Connected to " + connection.getCatalog());
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }    
}
