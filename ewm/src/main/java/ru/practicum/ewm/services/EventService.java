package ru.practicum.ewm.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.StatDto;
import ru.practicum.ewm.client.StatClient;
import ru.practicum.ewm.dto.event.*;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.enums.EventSort;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.mappers.EventMapper;
import ru.practicum.ewm.mappers.UserMapper;
import ru.practicum.ewm.models.Event;
import ru.practicum.ewm.repositories.CategoryRepository;
import ru.practicum.ewm.repositories.EventRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.repositories.EventRepository.EventSpecification.*;

@Service
@Transactional(readOnly = true)
public class EventService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final UserService userService;
    private final StatClient statClient;

    public EventService(CategoryRepository categoryRepository,
                        EventRepository eventRepository,
                        UserService userService,
                        StatClient statClient
    ) {
        this.categoryRepository = categoryRepository;
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
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));
        return EventMapper.toDto(event, getViews(event));
    }

    public EventFullDto updatePrivateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventRequest) {
        Event oldEvent = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));
        Event updatedEvent = EventMapper.mergeModel(oldEvent, updateEventRequest);
        return EventMapper.toDto(eventRepository.save(updatedEvent), getViews(oldEvent));
    }

    public EventFullDto find(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));
        return EventMapper.toDto(event, getViews(event));
    }

    public Collection<EventShortDto> getEvents(EventFilterDto filter, EventSort sort, Integer from, Integer size) {
        Specification<Event> specification = byText(filter.getText())
                .and(byCategories(categoryRepository.findByIdIn(filter.getCategories())))
                .and(byPaid(filter.getPaid()))
                .and(byRangeStart(filter.getRangeStart()))
                .and(byRangeEnd(filter.getRangeEnd()))
                .and(byOnlyAvailable(filter.getOnlyAvailable()));
        Collection<Event> events = eventRepository.findAll(specification,
                PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "eventDate"))
        ).toList();
        return events.stream().map(event -> EventMapper.toShortDto(event, 0)).collect(Collectors.toList());
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
