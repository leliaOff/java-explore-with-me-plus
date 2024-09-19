package ru.practicum.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.server.model.Hit;

public interface HitRepository extends JpaRepository<Hit, Integer> {
}
