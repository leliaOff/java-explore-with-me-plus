package ru.practicum.ewm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.models.EventRequest;

import java.util.List;

public interface RequestRepository extends JpaRepository<EventRequest, Integer> {
    boolean existsByRequesterIdAndEventId(Long userId, Long eventId);

    List<EventRequest> findAllByEventId(Long eventId);
}
