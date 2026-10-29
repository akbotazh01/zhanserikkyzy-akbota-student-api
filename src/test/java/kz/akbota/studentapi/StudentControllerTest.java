package kz.akbota.studentapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentController controller;

    private Student sample() {
        return new Student("Akbota Zhanserikkyzy", "IT2-2308", "37106",
                "akbota.zhanserikkyzy@example.com");
    }

    @Test
    void createReturns201WithLocationHeader() {
        Student saved = sample();
        saved.setId(1L);
        when(repository.save(any(Student.class))).thenReturn(saved);

        ResponseEntity<Student> response = controller.create(sample());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/students/1", response.getHeaders().getLocation().toString());
        assertEquals("Akbota Zhanserikkyzy", response.getBody().getFullName());
    }

    @Test
    void findAllReturnsStudents() {
        when(repository.findAll()).thenReturn(List.of(sample()));

        assertEquals(1, controller.findAll().size());
    }

    @Test
    void findOneReturnsStudent() {
        when(repository.findById(1L)).thenReturn(Optional.of(sample()));

        assertEquals("37106", controller.findOne(1L).getStudentId());
    }

    @Test
    void findOneThrowsWhenStudentDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> controller.findOne(99L));
    }

    @Test
    void blankFullNameIsRejectedByValidation() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Student invalid = new Student("", "IT2-2308", "37106", "akbota.zhanserikkyzy@example.com");

        Set<ConstraintViolation<Student>> violations = validator.validate(invalid);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("fullName")));
    }
}
