package uz.pdp.cache_simple_task1.mapper;

import org.mapstruct.*;
import uz.pdp.cache_simple_task1.dto.StudentCreateDTO;
import uz.pdp.cache_simple_task1.entity.Student;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {
    Student toEntity(StudentCreateDTO studentCreateDTO);

    StudentCreateDTO toDto(Student student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Student partialUpdate(StudentCreateDTO studentCreateDTO, @MappingTarget Student student);
}