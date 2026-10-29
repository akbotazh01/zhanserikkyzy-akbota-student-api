package kz.akbota.studentapi;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody Student student) {
        student.setId(null); // the database generates the id
        Student saved = repository.save(student);
        return ResponseEntity.created(URI.create("/students/" + saved.getId())).body(saved);
    }

    @GetMapping
    public List<Student> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Student findOne(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @Valid @RequestBody Student changes) {
        Student existing = repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        existing.setFullName(changes.getFullName());
        existing.setGroupName(changes.getGroupName());
        existing.setStudentId(changes.getStudentId());
        existing.setEmail(changes.getEmail());
        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
