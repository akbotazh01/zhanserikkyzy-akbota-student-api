package kz.akbota.studentapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class StudentControllerDeleteTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentController controller;

    @Test
    void deleteReturns204WhenStudentExists() {
        when(repository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(repository).deleteById(1L);
    }

    @Test
    void deleteThrowsWhenStudentDoesNotExist() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(StudentNotFoundException.class, () -> controller.delete(99L));
        verify(repository, never()).deleteById(any());
    }
}
