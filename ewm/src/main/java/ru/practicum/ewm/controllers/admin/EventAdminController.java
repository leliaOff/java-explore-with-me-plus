package ru.practicum.ewm.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.services.EventService;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/events")
public class EventAdminController {
    @Autowired
    private final EventService eventService;

    public EventAdminController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    EventFullDto addEvent(@PathVariable("userId") Long userId, NewEventDto event) {
        log.info("Add event {}", event);
        return eventService.addEvent(userId, event);
    }
}
