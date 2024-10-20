package bg.tasky.TaskManagement.mappers;

import bg.tasky.TaskManagement.dtos.UserDto;
import bg.tasky.TaskManagement.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "firstName", source = "dto.firstName")
    @Mapping(target = "lastName", source = "dto.lastName")
    @Mapping(target = "username", source = "dto.username")
    @Mapping(target = "password", source = "dto.password")
    @Mapping(target = "boards", source = "dto.boards")
    UserEntity convertDtoToEntity(UserDto dto);

    @Mapping(target = "firstName", source = "entity.firstName")
    @Mapping(target = "lastName", source = "entity.lastName")
    @Mapping(target = "username", source = "entity.username")
    @Mapping(target = "password", source = "entity.password")
    @Mapping(target = "boards", source = "entity.boards")
    UserDto convertEntityToDto(UserEntity entity);
}
