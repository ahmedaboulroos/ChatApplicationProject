package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.GroupChatDao;
import eg.gov.iti.jets.models.entities.GroupChat;
import eg.gov.iti.jets.models.persistence.DBConnection;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupChatDaoImplTest {
    static GroupChatDao groupChatDao;
    static Connection connection;
    private static ArrayList<String> testFileNames = new ArrayList<>();
    private String testFilesDirectoryPath = "TestResources/JavaConversionTestFiles";

    @org.junit.jupiter.api.BeforeAll
    static void setUp() throws SQLException, RemoteException {
//        testFileNames.add("resources/GroupChatDaoImplTests");
//        DBConnection.getInstance().initConnection();
//        connection = DBConnection.getInstance().getConnection();
//        groupChatDao = new GroupChatDaoImpl();
    }

    @org.junit.jupiter.api.AfterAll
    static void tearDown() {
        DBConnection.getInstance().stopConnection();
    }

    @org.junit.jupiter.api.Test
    void getInstance() {
    }

    @org.junit.jupiter.api.Test
    void createGroupChat() {
        GroupChat groupChat;
        try {
            Image img = new Image(new FileInputStream("hh.png"));
            LocalDateTime time = LocalDateTime.now();
            groupChat = new GroupChat("title", "description", img, time);
            assertTrue(groupChatDao.createGroupChat(groupChat));
            System.out.println(groupChat);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getGroupChat() {
    }

    @Test
    void getGroupChatMemberships() {
    }

    @Test
    void getGroupChatUsers() {
    }

    @Test
    void getGroupChatMessages() {
    }

    @Test
    void updateGroupChat() {
    }

    @Test
    void deleteGroupChat() {
    }
}
