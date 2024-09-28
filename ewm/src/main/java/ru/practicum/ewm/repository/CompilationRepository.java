package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.models.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    boolean existsByTitleAndIdNot(String name, Long id);
}
