package kz.akbota.studentapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final StudentRepository repository;

    public DataSeeder(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        // Insert the personal record only into an empty table
        if (repository.count() == 0) {
            repository.save(new Student(
                    "Akbota Zhanserikkyzy", "IT2-2308", "37106",
                    "akbota.zhanserikkyzy@example.com"));
        }
    }
}
