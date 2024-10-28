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

    // Test for DTO to Entity conversion
    @ParameterizedTest
    @MethodSource("dtoToEntityParamProvider")
    void convertDtoToEntityTest(ListDto dto, String[] emptyFields) {
        ListEntity result = underTest.convertDtoToEntity(dto);
        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept(emptyFields);
    }

    // Test for Entity to DTO conversion
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
                        new String[]{"id", "board", "cards"}  // Allow these fields to be null
                ),
                Arguments.of(
                        new ListDto(null),
                        new String[]{"title", "id", "board", "cards"}  // Allow title and other fields to be null
                )
        );
    }

    private static Stream<Arguments> entityToDtoParamProvider() {
        return Stream.of(
                Arguments.of(
                        createEntity("In Progress"),
                        new String[]{"board", "cards"}  // Allow these fields to be null in DTO
                ),
                Arguments.of(
                        createEntity(null),
                        new String[]{"title", "board", "cards"}  // Allow title and other fields to be null in DTO
                )
        );
    }

    private static ListEntity createEntity(String title) {
        ListEntity entity = new ListEntity();
        entity.setTitle(title);
        return entity;
    }
}
