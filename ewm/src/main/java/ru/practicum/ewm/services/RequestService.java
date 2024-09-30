package ru.practicum.ewm.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.eventRequest.ParticipationRequestDto;
import ru.practicum.ewm.enums.EventRequestStatus;
import ru.practicum.ewm.exceptions.InvalidDataException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.mappers.EventRequestMapper;
import ru.practicum.ewm.models.Event;
import ru.practicum.ewm.models.EventRequest;
import ru.practicum.ewm.models.User;
import ru.practicum.ewm.repositories.EventRepository;
import ru.practicum.ewm.repositories.RequestRepository;
import ru.practicum.ewm.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public RequestService(RequestRepository requestRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        User user = getUser(userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));
        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId))
            throw new InvalidDataException("Request already exist");
        if (event.getInitiator().getId().equals(userId)) {
            throw new InvalidDataException("Request can't be created by initiator");
        }
        if (event.getPublishedOn() == null) {
            throw new InvalidDataException("Event not yet published");
        }

        int requestsSize = requestRepository.findAllByEventId(eventId).size();
        if (event.getParticipantLimit() < requestsSize) {
            throw new InvalidDataException("Participant limit exceeded");
        }

        EventRequest eventRequest = new EventRequest(null, event, user, LocalDateTime.now(), EventRequestStatus.PENDING);
        if (!event.getRequestModeration()) {
            eventRequest.setStatus(EventRequestStatus.CONFIRMED);
        }

        return EventRequestMapper.toDto(requestRepository.save(eventRequest));
    }

    public List<ParticipationRequestDto> getRequests(Long userId) {
        getUser(userId);
        return EventRequestMapper.toDto(requestRepository.findAllByRequesterId(userId));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " was not found"));
    }

    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        EventRequest request = requestRepository.findByRequesterIdAndId(userId, requestId)
                .orElseThrow(() -> new NotFoundException("Request with id=" + requestId + " was not found"));
        request.setStatus(EventRequestStatus.CANCELLED);
        return EventRequestMapper.toDto(requestRepository.save(request));
    }
}
