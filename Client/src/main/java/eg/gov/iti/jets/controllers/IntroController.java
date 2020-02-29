package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.network.implementations.ClientService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class IntroController implements Initializable {
    @FXML
    private Circle circleIcon;
    @FXML
    private JFXTextField serverAddressTf;
    @FXML
    private JFXButton checkServerAddressBtn;
    @FXML
    private Label errorLbl;
    private boolean connectionEstablished = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            circleIcon.setFill(new ImagePattern(
                    new Image("images/vector-chat-icon.jpg")));
        } catch (Exception e) {
            System.out.println("Intro Icon did not load");
        }

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            serverAddressTf.setText(inetAddress.getHostAddress());
            serverAddressTf.setDisable(false);
            checkServerAddressBtn.setDisable(false);
            LoginViewController.getInstance().disable();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void closeBtnHandler(MouseEvent Event) {
        Platform.exit();
    }


    public void handleCheckServerConnection(ActionEvent actionEvent) {
        connectionEstablished = RMIConnection.startConnection(serverAddressTf.getText());
        if (connectionEstablished) {
            serverAddressTf.setDisable(true);
            checkServerAddressBtn.setDisable(true);
            LoginViewController.getInstance().enable();
            performRememberMeOperation();
        } else {
            errorLbl.setText("Server Connection Error");
        }
    }

    private void performRememberMeOperation() {
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
                //System.out.println(user.getPhoneNumber());
                if (user != null) {
                    ClientStageCoordinator coordinator = ClientStageCoordinator.getInstance();
                    coordinator.currentUser = user;
                    //System.out.println(ClientService.getInstance() + "client service");
                    RMIConnection.getServerService().login(user.getId(), ClientService.getInstance());
                    coordinator.startMainChatAppScene();
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }

        }

    }
}
