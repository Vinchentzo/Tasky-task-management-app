package bg.tasky.TaskManagement.mappers;

import bg.tasky.TaskManagement.dtos.ListDto;
import bg.tasky.TaskManagement.entities.ListEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ListMapper {

    @Mapping(target = "title", source = "dto.title")
    ListEntity convertDtoToEntity(ListDto dto);

    @Mapping(target = "title", source = "entity.title")
    ListDto convertEntityToDto(ListEntity entity);
}