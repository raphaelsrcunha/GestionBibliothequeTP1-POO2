package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager implements IDataAccess {
    // Instance unique (Singleton)
    private static volatile DatabaseManager instance;
    private static Connection connection;
    
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private DatabaseManager() {
        if (instance != null) {
            throw new RuntimeException("Utiliser la méthode getInstance() pour créer");
        }
    }
    
    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }
    
    @Override
    public void connect() throws Exception {
        if (connection == null || connection.isClosed()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
    
    @Override
    public void disconnect() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
            System.out.println("Connexion à la base de données fermée.");
        }
    }
    
    
    public Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            return connection;
        } catch (Exception e) {
            throw new SQLException("Error: ", e);
        }
    }
}