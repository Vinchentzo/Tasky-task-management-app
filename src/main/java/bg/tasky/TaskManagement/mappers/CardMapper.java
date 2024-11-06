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
    CardEntity convertDtoToEntity(CardDto dto);

    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "description", source = "entity.description")
    CardDto convertEntityToDto(CardEntity entity);
}
