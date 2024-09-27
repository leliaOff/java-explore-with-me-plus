package ru.practicum.ewm.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.models.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByCategoryId(Long categoryId);

    List<Event> findAllByInitiatorId(Long userId, PageRequest request);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long userId);
}
