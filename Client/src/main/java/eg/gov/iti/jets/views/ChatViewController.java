package eg.gov.iti.jets.views;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ChatViewController implements Initializable {
    @FXML
    JFXListView<String> listView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String string, boolean empty) {
                        super.updateItem(string, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            Circle userChatImage = new Circle();
                            userChatImage.setRadius(30);
                            userChatImage.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/images/2.jpg"))));
                            setText(string);
                            setGraphic(userChatImage);
                        }
                    }
                };
            }
        });
        ObservableList<String> list = FXCollections.observableList(Arrays.asList("Hey", "Hello", "I'm Tuna! ;)"));
        listView.setItems(list);
    }

}
