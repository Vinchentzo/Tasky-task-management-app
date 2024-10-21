package bg.tasky.TaskManagement.mappers;


import bg.tasky.TaskManagement.dtos.BoardDto;
import bg.tasky.TaskManagement.entities.BoardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BoardMapper {

    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "key", source = "dto.key")
    //@Mapping(target = "users", source = "dto.users")
    @Mapping(target = "lists", source = "dto.lists")
    BoardEntity convertDtoToEntity(BoardDto dto);

    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "key", source = "entity.key")
    //@Mapping(target = "users", source = "entity.users")
    @Mapping(target = "lists", source = "entity.lists")
    BoardDto convertEntityToDto(BoardEntity entity);
}


