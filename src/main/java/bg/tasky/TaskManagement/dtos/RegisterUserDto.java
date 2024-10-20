package bg.tasky.TaskManagement.dtos;

import java.util.Set;

public class RegisterUserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Set<BoardDto> boards;


    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setBoards(Set<BoardDto> boards) {
        this.boards = boards;
    }

    public String getUsername() {
        return username;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return firstName;
    }
    public Set<BoardDto> getBoards() {
        return boards;
    }
}
