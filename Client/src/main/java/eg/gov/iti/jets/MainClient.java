package eg.gov.iti.jets;

import eg.gov.iti.jets.controllers.ClientStageCoordinator;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.network.implementations.ClientService;
import javafx.application.Application;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

public class MainClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        RMIConnection.startConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Client start...");
//        ClientStageCoordinator coordinator = ClientStageCoordinator.getInstance();
//        coordinator.setStage(primaryStage);
//        coordinator.startLoginScene();
        //
        final String xmlFilePath = "loginFile.xml";
        File file = new File(xmlFilePath);
        if (file.exists()) {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = null;
            try {
                String phone = null;
                String password = null;
                documentBuilder = documentFactory.newDocumentBuilder();


                Document document = documentBuilder.parse(file);

                NodeList userInfo = document.getElementsByTagName("User");

                for (int itr = 0; itr < userInfo.getLength(); itr++) {
                    Node node = userInfo.item(itr);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element element = (Element) node;
                        phone = element.getElementsByTagName("phone").item(0).getTextContent();

                        password = element.getElementsByTagName("password").item(0).getTextContent();
                    }


                }

                UserDao userDao = RMIConnection.getUserDao();
                User user = userDao.getUser(phone, password);
                System.out.println(user.getPhoneNumber());
                if (user != null) {

                    ClientStageCoordinator coordinator = ClientStageCoordinator.getInstance();
                    coordinator.currentUser = user;
                    coordinator.setStage(primaryStage);
                    coordinator.startMainChatAppScene();

                } else {

                    ClientStageCoordinator coordinator = ClientStageCoordinator.getInstance();
                    coordinator.setStage(primaryStage);
                    coordinator.startLoginScene();
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {

                e.printStackTrace();
            }

        } else {

            ClientStageCoordinator coordinator = ClientStageCoordinator.getInstance();
            coordinator.setStage(primaryStage);
            coordinator.startLoginScene();

        }
    }


    @Override
    public void stop() throws Exception {
        RMIConnection.getServerService().logout(ClientStageCoordinator.getInstance().currentUser.getId(), ClientService.getInstance());
        System.exit(0);
    }

}
