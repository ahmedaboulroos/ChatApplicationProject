package eg.gov.iti.jets.models.imageutiles;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

public class ImageUtiles {
    public static Blob FromStringToBlob(String imageEncodedString) {
        Blob blob = null;
        byte[] bytesArr = imageEncodedString.getBytes();
        try {
            blob = new javax.sql.rowset.serial.SerialBlob(bytesArr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blob;

    }

    public static String FromBlobToString(Blob blob) {
        int blobLength;
        byte[] blobAsBytes = new byte[0];
        try {
            blobLength = (int) blob.length();
            blobAsBytes = blob.getBytes(1, blobLength);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String(blobAsBytes);

    }

    public static Image FromBytesToImage(byte[] bytesArr) {
        byte[] decodedBytes = Base64.getDecoder().decode(bytesArr);
        ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
        return new Image(bis);
    }

}
