package eg.gov.iti.jets.views;

import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.network.RMIConnection;
import eg.gov.iti.jets.models.network.implementations.ClientService;
import eg.gov.iti.jets.models.network.interfaces.ClientInterface;
import eg.gov.iti.jets.models.network.interfaces.ServerInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientStageCoordinator {

    private static ClientStageCoordinator instance;
    public User currentUser;
    private Stage stage;
    private ChatAppViewController chatAppViewController;

    private ClientStageCoordinator() {
    }

    public static ClientStageCoordinator getInstance() {
        if (instance == null) {
            instance = new ClientStageCoordinator();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void startMainChatAppScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ChatAppView.fxml"));
        Parent mainChatAppView = fxmlLoader.load();
        ServerInterface serverService = RMIConnection.getInstance().getServerService();
        ChatAppViewController chatAppViewController = fxmlLoader.getController();
        ClientInterface clientService = new ClientService(chatAppViewController);
        serverService.login(currentUser.getUserId(), clientService);

        /*AnnouncementController announcementController = new AnnouncementController(chatAppViewController);
        AnnouncementDeliveryController announcementDeliveryController = new AnnouncementDeliveryController(chatAppViewController);
        GroupChatController groupChatController = new GroupChatController(chatAppViewController);
        GroupChatMessageController groupChatMessageController = new GroupChatMessageController(chatAppViewController);
        GroupContactController groupContactController = new GroupContactController(chatAppViewController);
        GroupController groupController = new GroupController(chatAppViewController);
        MembershipController membershipController = new MembershipController(chatAppViewController);
        RelationshipController relationshipController = new RelationshipController(chatAppViewController);
        SeenByStatusController seenByStatusController = new SeenByStatusController(chatAppViewController);
        SingleChatController singleChatController = new SingleChatController(chatAppViewController);
        SingleChatMessageController singleChatMessageController = new SingleChatMessageController(chatAppViewController);
        UserController userController = new UserController(chatAppViewController);*/
        /*ClientService clientService = new ClientService(announcementController,announcementDeliveryController,
                groupChatController, groupChatMessageController, groupContactController,
                groupController, membershipController, relationshipController, seenByStatusController,
                singleChatController, singleChatMessageController, userController);
        chatAppViewController.setControllers(announcementController,announcementDeliveryController,
                groupChatController, groupChatMessageController, groupContactController,
                groupController, membershipController, relationshipController, seenByStatusController,
                singleChatController, singleChatMessageController, userController);*/
        //clientService.displayMsg();
        this.stage.setScene(new Scene(mainChatAppView));
        this.stage.setTitle("Chat Application");
        this.stage.show();
    }

    public void startLoginScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
        Parent loginView = fxmlLoader.load();
        this.stage.setScene(new Scene(loginView));
        this.stage.setTitle("Login");
        this.stage.show();
    }

    public void startRegistrationScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/RegisterView.fxml"));
        Parent registrationView = fxmlLoader.load();
        this.stage.setScene(new Scene(registrationView));
        this.stage.setTitle("Registration");
        this.stage.show();
    }

    public void openNewSingleChat(int singleChatId) {
        chatAppViewController.openSingleChat(singleChatId);
    }

    public void openNewGroupChat(int groupChatId) {
        chatAppViewController.openGroupChat(groupChatId);
    }
}
