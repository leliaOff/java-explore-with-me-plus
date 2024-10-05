package ru.practicum.ewm.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.eventComment.CreateCommentRequest;
import ru.practicum.ewm.dto.eventComment.EventCommentDto;
import ru.practicum.ewm.dto.eventComment.EventCommentPrivateDto;
import ru.practicum.ewm.dto.eventComment.UpdateCommentRequest;
import ru.practicum.ewm.enums.EventCommentStatus;
import ru.practicum.ewm.exceptions.BadRequestException;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.mappers.EventCommentMapper;
import ru.practicum.ewm.models.Event;
import ru.practicum.ewm.models.EventComment;
import ru.practicum.ewm.models.User;
import ru.practicum.ewm.repositories.EventCommentRepository;
import ru.practicum.ewm.repositories.EventRepository;
import ru.practicum.ewm.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
public class EventCommentService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventCommentRepository eventCommentRepository;

    public EventCommentService(UserRepository userRepository,
                               EventRepository eventRepository,
                               EventCommentRepository eventCommentRepository
    ) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.eventCommentRepository = eventCommentRepository;
    }

    public List<EventCommentPrivateDto> getComments(Long userId, Long eventId, Integer size, Integer from) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "created"));
        if (eventId != null) {
            return eventCommentRepository.findAllByAuthorIdAndEventId(userId, eventId, pageRequest).stream()
                    .map(EventCommentMapper::toPrivateDto)
                    .collect(Collectors.toList());
        }
        return eventCommentRepository.findAllByAuthorId(userId, pageRequest).stream()
                .map(EventCommentMapper::toPrivateDto)
                .collect(Collectors.toList());
    }

    public List<EventCommentDto> getPublishedComments(Long eventId, Integer size, Integer from) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "created"));
        return eventCommentRepository.findAllByEventIdAndStatus(eventId, EventCommentStatus.PUBLISHED, pageRequest).stream()
                .map(EventCommentMapper::toDto)
                .collect(Collectors.toList());
    }

    public EventComment find(Long eventId) {
        return eventCommentRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event's comment not found"));
    }


    @Transactional
    public EventCommentPrivateDto create(Long userId, CreateCommentRequest request) {
        User author = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Event event = eventRepository.findById(request.getEventId()).orElseThrow(() -> new NotFoundException("Event not found"));
        if (event.getEventDate().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("The event has not started yet");
        }
        EventComment comment = eventCommentRepository.save(EventCommentMapper.toModel(request, author, event));
        return EventCommentMapper.toPrivateDto(comment);
    }


    @Transactional
    public EventCommentPrivateDto update(Long userId, Long commentId, UpdateCommentRequest request) {
        EventComment comment = find(commentId);
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new BadRequestException("No access to comment");
        }
        if (!comment.getStatus().equals(EventCommentStatus.PENDING)) {
            throw new BadRequestException("The comment must be in draft");
        }
        comment = eventCommentRepository.save(EventCommentMapper.mergeModel(request, comment));
        return EventCommentMapper.toPrivateDto(comment);
    }

    @Transactional
    public void delete(Long userId, Long commentId) {
        EventComment comment = find(commentId);
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new BadRequestException("No access to comment");
        }
        if (!comment.getStatus().equals(EventCommentStatus.PENDING)) {
            throw new BadRequestException("The comment must be in draft");
        }
        eventCommentRepository.delete(comment);
    }
}
