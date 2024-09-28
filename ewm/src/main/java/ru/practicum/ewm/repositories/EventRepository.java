package ru.practicum.ewm.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.models.Event;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByCategoryId(Long categoryId);

    List<Event> findAllByInitiatorId(Long userId, PageRequest request);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long userId);

    @Query("SELECT event FROM Event event WHERE event.id IN (:ids)")
    List<Event> findByIdIn(@Param("ids") Collection<Long> ids);
}
