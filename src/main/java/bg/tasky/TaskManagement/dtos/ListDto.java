package bg.tasky.TaskManagement.dtos;


import java.util.Set;

public record ListDto(String title, BoardDto board, Set<CardDto> cards) {
}
