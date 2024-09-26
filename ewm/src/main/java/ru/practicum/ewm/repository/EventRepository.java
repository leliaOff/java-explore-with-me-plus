package ru.practicum.ewm.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.models.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByCategoryId(Long categoryId);

    List<Event> findAllByInitiatorId(Long userId, PageRequest request);

    Event findByIdAndInitiatorId(Long eventId, Long userId);
}
