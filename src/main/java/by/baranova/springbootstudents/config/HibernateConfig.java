package by.baranova.springbootstudents.config;

import by.baranova.springbootstudents.model.Student;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {
    @Bean
    public SessionFactory sessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();

        return new MetadataSources(registry)
                .addAnnotatedClass(Student.class)
                .buildMetadata()
                .buildSessionFactory();
    }


}
