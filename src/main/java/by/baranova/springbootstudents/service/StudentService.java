package by.baranova.springbootstudents.service;
import by.baranova.springbootstudents.exception.EntityNotFoundException;
import by.baranova.springbootstudents.model.StudentDto;
import by.baranova.springbootstudents.mapper.StudentMapper;
import by.baranova.springbootstudents.model.Student;
import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final SessionFactory sessionFactory;
    private final StudentMapper studentMapper;
    private final String CONST_UPDATE = """
            UPDATE Student S SET\s
               S.name = :name,\s
               S.lastName = :last_name,\s
               S.phoneNumber = :phone_number,\s
               S.birthday = :birthday
            WHERE S.id = :id""";

    public StudentService(SessionFactory sessionFactory, StudentMapper studentMapper) {
        this.sessionFactory = sessionFactory;
        this.studentMapper = studentMapper;
    }

    public List<StudentDto> findStudents() {
        final List<Student> students = sessionFactory.fromSession(session -> {
            Query<Student> query = session.createQuery("FROM Student ", Student.class);
            return query.list();
        });
        return students.stream()
                .map(studentMapper::toDto)
                .toList();
    }

    public StudentDto findStudentById(Long id) {
        Optional<Student> optionalStudent = sessionFactory.fromSession(session -> {
            Query<Student> query = session.createQuery("FROM Student S WHERE S.id = :id", Student.class);
            query.setParameter("id", id);
            return query.uniqueResultOptional();
        });
        if (optionalStudent.isPresent()) {
            final Student student = optionalStudent.get();
            return studentMapper.toDto(student);
        }
        throw new EntityNotFoundException("Студента с ID" + id + "не существует");
    }

    public void deleteById(Long id) {
        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session.createMutationQuery("""
                    DELETE FROM Student
                    WHERE id = :id
                    """);
            query.setParameter("id", id);
            query.executeUpdate();
        });
    }

    public void save(StudentDto studentDto) {
        final Student student = studentMapper.toEntity(studentDto);
        sessionFactory.inTransaction(session -> {
            session.persist(student);
        });
    }

    public void update(Long id, StudentDto student) {
        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session.createMutationQuery(CONST_UPDATE);

            query.setParameter("id", id);
            query.setParameter("name", student.getName());
            query.setParameter("last_name", student.getLastName());
            query.setParameter("phone_number", student.getPhoneNumber());
            query.setParameter("birthday", student.getBirthday());

            query.executeUpdate();
        });
    }
}