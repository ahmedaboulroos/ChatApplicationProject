package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXListView;
import eg.gov.iti.jets.models.dao.interfaces.SingleChatDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.Relationship;
import eg.gov.iti.jets.models.entities.SingleChat;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddSingleChatViewController implements Initializable {


    private static AddSingleChatViewController addSingleChatViewController;

    public static AddSingleChatViewController getInstance() {
        return addSingleChatViewController;
    }

    //    @FXML
//    private JFXComboBox<eg.gov.iti.jets.models.entities.User> usersCompoBox;
    @FXML
    private JFXListView<eg.gov.iti.jets.models.entities.User> userContactsLv;

    SingleChatDao singleChatDao = RMIConnection.getSingleChatDao();
    private User currentUser = ClientStageCoordinator.getInstance().currentUser;
    private int userTwoId;
    private ClientStageCoordinator clientStageCoordinator = ClientStageCoordinator.getInstance();
    private UserDao userDao = RMIConnection.getUserDao();

    public void setController(AddSingleChatViewController addSingleChatViewController) {
        AddSingleChatViewController.addSingleChatViewController = addSingleChatViewController;
    }
//    @FXML
//    void handleContactsCB(ActionEvent event) {
//        System.out.println(usersCompoBox.getSelectionModel().getSelectedItem());
//        User selectedItem = usersCompoBox.getSelectionModel().getSelectedItem();
//        System.out.println(selectedItem);
//        userTwoId = selectedItem.getId();
//
//        System.out.println(userTwoId);
//    }

    @FXML
    void handleCreateButton(ActionEvent event) {
        try {
            User selectedItem = userContactsLv.getSelectionModel().getSelectedItem();
            System.out.println(selectedItem);
            userTwoId = selectedItem.getId();
            SingleChat singleChat = new SingleChat(this.currentUser.getId(), userTwoId);
            System.out.println(singleChat + "my singleChat");
            singleChatDao.createSingleChat(singleChat);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        usersCompoBox.setPromptText("Choose Contact");
//        usersCompoBox.setEditable(false);
        List<User> contacts = loadContacts();
        ObservableList<User> options = FXCollections.observableList(contacts);
        userContactsLv.setItems(options);
        userContactsLv.setCellFactory(userContactsLv -> new ListCell<eg.gov.iti.jets.models.entities.User>() {
            // super.updateItem(item, empty);
            @Override
            public void updateItem(eg.gov.iti.jets.models.entities.User item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {
                    // User user = usersCompoBox.getItems().get(0);
                    User user = item;
                    HBox hBox = new HBox();
                    hBox.setStyle("-fx-background-color: transparent  ;" +
                            "-fx-padding: 1;" + "-fx-border-style: solid inside;"
                            + "-fx-border-width: 3;" + "-fx-border-insets: 1;"
                            + "-fx-border-radius: 2;" + "-fx-border-color: white;");
                    Circle imageCircle = new Circle();
                    Image imageForTasting = new Image("images/chat-circle-blue-512.png");
                    imageCircle.setFill(new ImagePattern(imageForTasting));
                    imageCircle.setRadius(20);
                    imageCircle.setStroke(Color.NAVY);
                    imageCircle.setStrokeWidth(1);
                    StackPane stackPane = new StackPane();
                    //Region region= new Region();
                    Region selectedBar = new Region();
                    // selectedBar.getStyleClass().add(HIGHLIGHT_STYLE_CLASS);
                    selectedBar.setMinWidth(Region.USE_PREF_SIZE);
                    selectedBar.setMaxHeight(Region.USE_PREF_SIZE);
                    selectedBar.setMaxWidth(Double.MAX_VALUE);
                    StackPane.setAlignment(selectedBar, Pos.BOTTOM_CENTER);
                    stackPane.getChildren().addAll(imageCircle, selectedBar);
                    String userInfo;
                    if (user.getUsername() == null) {
                        userInfo = user.getPhoneNumber();
                    } else {
                        userInfo = user.getUsername();
                    }
                    Text text = new Text(userInfo);
                    text.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 20));//FontWeight.BOLD
                    text.setFill(Color.NAVY);
                    Label label = new Label();
                    label.setMinWidth(20);
                    hBox.getChildren().addAll(stackPane, label, text);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    setPrefWidth(200);
                    setPrefHeight(60);
                    hBox.setMaxWidth(200);
                    hBox.setMinWidth(200);
                    setGraphic(hBox);
                } else {
                    setGraphic(null);
                }
            }
        });

    }

    private List<User> loadContacts() {
        User user = clientStageCoordinator.currentUser;
        List<User> friends = null;
        int userId = user.getId();
        try {
            List<Relationship> userRelationships = userDao.getUserRelationships(userId);
            if (userRelationships != null) {
                List<Integer> friendIds = new ArrayList<>();
                for (Relationship r : userRelationships) {
                    if (r.getStatus() == RelationshipStatus.ACCEPTED) {
                        int id = r.getFirstUserId();
                        if (id != user.getId())
                            friendIds.add(id);
                        else
                            friendIds.add(r.getSecondUserId());
                    }
                }
                friends = new ArrayList<>();
                for (Integer i : friendIds)
                    friends.add(userDao.getUser(i));
            } else {
                System.out.println("No Relationships");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return friends;
    }


}


