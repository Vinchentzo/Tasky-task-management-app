package bg.tasky.TaskManagement.dtos;

import bg.tasky.TaskManagement.entities.UserEntity;

import java.util.Set;

public record BoardDto(String title, String key, Set<UserDto> users, Set<ListDto> lists) {
}
