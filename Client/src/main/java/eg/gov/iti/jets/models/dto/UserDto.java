package eg.gov.iti.jets.models.dto;

import javafx.scene.image.Image;

public class UserDto {
    String username;
    Image profileImage;

    public UserDto(String username, Image profileImage) {
        this.username = username;
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public Image getProfileImage() {
        return profileImage;
    }
}
