package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameAndIdNot(String name, Long id);
}
