package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXListCell;
import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.dao.interfaces.GroupChatMessageDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class GroupChatViewController implements Initializable {

    private User currentUser = ClientStageCoordinator.getInstance().currentUser;
    private GroupChatMessageDao groupChatMessageDao = RMIConnection.getGroupChatMessageDao();
    private UserDao userDao = RMIConnection.getUserDao();
    private int groupChatId;

    private static GroupChatViewController groupChatViewController;
    private static GroupChatViewController instance;
    private GroupChat groupChat;
    private GroupChatDao groupChatDao = RMIConnection.getGroupChatDao();
    @FXML
    private Circle imageCircle;
    @FXML
    private Label nameLbl;

    public static GroupChatViewController getInstance() {
        return groupChatViewController;
    }

    public void setController(GroupChatViewController groupChatViewController) {
        GroupChatViewController.groupChatViewController = groupChatViewController;
    }

    @FXML
    private ListView<GroupChatMessage> groupChatMessagesLv;

    @FXML
    private HTMLEditor groupChatMessageHtml;

    public void setGroupChatMessages(int groupChatId) {
        try {
            this.groupChatId = groupChatId;
            this.groupChat = groupChatDao.getGroupChat(groupChatId);
            List<GroupChatMessage> groupChatMessages = RMIConnection.getGroupChatDao().getGroupChatMessages(groupChatId);
            if (groupChatMessages != null)
                groupChatMessagesLv.setItems(FXCollections.observableList(groupChatMessages));
            updateGroupChat();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSendBtn(ActionEvent event) {
        sendMessage();
    }

    private void updateGroupChat() {
        try {
            byte[] imageBytes = groupChat.getGroupImageBytes();
            if (imageBytes == null) {
                File file = null;
                URL res = getClass().getClassLoader().getResource("images/group-icon.png");
                try {
                    file = Paths.get(res.toURI()).toFile();
                    String filePath = file.getAbsolutePath();
                    imageBytes = ImageUtiles.fromImageToBytes(filePath);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            Image image = ImageUtiles.fromBytesToImage(imageBytes);
            imageCircle.setFill(new ImagePattern(image));

            nameLbl.setText(groupChat.getTitle());
            groupChatMessagesLv.getItems().clear();
            List<GroupChatMessage> groupChatMessages = RMIConnection.getGroupChatDao().getGroupChatMessages(groupChatId);
            System.out.println("inside GroupChatViewController.setGroupChatMessages ===> groupChatMessages list size " + groupChatMessages.size());
            groupChatMessagesLv.setItems(FXCollections.observableList(groupChatMessages));
            if (groupChatMessages != null) {
                groupChatMessagesLv.setItems(FXCollections.observableList(groupChatMessages));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void addGroupChatMessage(GroupChatMessage groupChatMessage) {
            groupChatMessagesLv.getItems().add(groupChatMessage);
    }

    @FXML
    public void handleSaveSessionBtn(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupChatMessagesLv.setCellFactory(listViewListCellCallback -> new JFXListCell<>() {
            @Override
            protected void updateItem(GroupChatMessage message, boolean empty) {
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
                        Circle circle = new Circle();
                        byte[] imageBytes = user.getProfileImage();
                        if (imageBytes == null) {
                            File file = null;
                            URL res = getClass().getClassLoader().getResource("images/group-icon.png");
                            try {
                                file = Paths.get(res.toURI()).toFile();
                                String filePath = file.getAbsolutePath();
                                imageBytes = ImageUtiles.fromImageToBytes(filePath);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                        Image image = ImageUtiles.fromBytesToImage(imageBytes);
                        circle.setFill(new ImagePattern(image));
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
                        groupChatMessagesLv.scrollTo(groupChatMessagesLv.getItems().size() - 1);

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

    private void sendMessage() {
        try {
            String msg = groupChatMessageHtml.getHtmlText();
            System.out.println(msg);
            GroupChatMessageDao groupChatDao = RMIConnection.getGroupChatMessageDao();
            System.out.println("inside GroupchatViewController ==> this.groupChatId, currentUser.getId(), msg" + this.groupChatId + " " + currentUser.getId() + " " + msg);
            GroupChatMessage GroupChatMessage = new GroupChatMessage(currentUser.getId(), this.groupChatId, msg);
            groupChatDao.createGroupChatMessage(GroupChatMessage);
        } catch (
                RemoteException e) {
            e.printStackTrace();
        }
    }
}



