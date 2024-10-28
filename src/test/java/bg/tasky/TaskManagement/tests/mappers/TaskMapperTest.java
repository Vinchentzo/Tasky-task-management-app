package bg.tasky.TaskManagement.tests.mappers;

import bg.tasky.TaskManagement.dtos.TaskDto;
import bg.tasky.TaskManagement.entities.TaskEntity;
import bg.tasky.TaskManagement.mappers.TaskMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    private final TaskMapper underTest = Mappers.getMapper(TaskMapper.class);

    // Test for DTO to Entity conversion
    @ParameterizedTest
    @MethodSource("dtoToEntityParamProvider")
    void convertDtoToEntityTest(TaskDto dto, String[] emptyFields) {
        TaskEntity result = underTest.convertDtoToEntity(dto);
        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept(emptyFields);
    }

    // Test for Entity to DTO conversion
    @ParameterizedTest
    @MethodSource("entityToDtoParamProvider")
    void convertEntityToDtoTest(TaskEntity entity, String[] emptyFields) {
        TaskDto result = underTest.convertEntityToDto(entity);
        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept(emptyFields);
    }

    private static Stream<Arguments> dtoToEntityParamProvider() {
        return Stream.of(
                Arguments.of(
                        new TaskDto("Initial Setup", true),
                        new String[]{"id", "card"}  // Allow these fields to be null in Entity
                ),
                Arguments.of(
                        new TaskDto(null, false),
                        new String[]{"title", "completed", "id", "card"}  // Allow all fields to be null
                )
        );
    }

    private static Stream<Arguments> entityToDtoParamProvider() {
        return Stream.of(
                Arguments.of(
                        createEntity("Bug Investigation", false),
                        new String[]{"card"}  // Allow these fields to be null in DTO
                ),
                Arguments.of(
                        createEntity(null, true),
                        new String[]{"title", "card"}  // Allow title and card to be null
                )
        );
    }

    private static TaskEntity createEntity(String title, Boolean completed) {
        TaskEntity entity = new TaskEntity();
        entity.setTitle(title);
        entity.setCompleted(completed);
        return entity;
    }
}

