package eg.gov.iti.jets.models.persistence;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import oracle.net.ano.AnoServices;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    static final String USERNAME = "CHATAPP";
    static final String PASSWORD = "CHATAPP";
    static final String URL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=localhost)(PORT=1521))" + "(CONNECT_DATA=(SERVICE_NAME=XE)))";

    private static OracleConnection connection;

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
                OracleDriver dr = new OracleDriver();
                Properties prop = new Properties();
                // We require the connection to be encrypted with either AES256 or AES192.
                // If the database doesn't accept such a security level, then the connection
                // attempt will fail.
                prop.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_ENCRYPTION_LEVEL, AnoServices.ANO_REQUIRED);
                prop.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_ENCRYPTION_TYPES, "( " + AnoServices.ENCRYPTION_AES256 + "," + AnoServices.ENCRYPTION_AES192 + ")");

                // We also require the use of the SHA1 algorithm for data integrity checking.
                prop.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_LEVEL, AnoServices.ANO_REQUIRED);
                prop.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_TYPES, "( " + AnoServices.CHECKSUM_SHA1 + " )");

                prop.setProperty("user", USERNAME);
                prop.setProperty("password", PASSWORD);
                connection = (OracleConnection) dr.connect(URL, prop);

                System.out.println(">> Database Connection Established...");
                System.out.println(">> Connection Encryption algorithm is: " + connection.getEncryptionAlgorithmName() + ", data integrity algorithm is: " + connection.getDataIntegrityAlgorithmName());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

}
