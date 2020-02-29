package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import eg.gov.iti.jets.models.dao.implementations.UserDaoImpl;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.UserGender;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import eg.gov.iti.jets.models.imageutiles.ImageUtiles;
import eg.gov.iti.jets.models.persistence.DBConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class UsersViewController implements Initializable {
    UserDao userDao = UserDaoImpl.getInstance(DBConnection.getConnection());

    File userImagePath;
    byte[] imageBytes;

    @FXML
    private JFXButton selectedImageBtn;
    @FXML
    private TextField phoneNoTf;
    @FXML
    private TextField usernameTf;
    @FXML
    private PasswordField passwordPf;
    @FXML
    private TextField emailTf;
    @FXML
    private TextField countryTf;

    @FXML
    private TableColumn<User, String> phoneNoCol;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> genderCol;
    @FXML
    private TableColumn<User, String> countryCol;
    @FXML
    private TableColumn<User, String> statusCol;
    @FXML
    private TableColumn<User, String> birthDateCol;

    @FXML
    private TableView<User> userViewTv;
    @FXML
    private ImageView userProfileImageView;
    @FXML
    private Label validationLbl;

    @FXML
    private TextArea bioTa;
    @FXML
    private DatePicker birthDateDp;
    @FXML
    private ComboBox<UserGender> userGenderCb;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearFieldsData();
        initUsersTableColumns();
        updateUsersTable();
        //userProfileImageView.setImage(new Image("/images/user.png"));
        userGenderCb.getItems().addAll(UserGender.MALE, UserGender.FEMALE);
    }

    private void initUsersTableColumns() {
        phoneNoCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("userGender"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("userStatus"));
        birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
    }

    @FXML
    void handleRegisterBtn(ActionEvent event) {
        try {
            if (validUserInputFields()) {
                String phoneNumber = phoneNoTf.getText();
                String username = usernameTf.getText();
                String password = passwordPf.getText();
                String email = emailTf.getText();
                LocalDate birthDate = birthDateDp.getValue();
                UserGender gender = userGenderCb.getSelectionModel().getSelectedItem();
                String country = countryTf.getText();
                String bio = bioTa.getText();
                UserStatus status = UserStatus.AVAILABLE;
                if (imageBytes == null) {
                    URL res = getClass().getClassLoader().getResource("images/user.png");
                    File file = Paths.get(res.toURI()).toFile();
                    String absolutePath = file.getAbsolutePath();
                    System.out.println(">> imageBytes == null");
                    imageBytes = ImageUtiles.fromImageToBytes(absolutePath);
                    System.out.println(imageBytes);
                }
                User user = new User(phoneNumber, username, password, email, country, bio, birthDate, gender, imageBytes, status);
                int userId = userDao.createUser(user);
                successfulUserRegistration(userId, username);
            }
        } catch (RemoteException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleResetBtn(ActionEvent event) {
        clearFieldsData();
    }

    @FXML
    void handleSelectImageBtn(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
            userImagePath = fileChooser.showOpenDialog(null);
            if (userImagePath != null) {
                imageBytes = ImageUtiles.fromImageToBytes(userImagePath.getAbsolutePath());
                selectedImageBtn.setText("Selected Image ( " + userImagePath.getName() + " )");
                userProfileImageView.setImage(new Image(userImagePath.toURI().toURL().toString()));
            } else {
                userProfileImageView.setImage(new Image("/images/user.png"));
                selectedImageBtn.setText("Selected Image ( Default )");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void updateUsersTable() {
        try {
            List<User> allUsers = userDao.getAllUsers();
            if (allUsers != null) {
                userViewTv.setItems(FXCollections.observableList(allUsers));
            } else {
                System.out.println("no users found");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void clearFieldsData() {
        phoneNoTf.clear();
        phoneNoTf.setStyle("-fx-background-color: white");
        usernameTf.clear();
        usernameTf.setStyle("-fx-background-color: white");
        passwordPf.clear();
        passwordPf.setStyle("-fx-background-color: white");
        emailTf.clear();
        emailTf.setStyle("-fx-background-color: white");
        countryTf.clear();
        countryTf.setStyle("-fx-background-color: white");
        bioTa.clear();
        bioTa.setStyle("-fx-background-color: white");
        birthDateDp.setValue(null);
        userGenderCb.getSelectionModel().clearSelection();
        selectedImageBtn.setText("Selected Image ( Default )");
        //userProfileImageView.setImage(new Image("/images/user.png"));
        validationLbl.setText("");
    }

    void successfulUserRegistration(int userId, String username) {
        Notifications.create()
                .owner(ServerStageCoordinator.getStage())
                .title("User Registration")
                .text("User(" + userId + ") Successfully Created: " + username)
                .position(Pos.BOTTOM_RIGHT)
                .showInformation();
        clearFieldsData();
//        updateUsersTable();
    }

    boolean validUserInputFields() {
        boolean valid = true;
        if (phoneNoTf.getText().equals("")) {
            phoneNoTf.setStyle("-fx-background-color: lightpink");
            valid = false;
        }
        if (passwordPf.getText().equals("")) {
            passwordPf.setStyle("-fx-background-color: lightpink");
            valid = false;
        }
        if (!valid) {
            validationLbl.setText("Fill in Required Fields");
        }
        return valid;
    }

}
