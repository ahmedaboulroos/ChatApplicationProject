package eg.gov.iti.jets.controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RightViewController implements Initializable {
    private static RightViewController rightViewController;
    @FXML
    public BorderPane rightViewBp;
    boolean isActiveTap;
import eg.gov.iti.jets.models.entities.Relationship;

import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RightViewController {

    public static RightViewController getInstance() {
        return rightViewController;
    }

    public void setController(RightViewController rightViewController) {
        this.rightViewController = rightViewController;
        System.out.println("right view controller etsat");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println("ana omt");

        //  rightViewBp.setCenter(addContactView());

    }
//    private ListView<Relationship>relationShipLv=new ListView<Relationship>();
//
//
//
//    VBox vbox = new VBox();
//
//    //relationShipLv.getItems().add(Relationship);
//
// relationShipLv.setCellFactory(listViewListCellCallback -> new JFXListCell<>() {
//        @Override
//        protected void updateItem(Relationship relationship, boolean empty) {
//            super.updateItem(relationship, empty);
//            if ( relationShip!= null) {
//
//                if (relationship.getStatus() == RelationshipStatus.ACCEPTED) {
//
//                    Text content = new Text(relationship.get + " send Friend Request To You")
//                    vbox.getChildren().addAll(content);
//                } else {
//                }
//
//
//
//                setGraphic(vbox);
//            } else {
//                setGraphic(null);
//            }
//            setText(null);
//        }
//    });

}
