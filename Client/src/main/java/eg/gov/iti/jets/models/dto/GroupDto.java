package eg.gov.iti.jets.models.dto;

import java.util.List;

public class GroupDto {
    String groupName;
    List<UserDto> users;

    public GroupDto(String groupName, List<UserDto> users) {
        this.groupName = groupName;
        this.users = users;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<UserDto> getUsers() {
        return users;
    }
}