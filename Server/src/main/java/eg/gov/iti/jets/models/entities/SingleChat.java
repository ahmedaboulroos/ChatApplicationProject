package eg.gov.iti.jets.models.entities;

public class SingleChat {
    private int singleChatId;
    private int userOneId;
    private int userTwoId;

    public SingleChat(int userOneId, int userTwoId) {
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
    }

    // constructor for populating data from database
    public SingleChat(int singleChatId, int userOneId, int userTwoId) {
        this.singleChatId = singleChatId;
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
    }

    public int getSingleChatId() {
        return singleChatId;
    }

    public void setSingleChatId(int singleChatId) {
        this.singleChatId = singleChatId;
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
}
