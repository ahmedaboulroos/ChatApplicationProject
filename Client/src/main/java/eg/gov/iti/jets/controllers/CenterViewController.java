package eg.gov.iti.jets.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CenterViewController {

    private static CenterViewController instance;
    @FXML
    public BorderPane centerViewBp;
    /*@FXML
    private TabPane chatsTp;//chatsTp
     */
    ContactInfoViewController contactInfoViewController;
    GroupInfoViewController groupInfoViewController;
    SingleChatViewController singleChatViewController = SingleChatViewController.getInstance();
    private static CenterViewController centerViewController;

    public static CenterViewController getInstance() {
        return centerViewController;
    }

    public void setController(CenterViewController centerViewController) {
        CenterViewController.centerViewController = centerViewController;
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
            centerViewBp.setCenter(singleChatView);
            //Tab tab = new Tab(Integer.toString(singleChatId));
            Pane pane = loadContactView();
            RightViewController.getInstance().rightViewBp.setCenter(null);
            RightViewController.getInstance().rightViewBp.setCenter(pane);
            contactInfoViewController.setContactInfo(singleChatId);
            /*tab.setOnClosed(new EventHandler<Event>() {
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
            });*/
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

    private int groupChatId;

    public Pane loadGroupContactView() {
        Pane paneGroup = null;
        try {
            FXMLLoader groupInfoViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/GroupInfoView.fxml"));
            Parent contactInfoView = groupInfoViewFxmlLoader.load();
            groupInfoViewController = groupInfoViewFxmlLoader.getController();
            groupInfoViewController.setController(groupInfoViewController);
            System.out.println("load group contactView");
            groupInfoViewController.setGroupChatId(groupChatId);
            paneGroup = new Pane(contactInfoView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paneGroup;
    }

    public void displayGroupChat(int groupChatId) {
        this.groupChatId = groupChatId;
        try {
            FXMLLoader centerViewFxmlLoader = new FXMLLoader(getClass().getResource("/views/GroupChatView.fxml"));
            Parent groupChatView = centerViewFxmlLoader.load();
            GroupChatViewController groupChatViewController = centerViewFxmlLoader.getController();
            groupChatViewController.setController(groupChatViewController);
            groupChatViewController.setGroupChatMessages(groupChatId);
            centerViewBp.setCenter(groupChatView);
            Pane groupPane = loadGroupContactView();
            groupInfoViewController.setGroupInfo(groupChatId);
            RightViewController.getInstance().rightViewBp.setCenter(null);
            RightViewController.getInstance().rightViewBp.setCenter(groupPane);

            /*Tab tab = new Tab(Integer.toString(groupChatId));
            tab.setOnClosed(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    RightViewController.getInstance().rightViewBp.setCenter(null);
                }
            });
            tab.setOnSelectionChanged(event -> {
                if (tab.isSelected()) {
                    RightViewController.getInstance().rightViewBp.setCenter(groupPane);
                }
                if (!tab.isSelected()) {
                    RightViewController.getInstance().rightViewBp.setCenter(null);

                }

            });

            tab.setContent(groupChatView);
            chatsTp.getTabs().add(0, tab);
            chatsTp.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
            chatsTp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    RightViewController.getInstance().rightViewBp.setCenter(groupPane);

                }
            });*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
