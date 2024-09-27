package ru.practicum.ewm.controllers.privates;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.eventRequest.ParticipationRequestDto;
import ru.practicum.ewm.services.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/requests")
public class RequestPrivateController {

    private final RequestService requestService;

    public RequestPrivateController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    List<ParticipationRequestDto> getRequests(@PathVariable String userId) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ParticipationRequestDto addRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        return requestService.addRequest(userId, eventId);
    }
}
