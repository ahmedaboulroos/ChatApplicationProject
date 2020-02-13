package eg.gov.iti.jets.views;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactListController extends ListView<String> implements Initializable {
    final FileChooser fileChooser = new FileChooser();
    public Image profileimage;
    Stage stages = new Stage();
    @javafx.fxml.FXML
    Circle circle;
    ObservableList<String> listview = FXCollections.observableArrayList("nour", "sara", "alaa");
    Image friendImage = new Image("images/35-512.png");
    Image profileImage = new Image("images/defultimage.png");
    @javafx.fxml.FXML
    private JFXListView<String> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        circle.setFill(new ImagePattern(profileImage));
        list.setItems(listview);
        list.setCellFactory(param -> new ListCell<String>() {
            ImageView imageview = new ImageView();

            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {

                    setText(null);
                    setGraphic(null);

                } else {
                    setText(name);
                    imageview.setImage(friendImage);
                    imageview.setFitHeight(20);
                    imageview.setFitWidth(20);

                    setGraphic(imageview);
                }
            }


        });
        Circle circle;

    }

    @FXML
    public void setImage(MouseEvent actionEvent) {

        File file = fileChooser.showOpenDialog(stages);
        if (file != null) {

            profileimage = new Image(file.toURI().toString());
            // System.out.println(imageView);
            // imageView.setImage(image1);
            System.out.println(profileimage);
            circle.setFill(new ImagePattern(profileimage));

        } else {
            System.out.println("no image selected");
        }
    }


}


