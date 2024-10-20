package bg.tasky.TaskManagement.mappers;

import bg.tasky.TaskManagement.dtos.ListDto;
import bg.tasky.TaskManagement.entities.ListEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListMapper {

    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "board", source = "dto.board")
    @Mapping(target = "cards", source = "dto.cards")
    ListEntity convertDtoToEntity(ListDto dto);

    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "board", source = "entity.board")
    @Mapping(target = "cards", source = "entity.cards")
    ListDto convertEntityToDto(ListEntity entity);
}