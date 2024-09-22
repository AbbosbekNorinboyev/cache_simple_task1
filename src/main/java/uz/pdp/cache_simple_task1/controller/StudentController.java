package uz.pdp.cache_simple_task1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.cache_simple_task1.dto.StudentCreateDTO;
import uz.pdp.cache_simple_task1.dto.StudentUpdateDTO;
import uz.pdp.cache_simple_task1.entity.Student;
import uz.pdp.cache_simple_task1.service.StudentServiceImpl;

@Slf4j
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentServiceImpl studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentCreateDTO studentCreateDTO) {
        return ResponseEntity.status(201).body(studentService.createStudent(studentCreateDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return ResponseEntity.status(201).body(studentService.getStudent(id));
    }

    @PutMapping
    public ResponseEntity<Void> updateStudent(@RequestBody StudentUpdateDTO studentUpdateDTO) {
        studentService.updateStudent(studentUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
