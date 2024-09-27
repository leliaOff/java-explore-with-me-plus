package ru.practicum.ewm.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.StatDto;
import ru.practicum.ewm.client.StatClient;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.event.UpdateEventUserRequest;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.mappers.EventMapper;
import ru.practicum.ewm.mappers.UserMapper;
import ru.practicum.ewm.models.Event;
import ru.practicum.ewm.repository.EventRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final StatClient statClient;

    public EventService(EventRepository eventRepository, UserService userService, StatClient statClient) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.statClient = statClient;
    }

    @Transactional
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        UserDto userDto = userService.getUser(userId);
        Event event = eventRepository.save(EventMapper.toModel(newEventDto, UserMapper.toModel(userDto)));
        return EventMapper.toDto(event, 0L);
    }

    public List<EventShortDto> getPrivateEvents(Long userId, PageRequest request) {
        List<Event> events = eventRepository.findAllByInitiatorId(userId, request);
        return events.stream().map(event -> EventMapper.toShortDto(event, 0)).toList();
    }

    public EventFullDto getPrivateEvent(Long userId, Long eventId) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + eventId + " was not found"));
        return EventMapper.toDto(event, getViews(event));
    }

    public EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventRequest) {
        Event oldEvent = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + eventId + " was not found"));
        Event updatedEvent = EventMapper.mergeModel(oldEvent, updateEventRequest);
        return EventMapper.toDto(eventRepository.save(updatedEvent), getViews(oldEvent));
    }

    private Long getViews(Event event) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<StatDto> stats = statClient.getStats(event.getCreatedOn().format(pattern),
                LocalDateTime.now().format(pattern), List.of("/events/" + event.getId()), false
        );
        if (stats.isEmpty())
            return 0L;
        else return stats.getFirst().getHits();
    }

}
