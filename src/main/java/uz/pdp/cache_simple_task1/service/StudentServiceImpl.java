package uz.pdp.cache_simple_task1.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.cache_simple_task1.dto.StudentCreateDTO;
import uz.pdp.cache_simple_task1.dto.StudentUpdateDTO;
import uz.pdp.cache_simple_task1.entity.Student;
import uz.pdp.cache_simple_task1.mapper.StudentMapper;
import uz.pdp.cache_simple_task1.repository.StudentRepository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final ConcurrentHashMap<Long, Student> studentsCache = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public Student createStudent(@NonNull StudentCreateDTO studentCreateDTO) {
        Student student = studentMapper.toEntity(studentCreateDTO);
        return studentRepository.save(student);
    }

    @Override
    @SneakyThrows
    public Student getStudent(@NonNull Long id) {
        Student cachedStudent = studentsCache.get(id);
        if (cachedStudent != null) {
            return cachedStudent;
        }
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));
        TimeUnit.SECONDS.sleep(1);
        studentsCache.put(id, student);
        return student;
    }

    @Override
    public void updateStudent(@NonNull StudentUpdateDTO studentUpdateDTO) {
        Student student = getStudent(studentUpdateDTO.getId());
        if (studentUpdateDTO.getName() != null) {
            student.setName(studentUpdateDTO.getName());
        }
        student.setAge(studentUpdateDTO.getAge());
        studentRepository.save(student);
        studentsCache.put(studentUpdateDTO.getId(), student);
    }

    @Override
    public void deleteStudent(@NonNull Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found: " + id));
        studentRepository.deleteById(student.getId());
        studentsCache.remove(id);
    }
}
