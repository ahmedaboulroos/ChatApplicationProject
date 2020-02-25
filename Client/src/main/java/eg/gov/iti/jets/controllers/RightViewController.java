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

    public static RightViewController getInstance() {
        return rightViewController;
    }

    public void setController(RightViewController rightViewController) {
        this.rightViewController = rightViewController;
        System.out.println("right view controller etsat");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ana omt");

        //  rightViewBp.setCenter(addContactView());

    }

}
