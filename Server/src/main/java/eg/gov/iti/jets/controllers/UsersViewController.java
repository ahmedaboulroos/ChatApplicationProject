package eg.gov.iti.jets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.implementations.UserDaoImpl;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;
import eg.gov.iti.jets.models.dto.UserViewModel;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.UserGender;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class UsersViewController implements Initializable {
    UserDao userDao = UserDaoImpl.getInstance();

    @FXML
    JFXButton registerBtn;
    @FXML
    private JFXTextField phoneNoTf;
    @FXML
    private JFXTextField usernameTf;
    @FXML
    private JFXTextField passwordTf;
    @FXML
    private JFXTextField emailTf;
    @FXML
    private JFXTextField countryTf;
    @FXML
    private TableColumn<UserViewModel, String> phoneNoCol;
    @FXML
    private TableColumn<UserViewModel, String> userNameCol;
    @FXML
    private TableColumn<UserViewModel, String> passCol;
    @FXML
    private JFXTextField bioTf;
    @FXML
    private JFXTextField birthDateTf;
    @FXML
    private JFXTextField userGenderTf;
    @FXML
    private TableColumn<UserViewModel, String> EmailCol;
    List<UserViewModel> userViewModelList;
    @FXML
    private TableView<User> userViewTv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        registerBtn.setOnAction(eh -> {
            registerUser();
            userViewTv.refresh();
        });

        try {

            userViewTv.setItems(FXCollections.observableList(userDao.getAllUsers()));
            phoneNoCol.setCellValueFactory(new PropertyValueFactory<UserViewModel, String>("phoneNumber"));
            userNameCol.setCellValueFactory(new PropertyValueFactory<UserViewModel, String>("username"));
            passCol.setCellValueFactory(new PropertyValueFactory<UserViewModel, String>("password"));
            EmailCol.setCellValueFactory(new PropertyValueFactory<UserViewModel, String>("email"));

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void registerUser() {
        try {
            UserDaoImpl userDao = new UserDaoImpl();
            User user = createUserFromView();
            userDao.createUser(user);
            userViewModelList.add(new UserViewModel(user));

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private User createUserFromView() {
        String phoneNo = getTextFromTextField(phoneNoTf);
        String username = getTextFromTextField(usernameTf);
        String password = getTextFromTextField(passwordTf);
        String email = getTextFromTextField(emailTf);
        String country = getTextFromTextField(countryTf);
        String bio = getTextFromTextField(bioTf);
        String birthDate = getTextFromTextField(birthDateTf);
        String userGender = getTextFromTextField(userGenderTf);

        User user = new User(phoneNo, password);
        user.setUsername(username);
        user.setEmail(email);
        user.setCountry(country);
        user.setUserGender(userGender == null ? null : UserGender.valueOf(userGender));
        user.setBio(bio);
        user.setBirthDate(getTempLocalDate(birthDate));

        return user;
    }

    private LocalDate getTempLocalDate(String birthDate) {
        if (birthDate == null)
            return null;
        int day = Integer.parseInt(birthDate.substring(0, birthDate.indexOf('-')));
        int month = Integer.parseInt(birthDate.substring(birthDate.indexOf('-') + 1,
                birthDate.lastIndexOf('-')));
        int year = Integer.parseInt(birthDate.substring(birthDate.lastIndexOf('-') + 1));
        return LocalDate.of(year, month, day);
    }

    private String getTextFromTextField(JFXTextField jfxTextField) {
        return jfxTextField.getText().length() > 0 ?
                jfxTextField.getText() : null;
    }

}
