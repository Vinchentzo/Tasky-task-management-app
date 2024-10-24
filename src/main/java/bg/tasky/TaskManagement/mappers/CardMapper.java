package bg.tasky.TaskManagement.mappers;

import bg.tasky.TaskManagement.dtos.CardDto;
import bg.tasky.TaskManagement.entities.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {

    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "description", source = "dto.description")
//    @Mapping(target = "list", source = "dto.list")
//    @Mapping(target = "tasks", source = "dto.tasks")
    CardEntity convertDtoToEntity(CardDto dto);

    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "description", source = "entity.description")
//    @Mapping(target = "list", source = "entity.list")
//    @Mapping(target = "tasks", source = "entity.tasks")
    CardDto convertEntityToDto(CardEntity entity);
}
