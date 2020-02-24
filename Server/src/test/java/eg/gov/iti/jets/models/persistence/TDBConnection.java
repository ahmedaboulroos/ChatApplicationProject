package eg.gov.iti.jets.models.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TDBConnection {

    private static Connection connection;

    private TDBConnection() {
    }

    public static void stopConnection() {
        if (connection == null) {
            System.out.println(">> Test Database Connection Already Terminated...");
        } else {
            try {
                connection.close();
                System.out.println(">> Test Database Connection Terminated...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CHATAPPTEST", "CHATAPPTEST");
                connection.setAutoCommit(true);
                System.out.println(">> Test Database Connection Established...");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

}
