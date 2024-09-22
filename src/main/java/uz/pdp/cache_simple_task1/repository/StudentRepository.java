package uz.pdp.cache_simple_task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.cache_simple_task1.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}