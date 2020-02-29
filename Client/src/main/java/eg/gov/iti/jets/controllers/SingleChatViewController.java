package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXListCell;
import eg.gov.iti.jets.models.dao.interfaces.SingleChatDao;
import eg.gov.iti.jets.models.dao.interfaces.SingleChatMessageDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.SingleChatMessage;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.singleChat.ObjectFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SingleChatViewController implements Initializable {
    private static SingleChatViewController singleChatViewController;
    private SingleChatMessageDao singleChatMessageDao = RMIConnection.getSingleChatMessageDao();
    private SingleChatDao singleChatDao = RMIConnection.getSingleChatDao();
    @FXML
    Circle imageCircle;
    @FXML
    Label nameLbl;

    public static SingleChatViewController getInstance() {
        return singleChatViewController;
    }

    public void setController(SingleChatViewController singleChatViewController) {
        SingleChatViewController.singleChatViewController = singleChatViewController;
    }

    private User currentUser = ClientStageCoordinator.getInstance().currentUser;
    private int singleChatId;
    private UserDao userDao = RMIConnection.getUserDao();
    @FXML
    private ListView<SingleChatMessage> singleChatMessagesLv;

    @FXML
    private HTMLEditor singleChatMessageHtml;
    private SingleChat singleChat;
    private int otherUserId;

    public void setSingleChatMessages(int singleChatId) {
        this.singleChatId = singleChatId;
        try {
            this.singleChat = singleChatDao.getSingleChat(singleChatId);
            this.otherUserId = singleChat.getUserOneId() == currentUser.getId() ?
                    singleChat.getUserTwoId() : singleChat.getUserOneId();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        updateSingleChat();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        singleChatMessageHtml.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER && keyEvent.isShiftDown()) {
                sendMessage();
            }
        });
        singleChatMessagesLv.setCellFactory(listViewListCellCallback -> new JFXListCell<>() {
            @Override
            protected void updateItem(SingleChatMessage message, boolean empty) {
                super.updateItem(message, empty);
                if (message != null) {
                    HBox hBox = new HBox();
                    try {
                        Pos pos = null;
                        User user = null;

                        if (message.getUserId() == currentUser.getId()) {
                            user = currentUser;
                            pos = Pos.CENTER_RIGHT;
                        } else {
                            user = userDao.getUser(message.getUserId());
                            pos = Pos.CENTER_LEFT;
                        }
                        byte[] imageBytes = user.getProfileImage();
                        Circle circle = new Circle();
                        try {
                            Image image = ImageUtiles.fromBytesToImage(imageBytes);
                            circle.setFill(new ImagePattern(image));
                        } catch (Exception e) {
                            System.out.println("Chat Icon not loaded.");
                        }
                        circle.setRadius(20);

                        WebView webView = new WebView();
                        webView.getEngine().loadContent(message.getContent());
                        webView.setMaxSize(400, 100);

                        if (pos == Pos.CENTER_RIGHT) {
                            hBox.getChildren().addAll(webView, circle);
                        } else {
                            hBox.getChildren().addAll(circle, webView);
                        }
                        hBox.setAlignment(pos);
                        setGraphic(hBox);
                        singleChatMessagesLv.scrollTo(singleChatMessagesLv.getItems().size() - 1);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    setGraphic(null);
                }
                setText(null);
            }
        });
    }

    @FXML
    void handleSendBtn(ActionEvent event) {
        sendMessage();
    }

    private void sendMessage() {
        try {
            String msg = singleChatMessageHtml.getHtmlText().replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "").replace("<p>", "\n")
                    .replace("<br>", "\n").replace("&nbsp;", " ").replace("</p>", "\n").replace("</body></html>", "");
            // System.out.println("msggggggggggggggggggggggggggggggggggggggggg"+msg);
            SingleChatMessageDao singleChatDao = RMIConnection.getSingleChatMessageDao();
            SingleChatMessage singleChatMessage = new SingleChatMessage(this.singleChatId, currentUser.getId(), msg);
            singleChatDao.createSingleChatMessage(singleChatMessage);
        } catch (
                RemoteException e) {
            e.printStackTrace();
        }
    }

    private void updateSingleChat() {
        try {
            User user = userDao.getUser(otherUserId);
            byte[] imageBytes = user.getProfileImage();
            if (imageBytes != null)
                imageCircle.setFill(new ImagePattern(ImageUtiles.fromBytesToImage(imageBytes)));
            else {
                try {
                    imageCircle.setFill(new ImagePattern(new Image(getClass().getClassLoader().getResource("images/chat-circle-blue-512.png").toExternalForm())));
                } catch (Exception e) {
                    System.out.println("Center View Image not loaded.");
                }
            }
            nameLbl.setText(user.getUsername() == null ? user.getPhoneNumber() : user.getUsername());
            singleChatMessagesLv.getItems().clear();
            List<SingleChatMessage> singleChatMessages = RMIConnection.getSingleChatDao().getSingleChatMessages(singleChatId);
            if (singleChatMessages != null) {
                singleChatMessagesLv.setItems(FXCollections.observableList(singleChatMessages));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addSingleChatMessage(SingleChatMessage singleChatMessage) {
        Platform.runLater(() -> singleChatMessagesLv.getItems().add(singleChatMessage));
    }

    @FXML
    void handleSaveSessionBtn(ActionEvent event) {
        List<SingleChatMessage> singleChatMessageList = getSingleChatMessages(singleChatId);
        generateXml(singleChatMessageList);
        System.out.println(singleChatMessageList);
    }

    public List<SingleChatMessage> getSingleChatMessages(int singleChatId) {
        List<SingleChatMessage> singleChatMessagesList = null;
        List<SingleChatMessage> singleChatMessageFilteredList = new ArrayList<>();
        try {
            LocalDateTime loggedInTime = ClientStageCoordinator.getInstance().loggedInTime;
            singleChatMessagesList = singleChatDao.getSingleChatMessages(singleChatId);
            if (singleChatMessagesList.size() > 0) {
                for (SingleChatMessage singleChatMessage : singleChatMessagesList) {
                    if (singleChatMessage.getMessageDateTime().isAfter(loggedInTime)) {
                        System.out.println("single chat message " + singleChatMessage + "\n singlechat message time stamp " + singleChatMessage.getMessageDateTime());
                        System.out.println("loggedInTime" + loggedInTime);
                        if (singleChatMessage != null) {
                            System.out.println(singleChatMessage);
                            singleChatMessageFilteredList.add(singleChatMessage);
                        }

                    }
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return singleChatMessageFilteredList;
    }

    public void generateXml(List<SingleChatMessage> singleChatMessageList) {
        try {
            JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);

            eg.gov.iti.jets.models.singleChat.SingleChat singleChat = new eg.gov.iti.jets.models.singleChat.SingleChat();
            //  eg.gov.iti.jets.models.singleChat.SingleChat.SingleChatMessage singleChatMessage1 = new eg.gov.iti.jets.models.singleChat.SingleChat.SingleChatMessage();
            List<eg.gov.iti.jets.models.singleChat.SingleChat.SingleChatMessage> singleChatMessageList1 = singleChat.getSingleChatMessage();
            for (int i = 0; i < singleChatMessageList.size(); i++) {
//                LocalDateTime date = singleChatMessageList.get(i).getMessageDateTime();
//                GregorianCalendar gcal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
//                XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
                eg.gov.iti.jets.models.singleChat.SingleChat.SingleChatMessage singleChatMessage1 = new eg.gov.iti.jets.models.singleChat.SingleChat.SingleChatMessage();
                //   singleChatMessage1.setUserId(singleChatMessageList.get(i).getUserId());
                singleChatMessage1.setContent(singleChatMessageList.get(i).getContent());
                singleChatMessageList1.add(singleChatMessage1);
            }
            eg.gov.iti.jets.models.singleChat.SingleChat singleChat1 = new eg.gov.iti.jets.models.singleChat.SingleChat();
            singleChat1.getSingleChatMessage().addAll(singleChatMessageList1);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = null;
            try {
                schema = schemaFactory.newSchema(new File("single-chat-message.xsd"));
            } catch (SAXException e) {
                e.printStackTrace();
            }
            marshaller.setSchema(schema);
            marshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml-stylesheet type='text/xsl' href='test1.xsl'?>");
            try {
                marshaller.marshal(singleChat1, new FileOutputStream("chat-out1.xml"));
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        } catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}