package eg.gov.iti.jets.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class CenterViewController {

    private static CenterViewController instance;
    @FXML
    private TabPane chatsTp;//chatsTp

    public static CenterViewController getInstance() {
        if (instance == null) {
            instance = new CenterViewController();
        }
        return instance;
    }

    public void addSingleChat(int singleChatId) {
        try {
            FXMLLoader centerViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/SingleChatView.fxml"));
            Parent singleChatView = centerViewFxmlLoader.load();
            SingleChatViewController singleChatViewController = centerViewFxmlLoader.getController();
            singleChatViewController.setController(singleChatViewController);
            singleChatViewController.setSingleChatMessages(singleChatId);
            Tab tab = new Tab(Integer.toString(singleChatId));
            tab.setClosable(true);
            tab.setContent(singleChatView);
            chatsTp.getTabs().add(tab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addGroupChat(int groupChatId) {
        try {
            FXMLLoader centerViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/GroupChatView.fxml"));
            Parent groupChatView = centerViewFxmlLoader.load();
            GroupChatViewController groupChatViewController = centerViewFxmlLoader.getController();
            groupChatViewController.setGroupChatMessages(groupChatId);
            Tab tab = new Tab(Integer.toString(groupChatId));
            tab.setClosable(true);
            tab.setContent(groupChatView);
            chatsTp.getTabs().add(tab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
