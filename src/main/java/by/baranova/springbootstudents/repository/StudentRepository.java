package by.baranova.springbootstudents.repository;

import by.baranova.springbootstudents.mapper.StudentMapper;
import by.baranova.springbootstudents.model.Student;
import by.baranova.springbootstudents.model.StudentDto;
import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {

    private final SessionFactory sessionFactory;

    private final StudentMapper studentMapper;
    private static final String CONST_UPDATE = """
            UPDATE Student S SET\s
               S.name = :name,\s
               S.lastName = :last_name,\s
               S.phoneNumber = :phone_number,\s
               S.birthday = :birthday
            WHERE S.id = :id""";

    public StudentRepository(SessionFactory sessionFactory, StudentMapper studentMapper) {
        this.sessionFactory = sessionFactory;
        this.studentMapper = studentMapper;
    }

    public List<StudentDto> findAll() {
        final List<Student> students = sessionFactory.fromSession(session -> {
            Query<Student> query = session.createQuery("FROM Student ", Student.class);
            return query.list();
        });
        return students.stream()
                .map(studentMapper::toDto)
                .toList();
    }

    public Optional<StudentDto> findById(Long id) {
        return sessionFactory.fromSession(session -> {
            Query<Student> query = session.createQuery("FROM Student S WHERE S.id = :id", Student.class);
            query.setParameter("id", id);
            return query.uniqueResultOptional().map(studentMapper::toDto);
        });
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

    public void update(Long id, StudentDto updatedStudent) {
        sessionFactory.inTransaction(session -> {
            final MutationQuery query = session.createMutationQuery(CONST_UPDATE);
            query.setParameter("id", id);
            query.setParameter("name", updatedStudent.getName());
            query.setParameter("last_name", updatedStudent.getLastName());
            query.setParameter("phone_number", updatedStudent.getPhoneNumber());
            query.setParameter("birthday", updatedStudent.getBirthday());
            query.executeUpdate();
        });

    }
}