package eg.gov.iti.jets.models.imageutiles;

import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageUtiles {

    public static Image convertBytesToImage(byte[] encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
        return new Image(bis);
    }

    public static byte[] convertImageToBytes(String imagePath) {
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
