package eg.gov.iti.jets.models.dao.implementation;


import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.Membership;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.persistence.DBConnection;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class GroupChatDaoImpl implements GroupChatDao {

    public static void main(String[] args) {
        DBConnection.getInstance().initConnection();
        GroupChatDaoImpl groupChatImpl = new GroupChatDaoImpl();
        GroupChat g;
//        Image img = null;
//        try {
//            img = new Image(new FileInputStream("hh.png"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        LocalDateTime timestamp = LocalDateTime.now();
//        GroupChat g = new GroupChat(0, "alaa", "alaa", img);
//        boolean b = groupChatImpl.createGroupChat(g);
//        System.out.println(b);
        g = groupChatImpl.getGroupChat(1);
        System.out.println("id " + g.getGroupChatId()
                + ":: title " + g.getTitle() + ":: desc "
                + g.getDescription() + ":: image " + g.getGroupImage() + ":: time " + g.getCreationTimestamp());
    }

    @Override
    public boolean createGroupChat(GroupChat groupChat) {
        Connection connection = DBConnection.getInstance().getConnection();
        boolean b = false;
        try {
            String sql = "INSERT INTO group_chat (group_chat_id, title, description,group_image,creation_timestamp) VALUES (SEQ_GROUP_CHAT_ID.nextval,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, groupChat.getTitle());
            stmt.setString(2, groupChat.getDescription());
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            BufferedImage bImage = SwingFXUtils.fromFXImage(groupChat.getGroupImage(), null);
            ImageIO.write(bImage, "png", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());
            stmt.setBlob(3, fis);
            stmt.setTimestamp(4, Timestamp.valueOf(groupChat.getCreationTimestamp()));
            if (stmt.executeUpdate() != 0) {
                b = true;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return b;
    }

    @Override
    public GroupChat getGroupChat(int groupChatId) {
        Connection connection = DBConnection.getInstance().getConnection();
        ResultSet rs;
        int id = 0;
        String tilte = null;
        String description = null;
        Image group_image;
        LocalDateTime creation_time_stamp;
        Timestamp timestamp = null;
        InputStream in;
        Blob blob = null;
        BufferedImage imagen;
        GroupChat groupChat = null;
        try {
            String sql = "select group_chat_id, title, description, group_image, creation_timestamp from group_chat where group_chat_id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, groupChatId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("group_chat_id");
                tilte = rs.getString("title");
                description = rs.getString("description");
                blob = rs.getBlob("group_image");
                timestamp = rs.getTimestamp("creation_timestamp");
            }
            in = blob.getBinaryStream();
            imagen = ImageIO.read(in);
            group_image = SwingFXUtils.toFXImage(imagen, null);
            creation_time_stamp = timestamp.toLocalDateTime();
            groupChat = new GroupChat(id, tilte, description, group_image, creation_time_stamp);

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return groupChat;
    }

    @Override
    public List<Membership> getGroupChatMemberships(int groupChatId) {
        return null;
    }

    @Override
    public List<User> getGroupChatUsers(int groupChatId) {
        return null;
    }

    @Override
    public List<GroupChatMessage> getGroupChatMessages(int groupChatId) {
        return null;
    }

    @Override
    public boolean updateGroupChat(GroupChat groupChat) {
        return false;
    }

    @Override
    public boolean addGroupChatMessage(int groupMessageId) {
        return false;
    }

    @Override
    public boolean addGroupChatUser(int userId) {
        return false;
    }

    @Override
    public boolean deleteGroupChat(int groupChatId) {
        return false;
    }

}
