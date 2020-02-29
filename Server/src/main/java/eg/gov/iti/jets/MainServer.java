package eg.gov.iti.jets;

import eg.gov.iti.jets.controllers.ServerStageCoordinator;
import eg.gov.iti.jets.models.configs.ServerSettings;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.persistence.DBConnection;
import javafx.application.Application;
import javafx.stage.Stage;
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
                serverSettings.setDatabaseUrl("localhost");
                serverSettings.setDatabasePort(1234);
                serverSettings.setDatabaseUsername("CHATAPP");
                serverSettings.setDatabasePassword("CHATAPP");
                marshaller.marshal(serverSettings, new FileOutputStream("src/main/resources/configs/server_settings.xml"));
            }

            ServerSettings newServerSettings = (ServerSettings) context.createUnmarshaller().unmarshal(new FileReader("src/main/resources/configs/server_settings.xml"));
            System.out.println(newServerSettings);

            Properties properties = new Properties();
            properties.setProperty("serverUsername", newServerSettings.getServerUsername());
            properties.setProperty("serverPassword", newServerSettings.getServerPassword());
            properties.setProperty("databaseUrl", newServerSettings.getDatabaseUrl());
            properties.setProperty("databasePort", Integer.toString(newServerSettings.getDatabasePort()));
            properties.setProperty("databaseUsername", newServerSettings.getDatabaseUsername());
            properties.setProperty("databasePassword", newServerSettings.getDatabasePassword());

            ServerStageCoordinator.setServerProperties(properties);
        } catch (JAXBException | SAXException | FileNotFoundException e) {
            e.printStackTrace();
        }
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

}
