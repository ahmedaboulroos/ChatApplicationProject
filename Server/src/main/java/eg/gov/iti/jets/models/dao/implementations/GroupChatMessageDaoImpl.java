package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.GroupChatMessageDao;
import eg.gov.iti.jets.models.entities.GroupChatMessage;
import eg.gov.iti.jets.models.entities.SeenByStatus;
import eg.gov.iti.jets.models.persistence.DBConnection;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupChatMessageDaoImpl implements GroupChatMessageDao {
    Connection connection = DBConnection.getInstance().getConnection();

    public static  void  main(String[] args){
        DBConnection.getInstance().initConnection();
        GroupChatMessageDaoImpl groupChatMessageDaoImp= new GroupChatMessageDaoImpl();
        GroupChatMessage sm= new GroupChatMessage(5,"nour");
        groupChatMessageDaoImp.createGroupChatMessage(sm);



    }
    PreparedStatement stmt;

    ResultSet resultset;

    @Override
    public boolean createGroupChatMessage(GroupChatMessage groupChatMessage) {
        boolean b = false;
        try {
            //String sql = "INSERT INTO group_chat (group_chat_id, title, description,group_image,creation_time_stamp) VALUES (seq_group_chat.NEXTVAL,?,?,?,?)";
            String sql = "INSERT INTO  GROUP_CHAT_MESSAGE (GROUP_CHAT_MESSAGE_ID, USER_ID, CONTENT,MESSAGE_TIMESTAMP) VALUES (SEQ_USER_ID.nextval,?,?,?)";


            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, groupChatMessage.getUserId());
            stmt.setString(2, groupChatMessage.getContent());

            stmt.setTimestamp(3, Timestamp.valueOf(groupChatMessage.getMessageTimestamp()));
            if (stmt.executeUpdate()!=0) {
                b = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }


    @Override
    public GroupChatMessage getGroupChatMessage(int groupChatMessageId) {

        boolean b = false;
        GroupChatMessage groupChatMessage = null;
        try {


            PreparedStatement statement;

            statement = connection.prepareStatement("select * from group_chat_message  where groupMessageId=groupChatMessageId");


            resultset = statement.executeQuery();

            if (resultset.next()) {
                groupChatMessage = new GroupChatMessage(resultset.getInt("groupChatMessageId"), resultset.getString("Content"));

               return groupChatMessage;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List<SeenByStatus> getSeenByStatus(int groupChatMessageId) {
        List<SeenByStatus> list=new ArrayList<>();

        SeenByStatus seen = null;
        try {

            PreparedStatement statement;

            statement = connection.prepareStatement("select * from group_chat_message  where groupChatMessageId=groupChatMessageId");

            resultset = statement.executeQuery();


            while (resultset.next()) {
                seen = new SeenByStatus(resultset.getInt("groupChatMessageId"),resultset.getInt("userId"));

                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        @Override
    public boolean updateGroupChatMessage(GroupChatMessage groupChatMessage) {
        boolean b = false;
        try {
            //String sql = "INSERT INTO group_chat (group_chat_id, title, description,group_image,creation_time_stamp) VALUES (seq_group_chat.NEXTVAL,?,?,?,?)";
            String sql = "UPDATE group_chat_message SET groupMessageId=1 userId=? content=? creationTimestamp=?";

            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, groupChatMessage.getUserId());
            stmt.setString(2, groupChatMessage.getContent());

            stmt.setTimestamp(3, Timestamp.valueOf(groupChatMessage.getMessageTimestamp()));
            stmt.executeUpdate();
            System.out.println("Database updated successfully");
            if (stmt.execute()) {
                b = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }


    @Override
    public boolean deleteGroupChatMessage(int groupChatMessageId) {
        GroupChatMessage groupChatMessage = null;
        boolean b = false;
        try {
            resultset.moveToCurrentRow();
            String sql = "Delete from group_chat_message where SET groupMessageId=1 userId=? content=? creationTimestamp=?";

            resultset.deleteRow();
            System.out.println("Row Deleted");
            b = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }
}


