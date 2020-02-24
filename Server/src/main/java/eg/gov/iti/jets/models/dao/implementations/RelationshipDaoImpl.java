package eg.gov.iti.jets.models.dao.implementations;

import eg.gov.iti.jets.models.dao.interfaces.RelationshipDao;
import eg.gov.iti.jets.models.dao.interfaces.UserDao;

import eg.gov.iti.jets.models.entities.Relationship;
import eg.gov.iti.jets.models.entities.User;
import eg.gov.iti.jets.models.entities.enums.RelationshipStatus;
import eg.gov.iti.jets.models.network.implementations.ServerService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationshipDaoImpl extends UnicastRemoteObject implements RelationshipDao {

    private static RelationshipDaoImpl instance;
    private static Connection dbConnection;

    protected RelationshipDaoImpl() throws RemoteException {
    }

    public static RelationshipDao getInstance(Connection connection) {
        if (instance == null) {
            try {
                instance = new RelationshipDaoImpl();
                dbConnection = connection;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public int createRelationship(Relationship relationship) {
        int id = -1;
        String[] key = {"ID"};
        String sql = "insert into RELATIONSHIPS (ID, FIRST_USER_ID, SECOND_USER_ID, STATUS) values (ID_SEQ.NEXTVAL,?,?,?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, key)) {
            ps.setInt(1, relationship.getFirstUserId());
            ps.setInt(2, relationship.getSecondUserId());
            ps.setString(3, relationship.getStatus().toString());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            ServerService.getClient(relationship.getFirstUserId()).receiveNewRelationship(id);
            ServerService.getClient(relationship.getSecondUserId()).receiveNewRelationship(id);
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public List<User> getRelationshipTwoUsers(int relationshipId) {
        String sql = "select FIRST_USER_ID, SECOND_USER_ID from RELATIONSHIPS where ID = ?";
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, relationshipId);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                users.add(UserDaoImpl.getInstance(dbConnection).getUser(rs.getInt(1)));
                users.add(UserDaoImpl.getInstance(dbConnection).getUser(rs.getInt(2)));
            }
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Relationship getRelationship(int relationshipId) {
        Relationship relationship = null;
        String sql = "select ID, FIRST_USER_ID, SECOND_USER_ID, STATUS from RELATIONSHIPS where ID = ?";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, relationshipId);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                relationship = getRelationshipFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relationship;
    }

    @Override
    public Relationship getRelationshipBetween(int firstUserId, int secondUserId) {
        Relationship relationship = null;
        String sql = "select * from RELATIONSHIPS where (FIRST_USER_ID = ? and SECOND_USER_ID = ?) or (SECOND_USER_ID = ? and FIRST_USER_ID = ?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, firstUserId);
            ps.setInt(2, secondUserId);
            ps.setInt(3, firstUserId);
            ps.setInt(4, secondUserId);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                relationship = getRelationshipFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relationship;
    }

    @Override
    public void updateRelationship(Relationship relationship) {
        String sql = "update RELATIONSHIPS set FIRST_USER_ID = ?, SECOND_USER_ID = ?, STATUS = ? where ID = ?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
            preparedStatement.setInt(1, relationship.getFirstUserId());
            preparedStatement.setInt(2, relationship.getSecondUserId());
            preparedStatement.setString(3, relationship.getStatus().toString());
            preparedStatement.setInt(4, relationship.getFirstUserId());
            preparedStatement.executeUpdate();
            ServerService.getClient(relationship.getFirstUserId()).receiveNewRelationship(relationship.getId());
            ServerService.getClient(relationship.getSecondUserId()).receiveNewRelationship(relationship.getId());
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRelationship(int relationshipId) {
        String sql = "delete from RELATIONSHIPS where ID = ?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
            preparedStatement.setInt(1, relationshipId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Relationship getRelationshipFromResultSet(ResultSet resultSet) throws SQLException {
        return new Relationship(
                resultSet.getInt("ID"),
                resultSet.getInt("FIRST_USER_ID"),
                resultSet.getInt("SECOND_USER_ID"),
                RelationshipStatus.valueOf(resultSet.getString("STATUS"))
        );
    }

}
