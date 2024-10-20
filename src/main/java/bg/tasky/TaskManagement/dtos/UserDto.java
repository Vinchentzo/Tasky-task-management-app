package bg.tasky.TaskManagement.dtos;

import java.util.Set;

public record UserDto(String firstName, String lastName, String username, String password, Set<BoardDto> boards) {
}
