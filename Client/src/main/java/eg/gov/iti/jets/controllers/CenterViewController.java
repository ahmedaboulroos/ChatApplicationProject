package eg.gov.iti.jets.controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CenterViewController {

    private static CenterViewController instance;
    @FXML
    private TabPane chatsTp;//chatsTp
    ContactInfoViewController contactInfoViewController;
    SingleChatViewController singleChatViewController = SingleChatViewController.getInstance();
    public static CenterViewController getInstance() {
        if (instance == null) {
            instance = new CenterViewController();
        }
        return instance;
    }

    private int singleChatId;
    public void addSingleChat(int singleChatId) {
        this.singleChatId = singleChatId;
        try {
            FXMLLoader centerViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/SingleChatView.fxml"));
            Parent singleChatView = centerViewFxmlLoader.load();
            singleChatViewController = centerViewFxmlLoader.getController();
            singleChatViewController.setController(singleChatViewController);
            singleChatViewController.setSingleChatMessages(singleChatId);
            Tab tab = new Tab(Integer.toString(singleChatId));
            Pane pane = loadContactView();
            contactInfoViewController.setContactInfo(singleChatId);
            tab.setOnClosed(new EventHandler<Event>() {
                @Override
                public void handle(Event arg0) {
                    RightViewController.getInstance().rightViewBp.setCenter(null);
                }
            });
            tab.setOnSelectionChanged(event -> {
                if (tab.isSelected()) {
                    System.out.println("Tab is Selected");
                    RightViewController.getInstance().rightViewBp.setCenter(pane);
                }
                if (!tab.isSelected()) {
                    RightViewController.getInstance().rightViewBp.setCenter(null);
                }
            });
            tab.setContent(singleChatView);
            chatsTp.getTabs().add(0, tab);
            chatsTp.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
            chatsTp.setOnMouseClicked(new EventHandler<Event>() {
                @Override
                public void handle(Event arg0) {
                    RightViewController.getInstance().rightViewBp.setCenter(pane);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pane loadContactView() {
        Pane pane = null;
        try {
            FXMLLoader contactInfoViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/ContactInfo.fxml"));
            Parent contactInfoView = contactInfoViewFxmlLoader.load();
            System.out.println("el view loaded");
            contactInfoViewController = contactInfoViewFxmlLoader.getController();
            contactInfoViewController.setController(contactInfoViewController);
            contactInfoViewController.setSingleChatId(singleChatId);
            pane = new Pane(contactInfoView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
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
