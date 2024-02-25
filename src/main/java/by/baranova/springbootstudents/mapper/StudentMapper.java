package by.baranova.springbootstudents.mapper;


import by.baranova.springbootstudents.model.Student;
import by.baranova.springbootstudents.model.StudentDto;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDto toDto(Student student){
        final StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setLastName(student.getLastName());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setBirthday(student.getBirthday());

        return dto;
    }

    public Student toEntity(StudentDto dto){
        final Student entity = new Student();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setBirthday(dto.getBirthday());

        return entity;
    }
}
