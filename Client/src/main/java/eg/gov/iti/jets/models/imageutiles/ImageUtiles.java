package eg.gov.iti.jets.models.imageutiles;

import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

public class ImageUtiles {
    public static Blob fromBytesToBlob(byte[] bytesArr) {
        Blob blob = null;
        try {
            if (bytesArr != null)
                blob = new javax.sql.rowset.serial.SerialBlob(bytesArr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blob;

    }

    public static byte[] fromBlobToBytes(Blob blob) {
        int blobLength;
        byte[] blobAsBytes = null;
        try {
            if (blob != null) {
                blobLength = (int) blob.length();
                blobAsBytes = blob.getBytes(1, blobLength);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blobAsBytes;

    }

    public static Image fromBytesToImage(byte[] bytesArr) {
        Image image = null;
        if (bytesArr != null) {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytesArr);
            image = new Image(bis);
        }
        return image;
    }

    public static byte[] fromImageToBytes(String imagePath) {
        byte[] fileContent = null;
        try {
            File file = new File(imagePath);
            if (file.exists())
                fileContent = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

}
