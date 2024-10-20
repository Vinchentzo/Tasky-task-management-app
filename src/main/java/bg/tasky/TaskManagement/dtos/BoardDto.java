package bg.tasky.TaskManagement.dtos;

import java.util.Set;

public record BoardDto(String title, String key, Set<ListDto> lists) {
}
