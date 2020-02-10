package eg.gov.iti.jets.models.dao.implementation;


import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.Membership;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.persistence.DBConnection;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class GroupChatDaoImpl implements GroupChatDao {

    Connection connection = DBConnection.getInstance().getConnection();

    public static void main(String[] args) {
        GroupChatDaoImpl croupChat = new GroupChatDaoImpl();
        GroupChat g = new GroupChat();
        croupChat.createGroupChat();
    }

    @Override
    public boolean createGroupChat(GroupChat groupChat) {
        boolean b = false;
        try {
            //String sql = "INSERT INTO group_chat (group_chat_id, title, description,group_image,creation_time_stamp) VALUES (seq_group_chat.NEXTVAL,?,?,?,?)";
            String sql = "INSERT INTO group_chat (group_chat_id, title, description,group_image,creation_time_stamp) VALUES (1,?,?,?,?)";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(2, groupChat.getTitle());
            stmt.setString(3, groupChat.getDescription());
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            BufferedImage bImage = SwingFXUtils.fromFXImage(groupChat.getGroupImage(), null);
            ImageIO.write(bImage, "png", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());
            stmt.setBlob(4, fis);
            stmt.setTimestamp(5, Timestamp.valueOf(groupChat.getCreationTimestamp()));
            if (stmt.execute()) {
                b = true;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return b;
    }

    @Override
    public GroupChat getGroupChat(int groupChatId) {
        return null;
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
