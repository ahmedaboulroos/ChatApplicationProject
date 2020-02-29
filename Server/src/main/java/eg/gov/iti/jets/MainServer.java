package eg.gov.iti.jets;

import eg.gov.iti.jets.controllers.ServerStageCoordinator;
import eg.gov.iti.jets.models.configs.DatabaseSettings;
import eg.gov.iti.jets.models.configs.ServerSettings;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.persistence.DBConnection;
import javafx.application.Application;
import javafx.stage.Stage;
import oracle.net.ano.AnoServices;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Properties;

public class MainServer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        loadServerSettings();
        loadDatabaseSettings();
    }

    @Override
    public void start(Stage primaryStage) {
        ServerStageCoordinator coordinator = new ServerStageCoordinator(primaryStage);
        coordinator.startServerLoginScene();
    }

    @Override
    public void stop() {
        RMIConnection.getInstance().stopConnection();
        DBConnection.stopConnection();
        System.exit(0);
    }

    private void loadDatabaseSettings() {
        try {
            JAXBContext context = JAXBContext.newInstance(DatabaseSettings.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("src/main/resources/configs/database_settings.xsd"));
            marshaller.setSchema(schema);

            File xmlFileSettings = new File("src/main/resources/configs/database_settings.xsd");
            if (!xmlFileSettings.exists()) {
                DatabaseSettings databaseSettings = new DatabaseSettings();
                databaseSettings.setDatabaseUrl("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=tcp)(HOST=localhost)(PORT=1521))" + "(CONNECT_DATA=(SERVICE_NAME=XE)))");
                databaseSettings.setDatabaseUsername("CHATAPP");
                databaseSettings.setDatabasePassword("CHATAPP");
                databaseSettings.setEncryptionType("( " + AnoServices.ENCRYPTION_AES256 + "," + AnoServices.ENCRYPTION_AES192 + ")");
                databaseSettings.setChecksumType("( " + AnoServices.CHECKSUM_SHA1 + " )");
                marshaller.marshal(databaseSettings, new FileOutputStream("src/main/resources/configs/database_settings.xml"));
            }

            DatabaseSettings newDatabaseSettings = (DatabaseSettings) context.createUnmarshaller().unmarshal(new FileReader("src/main/resources/configs/database_settings.xml"));
            System.out.println(newDatabaseSettings);

            Properties databaseProperties = new Properties();
            databaseProperties.setProperty("databaseUrl", newDatabaseSettings.getDatabaseUrl());
            databaseProperties.setProperty("databaseUsername", newDatabaseSettings.getDatabaseUsername());
            databaseProperties.setProperty("databasePassword", newDatabaseSettings.getDatabasePassword());
            databaseProperties.setProperty("encryptionType", newDatabaseSettings.getEncryptionType());
            databaseProperties.setProperty("checksumType", newDatabaseSettings.getChecksumType());

            DBConnection.setDatabaseProperties(databaseProperties);
        } catch (JAXBException | FileNotFoundException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void loadServerSettings() {
        try {
            JAXBContext context = JAXBContext.newInstance(ServerSettings.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("src/main/resources/configs/server_settings.xsd"));
            marshaller.setSchema(schema);

            File xmlFileSettings = new File("src/main/resources/configs/server_settings.xsd");
            if (!xmlFileSettings.exists()) {
                ServerSettings serverSettings = new ServerSettings();
                serverSettings.setServerUsername("amin");
                serverSettings.setServerPassword("amin");
                marshaller.marshal(serverSettings, new FileOutputStream("src/main/resources/configs/server_settings.xml"));
            }

            ServerSettings newServerSettings = (ServerSettings) context.createUnmarshaller().unmarshal(new FileReader("src/main/resources/configs/server_settings.xml"));
            System.out.println(newServerSettings);

            Properties properties = new Properties();
            properties.setProperty("serverUsername", newServerSettings.getServerUsername());
            properties.setProperty("serverPassword", newServerSettings.getServerPassword());

            ServerStageCoordinator.setServerProperties(properties);
        } catch (JAXBException | FileNotFoundException | SAXException e) {
            e.printStackTrace();
        }
    }
}
