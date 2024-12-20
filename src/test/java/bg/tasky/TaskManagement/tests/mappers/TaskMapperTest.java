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

    @ParameterizedTest
    @MethodSource("dtoToEntityParamProvider")
    void convertDtoToEntityTest(TaskDto dto, String[] emptyFields) {
        TaskEntity result = underTest.convertDtoToEntity(dto);
        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept(emptyFields);
    }

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
                        new String[]{"id", "card"}
                ),
                Arguments.of(
                        new TaskDto(null, false),
                        new String[]{"title", "completed", "id", "card"}
                )
        );
    }

    private static Stream<Arguments> entityToDtoParamProvider() {
        return Stream.of(
                Arguments.of(
                        createEntity("Bug Investigation", false),
                        new String[]{"card"}
                ),
                Arguments.of(
                        createEntity(null, true),
                        new String[]{"title", "card"}
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

