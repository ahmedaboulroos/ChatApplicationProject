package eg.gov.iti.jets.models.imageutiles;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class ImageUtiles {

    public static Image FromBytesToImage(byte[] encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
        return new Image(bis);
    }
}
