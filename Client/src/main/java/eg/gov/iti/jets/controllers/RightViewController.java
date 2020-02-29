package eg.gov.iti.jets.controllers;

import eg.gov.iti.jets.models.dao.interfaces.RelationshipDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.Relationship;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.network.RMIConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class RightViewController implements Initializable {
    private static RightViewController rightViewController;
    @FXML
    public BorderPane rightViewBp;
    boolean isActiveTap;
    private ListView<Relationship> relationshipLv;
    private UserDao userDao = RMIConnection.getUserDao();
    private RelationshipDao relationshipDao = RMIConnection.getInstance().getRelationshipDao();


    public static RightViewController getInstance() {
        return rightViewController;
    }

    public void setController(RightViewController rightViewController) {
        RightViewController.rightViewController = rightViewController;
        System.out.println("right view controller etsat");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println("ana omt");

        //  rightViewBp.setCenter(addContactView());
        //System.out.println("ana omt");
        //rightViewBp.setCenter(addContactView());
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

                                Button accept = new Button("Accept");
                                Button reject = new Button("Reject");
                                Button block = new Button("Block");

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
                                            relationship.getSecondUserId();

                                            relationshipDao.deleteRelationship(relationship.getId());


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
    }

    private String getUserDisplayName(User user) {
        return user.getUsername() == null ? user.getPhoneNumber() : user.getUsername();
    }

    public void displayRelationship(int relationshipId) {

        Platform.runLater(() -> {
            Relationship relationship = null;
            try {
                relationship = relationshipDao.getRelationship(relationshipId);
                System.out.println(relationship.getFirstUserId());
                relationshipLv.getItems().add(relationship);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

    }

}
