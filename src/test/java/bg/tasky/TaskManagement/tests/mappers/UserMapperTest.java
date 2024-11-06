package bg.tasky.TaskManagement.tests.mappers;

import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.entities.UserEntity;
import bg.tasky.TaskManagement.mappers.UserMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper underTest = Mappers.getMapper(UserMapper.class);

    @ParameterizedTest
    @MethodSource("paramProvider")
    void convertDtoToEntityTest(UserDto dto, String[] emptyFields) {
        UserEntity result = underTest.convertDtoToEntity(dto);
        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept(emptyFields);
    }

    private static Stream<Arguments> paramProvider() {
        return Stream.of(
                Arguments.of(
                        new UserDto("John", null, "john_doe", "password123"),
                        new String[]{"lastName", "id", "boards"}
                ),
                Arguments.of(
                        new UserDto(null, "Doe", "jane_doe", "password456"),
                        new String[]{"firstName", "id", "boards"}
                ),
                Arguments.of(
                        new UserDto("Alice", "Smith", null, "password789"),
                        new String[]{"username", "id", "boards"}
                )
        );
    }

    @ParameterizedTest
    @MethodSource("paramProviderForEntityToDto")
    void convertEntityToDtoTest(UserEntity entity, String[] emptyFields) {
        UserDto result = underTest.convertEntityToDto(entity);
        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept(emptyFields);
    }

    private static UserEntity createUserEntity(String firstName, String lastName, String username, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        return userEntity;
    }


    private static Stream<Arguments> paramProviderForEntityToDto() {
        return Stream.of(
                Arguments.of(
                        createUserEntity("John", null, "john_doe", "password123"),
                        new String[]{"lastName"}
                ),
                Arguments.of(
                        createUserEntity(null, "Doe", "jane_doe", "password456"),
                        new String[]{"firstName"}
                ),
                Arguments.of(
                        createUserEntity("Alice", "Smith", null, "password789"),
                        new String[]{"username"}
                )
        );
    }
}

