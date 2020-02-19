package eg.gov.iti.jets.models.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TDBConnection {

    private static TDBConnection instance;
    private static Connection connection;

    private TDBConnection() {
    }

    public static synchronized TDBConnection getInstance() {
        if (instance == null)
            instance = new TDBConnection();
        return instance;
    }

    public boolean initConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CHATAPPTEST", "CHATAPPTEST");
            connection.setAutoCommit(true);
            System.out.println(">> Test Database Connection Established...");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void stopConnection() {
        try {
            connection.close();
            System.out.println(">> Test Database Connection Terminated...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
