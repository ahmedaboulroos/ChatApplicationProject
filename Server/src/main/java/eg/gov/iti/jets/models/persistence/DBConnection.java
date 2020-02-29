package eg.gov.iti.jets.models.persistence;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleDriver;
import oracle.net.ano.AnoServices;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Properties databaseProperties;

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
                final String username = databaseProperties.getProperty("databaseUsername");
                final String password = databaseProperties.getProperty("databasePassword");
                final String url = databaseProperties.getProperty("databaseUrl");
                final String encryptionType = databaseProperties.getProperty("encryptionType");
                final String checksumType = databaseProperties.getProperty("checksumType");

                OracleDriver dr = new OracleDriver();
                Properties prop = new Properties();

                // We require the connection to be encrypted with either AES256 or AES192.
                // If the database doesn't accept such a security level, then the connection
                // attempt will fail.
                prop.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_ENCRYPTION_LEVEL, AnoServices.ANO_REQUIRED);
                prop.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_ENCRYPTION_TYPES, encryptionType);

                // We also require the use of the SHA1 algorithm for data integrity checking.
                prop.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_LEVEL, AnoServices.ANO_REQUIRED);
                prop.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_TYPES, checksumType);

                prop.setProperty("user", username);
                prop.setProperty("password", password);
                connection = (OracleConnection) dr.connect(url, prop);

                System.out.println(">> Database Connection Established...");
                System.out.println(">> Connection Encryption algorithm is: " + connection.getEncryptionAlgorithmName() + ", data integrity algorithm is: " + connection.getDataIntegrityAlgorithmName());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void setDatabaseProperties(Properties properties) {
        databaseProperties = properties;
    }
}
