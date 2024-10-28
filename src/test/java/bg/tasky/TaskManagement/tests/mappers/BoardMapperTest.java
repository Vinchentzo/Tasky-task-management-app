package bg.tasky.TaskManagement.tests.mappers;

import bg.tasky.TaskManagement.dtos.BoardDto;
import bg.tasky.TaskManagement.entities.BoardEntity;
import bg.tasky.TaskManagement.mappers.BoardMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BoardMapperTest {

    private final BoardMapper underTest = Mappers.getMapper(BoardMapper.class);

    @ParameterizedTest
    @MethodSource("paramProvider")
    void convertDtoToEntityTest(BoardDto dto, String[] emptyFields) {
        BoardEntity result = underTest.convertDtoToEntity(dto);
        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept(emptyFields);
    }

    private static Stream<Arguments> paramProvider() {
        return Stream.of(
                Arguments.of(
                        new BoardDto("Project Alpha", "PA123"),
                        new String[]{"id", "users", "lists"}  // Allow these fields to be null
                ),
                Arguments.of(
                        new BoardDto(null, "BK456"),
                        new String[]{"title", "id", "users", "lists"}  // Allow title and other fields to be null
                ),
                Arguments.of(
                        new BoardDto("Task Board", null),
                        new String[]{"key", "id", "users", "lists"}  // Allow key and other fields to be null
                )
        );
    }
}

