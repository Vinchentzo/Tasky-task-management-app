package bg.tasky.TaskManagement.mappers;

import bg.tasky.TaskManagement.dtos.TaskDto;
import bg.tasky.TaskManagement.entities.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {

    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "completed", source = "dto.completed")
    @Mapping(target = "card", source = "dto.card")
    TaskEntity convertDtoToEntity(TaskDto dto);

    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "completed", source = "entity.completed")
    @Mapping(target = "card", source = "entity.card")
    TaskDto convertEntityToDto(TaskEntity entity);
}
