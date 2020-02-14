package eg.gov.iti.jets.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import eg.gov.iti.jets.models.dao.implementations.UserDaoImpl;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.UserGender;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UsersViewController implements Initializable {

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
    private TableColumn<?, ?> phoneNoCol;

    @FXML
    private TableColumn<?, ?> userNameCol;

    @FXML
    private TableColumn<?, ?> passCol;

    @FXML
    private TableColumn<?, ?> EmailCol;
    @FXML
    private JFXTextField bioTf;
    @FXML
    private JFXTextField birthDateTf;
    @FXML
    private JFXTextField userGenderTf;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerBtn.setOnAction(eh -> {
            registerUser();
        });
    }

    private void registerUser() {
        try {
            UserDaoImpl userDao = new UserDaoImpl();
            User user = createUserFromView();
            userDao.createUser(user);
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
