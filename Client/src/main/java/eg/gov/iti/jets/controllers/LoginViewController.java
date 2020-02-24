package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.network.implementations.ClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginViewController implements Initializable {

    @FXML
    private JFXTextField phoneNumberTf;

    @FXML
    private JFXPasswordField passwordPf;

    @FXML
    private JFXCheckBox rememberMeCb;

    @FXML
    private JFXButton signInBtn;

    @FXML
    private JFXButton signUpBtn;

    @FXML
    private Label errorLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void handleSignInBtn(ActionEvent event) {
        try {
            UserDao userDao = RMIConnection.getUserDao();
            User user = userDao.getUser(phoneNumberTf.getText(), passwordPf.getText());
            if (user != null) {
                ClientStageCoordinator.getInstance().currentUser = user;
                ClientStageCoordinator.getInstance().startMainChatAppScene();
                System.out.println(user.getId());
                System.out.println(ClientService.getInstance());
                RMIConnection.getServerService().login(user.getId(), ClientService.getInstance());
                if (rememberMeCb.isSelected()) {
                    rememberMeCbHandelAction();
                }
            } else {
                errorLbl.setText("Invalid PhoneNumber or Password");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSignUpBtn(ActionEvent event) {
        try {
            ClientStageCoordinator.getInstance().startRegistrationScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rememberMeCbHandelAction() {

        final String xmlFilePath = "loginFile.xml";
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element elementRoot = document.createElement("User");
            document.appendChild(elementRoot);
            Element phoneElement = document.createElement("phone");

            phoneElement.appendChild(document.createTextNode(phoneNumberTf.getText()));
            elementRoot.appendChild(phoneElement);

            Element name = document.createElement("password");
            name.appendChild(document.createTextNode(passwordPf.getText()));
            elementRoot.appendChild(name);


            TransformerFactory transformFactory = TransformerFactory.newInstance();
            Transformer transformer = transformFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, streamResult);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();

        } catch (TransformerException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}

