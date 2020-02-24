package eg.gov.iti.jets.models.entities;

import java.io.Serializable;

public class SingleChat implements Serializable {
    private static final long serialVersionUID = 6519685198267157190L;
    private int id;
    private int userOneId;
    private int userTwoId;

    public SingleChat(int userOneId, int userTwoId) {
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
    }

    public SingleChat(int id, int userOneId, int userTwoId) {
        this.id = id;
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserOneId() {
        return userOneId;
    }

    public void setUserOneId(int userOneId) {
        this.userOneId = userOneId;
    }

    public int getUserTwoId() {
        return userTwoId;
    }

    public void setUserTwoId(int userTwoId) {
        this.userTwoId = userTwoId;
    }

    @Override
    public String toString() {
        return "SingleChat{" +
                "singleChatId=" + id +
                ", userOneId=" + userOneId +
                ", userTwoId=" + userTwoId +
                '}';
    }
}
