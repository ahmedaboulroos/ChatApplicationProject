package eg.gov.iti.jets.views.models;

import eg.gov.iti.jets.models.entities.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserViewModel {


    private SimpleIntegerProperty userId;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty email;
    private SimpleStringProperty country;
    private SimpleStringProperty bio;
    //    private LocalDate birthDate;
//    private UserGender userGender;
//    private Image profileImage;
//    private UserStatus userStatus;
    private SimpleBooleanProperty currentlyLoggedIn;

    public UserViewModel(int userId, String phoneNumber, String username, String password, String email, String country, String bio, boolean currentlyLoggedIn) {
        this.userId = new SimpleIntegerProperty(userId);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.country = new SimpleStringProperty(country);
        this.bio = new SimpleStringProperty(bio);
        this.currentlyLoggedIn = new SimpleBooleanProperty(currentlyLoggedIn);
    }

    public UserViewModel(User user) {
        this.userId = new SimpleIntegerProperty(user.getUserId());
        this.phoneNumber = new SimpleStringProperty(user.getPhoneNumber());
        this.username = new SimpleStringProperty(user.getUsername());
        this.password = new SimpleStringProperty(user.getPassword());
        this.email = new SimpleStringProperty(user.getEmail());
        this.country = new SimpleStringProperty(user.getCountry());
        this.bio = new SimpleStringProperty(user.getBio());
        this.currentlyLoggedIn = new SimpleBooleanProperty(user.isCurrentlyLoggedIn());

    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getBio() {
        return bio.get();
    }

    public void setBio(String bio) {
        this.bio.set(bio);
    }

    public boolean isCurrentlyLoggedIn() {
        return currentlyLoggedIn.get();
    }

    public void setCurrentlyLoggedIn(boolean currentlyLoggedIn) {
        this.currentlyLoggedIn.set(currentlyLoggedIn);
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public SimpleStringProperty countryProperty() {
        return country;
    }

    public SimpleStringProperty bioProperty() {
        return bio;
    }

    public SimpleBooleanProperty currentlyLoggedInProperty() {
        return currentlyLoggedIn;
    }


    @Override
    public String toString() {
        return "UserViewModel{" +
                "userId=" + userId +
                ", phoneNumber=" + phoneNumber +
                ", username=" + username +
                ", password=" + password +
                ", email=" + email +
                ", country=" + country +
                ", bio=" + bio +
                ", currentlyLoggedIn=" + currentlyLoggedIn +
                '}';
    }

}
