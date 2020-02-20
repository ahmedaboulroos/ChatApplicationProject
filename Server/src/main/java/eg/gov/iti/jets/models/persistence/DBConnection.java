package eg.gov.iti.jets.models.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    private DBConnection() {
    }

    public static void stopConnection() {
        if (connection == null) {
            System.out.println(">> Database Connection Already Terminated...");
        } else {
            try {
                connection.close();
                System.out.println(">> Database Connection Terminated...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CHATAPP", "CHATAPP");
                connection.setAutoCommit(true);
                System.out.println(">> Database Connection Established...");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

}
