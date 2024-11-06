package bg.tasky.TaskManagement.tests.mappers;

import bg.tasky.TaskManagement.dtos.ListDto;
import bg.tasky.TaskManagement.entities.ListEntity;
import bg.tasky.TaskManagement.mappers.ListMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ListMapperTest {

    private final ListMapper underTest = Mappers.getMapper(ListMapper.class);

    @ParameterizedTest
    @MethodSource("dtoToEntityParamProvider")
    void convertDtoToEntityTest(ListDto dto, String[] emptyFields) {
        ListEntity result = underTest.convertDtoToEntity(dto);
        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept(emptyFields);
    }

    @ParameterizedTest
    @MethodSource("entityToDtoParamProvider")
    void convertEntityToDtoTest(ListEntity entity, String[] emptyFields) {
        ListDto result = underTest.convertEntityToDto(entity);
        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept(emptyFields);
    }

    private static Stream<Arguments> dtoToEntityParamProvider() {
        return Stream.of(
                Arguments.of(
                        new ListDto("To-Do"),
                        new String[]{"id", "board", "cards"}
                ),
                Arguments.of(
                        new ListDto(null),
                        new String[]{"title", "id", "board", "cards"}
                )
        );
    }

    private static Stream<Arguments> entityToDtoParamProvider() {
        return Stream.of(
                Arguments.of(
                        createEntity("In Progress"),
                        new String[]{"board", "cards"}
                ),
                Arguments.of(
                        createEntity(null),
                        new String[]{"title", "board", "cards"}
                )
        );
    }

    private static ListEntity createEntity(String title) {
        ListEntity entity = new ListEntity();
        entity.setTitle(title);
        return entity;
    }
}
