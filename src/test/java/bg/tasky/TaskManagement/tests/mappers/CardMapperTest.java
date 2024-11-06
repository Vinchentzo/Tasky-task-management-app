package bg.tasky.TaskManagement.tests.mappers;

import bg.tasky.TaskManagement.dtos.CardDto;
import bg.tasky.TaskManagement.entities.CardEntity;
import bg.tasky.TaskManagement.mappers.CardMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CardMapperTest {

    private final CardMapper underTest = Mappers.getMapper(CardMapper.class);

    @ParameterizedTest
    @MethodSource("dtoToEntityParamProvider")
    void convertDtoToEntityTest(CardDto dto, String[] emptyFields) {
        CardEntity result = underTest.convertDtoToEntity(dto);
        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept(emptyFields);
    }

    @ParameterizedTest
    @MethodSource("entityToDtoParamProvider")
    void convertEntityToDtoTest(CardEntity entity, String[] emptyFields) {
        CardDto result = underTest.convertEntityToDto(entity);
        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept(emptyFields);
    }

    private static Stream<Arguments> dtoToEntityParamProvider() {
        return Stream.of(
                Arguments.of(
                        new CardDto("Feature Task", "Implement new feature"),
                        new String[]{"id", "list", "tasks"}
                ),
                Arguments.of(
                        new CardDto(null, null),
                        new String[]{"title", "description", "id", "list", "tasks"}
                )
        );
    }

    private static Stream<Arguments> entityToDtoParamProvider() {
        return Stream.of(
                Arguments.of(
                        createEntity("Bug Fix", "Resolve critical bug"),
                        new String[]{"list", "tasks"}
                ),
                Arguments.of(
                        createEntity(null, null),
                        new String[]{"title", "description", "list", "tasks"}
                )
        );
    }

    private static CardEntity createEntity(String title, String description) {
        CardEntity entity = new CardEntity();
        entity.setTitle(title);
        entity.setDescription(description);
        return entity;
    }
}
