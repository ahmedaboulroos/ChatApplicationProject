package eg.gov.iti.jets.models.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private static Connection connection;

    private DBConnection() { }

    public boolean initConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CHATAPP", "CHATAPP");
            connection.setAutoCommit(true);
            System.out.println(">> Database Connection Established...");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void stopConnection() {
        try {
            connection.close();
            System.out.println(">> Database Connection Terminated...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null)
            instance = new DBConnection();
        return instance;
    }

}
