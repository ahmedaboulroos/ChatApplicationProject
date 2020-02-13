package eg.gov.iti.jets.models.entities;

import eg.gov.iti.jets.models.entities.enums.UserGender;
import eg.gov.iti.jets.models.entities.enums.UserStatus;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
    private int userId;
    private String phoneNumber;
    private String username;
    private String password;
    private String email;
    private String country;
    private String bio;
    private LocalDate birthDate;
    private UserGender userGender;
    private Image profileImage;
    private UserStatus userStatus;
    private boolean currentlyLoggedIn;

    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(int userId, String phoneNumber, String username, String password, String email, String country, String bio, LocalDate birthDate, UserGender userGender, Image profileImage, UserStatus userStatus, boolean currentlyLoggedIn) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.email = email;
        this.country = country;
        this.bio = bio;
        this.birthDate = birthDate;
        this.userGender = userGender;
        this.profileImage = profileImage;
        this.userStatus = userStatus;
        this.currentlyLoggedIn = currentlyLoggedIn;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public UserGender getUserGender() {
        return userGender;
    }

    public void setUserGender(UserGender userGender) {
        this.userGender = userGender;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isCurrentlyLoggedIn() {
        return currentlyLoggedIn;
    }

    public void setCurrentlyLoggedIn(boolean currentlyLoggedIn) {
        this.currentlyLoggedIn = currentlyLoggedIn;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", bio='" + bio + '\'' +
                ", birthDate=" + birthDate +
                ", userGender=" + userGender +
                ", profileImage=" + profileImage +
                ", userStatus=" + userStatus +
                ", currentlyLoggedIn=" + currentlyLoggedIn +
                '}';
    }
}
