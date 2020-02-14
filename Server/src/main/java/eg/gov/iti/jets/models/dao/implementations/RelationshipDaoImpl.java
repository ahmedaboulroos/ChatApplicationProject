package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.RelationshipDao;
import eg.gov.iti.jets.models.entities.Relationship;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.persistence.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationshipDaoImpl extends UnicastRemoteObject implements RelationshipDao {

//    public static void main(String[] args) throws RemoteException {
//        RelationshipDaoImpl relationshipDao = new RelationshipDaoImpl();
//        DBConnection.getInstance().initConnection();
//        /*Relationship relationship = new Relationship(5,41);
//        System.out.println(relationshipDao.createRelationship(relationship));
//        System.out.println(relationship);*/
//        //System.out.println(relationshipDao.deleteRelationship(43));
//        //System.out.println(relationshipDao.getRelationshipBetween(41,2));
//        System.out.println(relationshipDao.getRelationship(42));
//        DBConnection.getInstance().stopConnection();
//    }

    protected RelationshipDaoImpl() throws RemoteException {
    }

    @Override
    public boolean createRelationship(Relationship relationship) {
        Connection connection = DBConnection.getInstance().getConnection();
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select SEQ_RELATIONSHIP_ID.nextval from DUAL");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int relationshipId = resultSet.getInt(1);
                relationship.setRelationshipId(relationshipId);
                preparedStatement = connection.prepareStatement(
                        "insert into RELATIONSHIP (RELATIONSHIP_ID, FIRST_USER_ID," +
                                " SECOND_USER_ID, RELATIONSHIP_STATUS)" +
                                " values (?,?,?,?)");
                preparedStatement.setInt(1, relationshipId);
                preparedStatement.setInt(2, relationship.getFirstUserId());
                preparedStatement.setInt(3, relationship.getSecondUserId());
                preparedStatement.setString(4, relationship.getRelationshipStatus().toString());
                result = preparedStatement.executeUpdate();
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    @Override
    public List<User> getRelationshipTwoUsers(int relationshipId) {
        Connection connection = DBConnection.getInstance().getConnection();
        List<User> users = new ArrayList<>();
        int[] userIDs = new int[2];
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select FIRST_USER_ID, SECOND_USER_ID from RELATIONSHIP where RELATIONSHIP_ID = " + relationshipId);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (resultSet.next()) {
                userIDs[0] = resultSet.getInt("FIRST_USER_ID");
                userIDs[1] = resultSet.getInt("SECOND_USER_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            UserDaoImpl userDao = new UserDaoImpl();
            users.add(userDao.getUser(userIDs[0]));
            users.add(userDao.getUser(userIDs[1]));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (users.isEmpty())
            users = null;
        return users;
    }

    @Override
    public Relationship getRelationship(int relationshipId) {
        Connection connection = DBConnection.getInstance().getConnection();
        Relationship relationship = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from RELATIONSHIP where RELATIONSHIP_ID = " + relationshipId);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (resultSet.next())
                relationship = getRelationshipFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relationship;
    }

    @Override
    public Relationship getRelationshipBetween(int firstUserId, int secondUserId) {
        Connection connection = DBConnection.getInstance().getConnection();
        Relationship relationship = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from RELATIONSHIP where (FIRST_USER_ID = " + firstUserId
                        + " and SECOND_USER_ID = " + secondUserId + ")"
                        + " or (SECOND_USER_ID = " + firstUserId
                        + " and FIRST_USER_ID = " + secondUserId + ")");
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (resultSet.next()) {
                relationship = getRelationshipFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relationship;
    }

    @Override
    public boolean updateRelationship(Relationship relationship) {
        Connection connection = DBConnection.getInstance().getConnection();
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update RELATIONSHIP set" +
                            " FIRST_USER_ID = ?," +
                            " SECOND_USER_ID = ?," +
                            " RELATIONSHIP_STATUS = ?," +
                            " where RELATIONSHIP_ID = " + relationship.getRelationshipId());
            preparedStatement.setInt(1, relationship.getFirstUserId());
            preparedStatement.setInt(2, relationship.getSecondUserId());
            preparedStatement.setString(3, relationship.getRelationshipStatus().toString());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    @Override
    public boolean deleteRelationship(int relationshipId) {
        Connection connection = DBConnection.getInstance().getConnection();
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from RELATIONSHIP where RELATIONSHIP_ID = " + relationshipId);
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    private Relationship getRelationshipFromResultSet(ResultSet resultSet) throws SQLException {
        RelationshipStatus relationshipStatus =
                RelationshipStatus.valueOf(resultSet.getString("RELATIONSHIP_STATUS"));
        return new Relationship(
                resultSet.getInt("RELATIONSHIP_ID"),
                resultSet.getInt("FIRST_USER_ID"),
                resultSet.getInt("SECOND_USER_ID"),
                relationshipStatus
        );
    }

}
