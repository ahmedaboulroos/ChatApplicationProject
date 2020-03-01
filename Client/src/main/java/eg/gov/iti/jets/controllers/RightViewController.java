package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import eg.gov.iti.jets.models.dao.interfaces.RelationshipDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.Relationship;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RightViewController implements Initializable {
    private static RightViewController rightViewController;
    @FXML
    public BorderPane rightViewBp;
    @FXML
    public JFXButton requestsBtn;
    @FXML
    Circle requestsNotifCircle;
    ListView<Relationship> relationshipLv = new ListView<>();
    private UserDao userDao = RMIConnection.getUserDao();
    private RelationshipDao relationshipDao = RMIConnection.getInstance().getRelationshipDao();
    private List<Relationship> relationshipList;
    private User user = ClientStageCoordinator.getInstance().currentUser;

    public static RightViewController getInstance() {
        return rightViewController;
    }

    public void setController(RightViewController rightViewController) {
        RightViewController.rightViewController = rightViewController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            relationshipList = userDao.getUserRelationships(user.getId());
        } catch (RemoteException e) {
            relationshipList = new ArrayList<>();
            e.printStackTrace();
        }

        relationshipLv = new ListView<>();
        rightViewBp.setCenter(relationshipLv);
        relationshipLv.setCellFactory(listViewListCellCallback -> new ListCell<>() {
            @Override
            protected void updateItem(Relationship relationship, boolean empty) {
                super.updateItem(relationship, empty);
                if (relationship != null) {
                    HBox hBox = new HBox();
                    User user = null;
                    int userId;
                    // shows notification of accaptance
                    if (relationship.getStatus() == RelationshipStatus.ACCEPTED) {
                        if (relationship.getFirstUserId() == ClientStageCoordinator.getInstance().currentUser.getId())
                            userId = relationship.getSecondUserId();
                        else
                            userId = relationship.getFirstUserId();
                        try {
                            user = userDao.getUser(userId);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        Text content = new Text("You are now friends with " + getUserDisplayName(user));
                        hBox.getChildren().addAll(content);
                        setGraphic(hBox);
                    } else if (relationship.getStatus() == RelationshipStatus.REJECTED) {


                        if (relationship.getFirstUserId() == ClientStageCoordinator.getInstance().currentUser.getId()) {
                            userId = relationship.getSecondUserId();
                            try {
                                user = userDao.getUser(userId);
                                Text content = new Text("your requset rejected by  :" + getUserDisplayName(user));
                                hBox.getChildren().addAll(content);
                                setGraphic(hBox);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    // shows notfication of frindship request
                    else if (relationship.getStatus() == RelationshipStatus.PENDING) {
                        if (relationship.getFirstUserId() == ClientStageCoordinator.getInstance().currentUser.getId()) {
                            userId = relationship.getSecondUserId();
                            try {
                                user = userDao.getUser(userId);
                                Text content = new Text("Friend request sent to " + getUserDisplayName(user));
                                hBox.getChildren().addAll(content);
                                setGraphic(hBox);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        } else {
                            userId = relationship.getFirstUserId();
                            try {
                                user = userDao.getUser(userId);
                                Text content = new Text(getUserDisplayName(user) + " sent you a friend request.");

                                JFXButton accept = new JFXButton("Accept");
                                accept.setStyle("-fx-background-color: #4F33FF");
                                JFXButton reject = new JFXButton("Reject");
                                reject.setStyle("-fx-background-color: #ECF0F1");
                                JFXButton block = new JFXButton("Block");
                                block.setStyle("-fx-background-color: #ffcccb");
                                accept.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        try {
                                            relationship.setStatus(RelationshipStatus.ACCEPTED);
                                            relationshipDao.updateRelationship(relationship);
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                reject.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        try {

                                            relationship.setStatus(RelationshipStatus.REJECTED);
                                            relationshipDao.updateRelationship(relationship);
                                            //relationshipDao.deleteRelationship(relationship.getId());

                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                block.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        try {
                                            relationship.setStatus(RelationshipStatus.BLOCKED);
                                            relationshipDao.updateRelationship(relationship);
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                                HBox hboxForButton = new HBox(accept, reject, block);
                                hboxForButton.setSpacing(20);

                                VBox vBox = new VBox(content, hboxForButton);
                                setGraphic(vBox);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    setGraphic(null);
                }
                setText(null);
            }
        });
        relationshipLv.setItems(FXCollections.observableList(relationshipList));
    }

    boolean flag = true;
    Node node = null;

    @FXML
    public void handleRequestsBtn(ActionEvent event) {
        if (flag) {
            node = rightViewBp.getCenter();
            requestsNotifCircle.setVisible(false);
            relationshipLv.setItems(FXCollections.observableList(relationshipList));
            rightViewBp.setCenter(relationshipLv);
        } else {
            rightViewBp.setCenter(node);
        }
        flag = !flag;
    }

    private String getUserDisplayName(User user) {
        return user.getUsername() == null ? user.getPhoneNumber() : user.getUsername();
    }

    public void displayRelationship(int relationshipId) {
        Relationship relationship = null;
        try {
            relationship = relationshipDao.getRelationship(relationshipId);
            System.out.println(relationship.getFirstUserId());
            relationshipList.add(relationship);
            requestsNotifCircle.setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}