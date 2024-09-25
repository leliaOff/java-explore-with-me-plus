package ru.practicum.ewm.mappers;

import ru.practicum.ewm.dto.event.*;
import ru.practicum.ewm.dto.location.Location;
import ru.practicum.ewm.enums.EventState;
import ru.practicum.ewm.models.Category;
import ru.practicum.ewm.models.Event;

public class EventMapper {
    public static EventShortDto toShortDto(Event model, Integer view) {
        return new EventShortDto(
                model.getId(),
                model.getTitle(),
                model.getAnnotation(),
                CategoryMapper.toDto(model.getCategory()),
                model.getConfirmedRequests(),
                model.getEventDate(),
                UserMapper.toShortDto(model.getInitiator()),
                model.getPaid(),
                view
        );
    }

    public static EventFullDto toDto(Event model, Integer view) {
        return new EventFullDto(
                model.getId(),
                model.getTitle(),
                model.getAnnotation(),
                CategoryMapper.toDto(model.getCategory()),
                model.getConfirmedRequests(),
                model.getCreatedOn(),
                model.getDescription(),
                model.getEventDate(),
                UserMapper.toShortDto(model.getInitiator()),
                new Location(model.getLat(), model.getLon()),
                model.getPaid(),
                model.getParticipantLimit(),
                model.getPublishedOn(),
                model.getRequestModeration(),
                model.getState(),
                view
        );
    }

    public static Event toModel(NewEventDto dto) {
        Event model = new Event();
        model.setTitle(dto.getTitle());
        model.setAnnotation(dto.getAnnotation());
        model.setCategory(new Category(dto.getCategory()));
        model.setDescription(dto.getDescription());
        model.setEventDate(dto.getEventDate());
        model.setPaid(dto.getPaid());
        model.setParticipantLimit(dto.getParticipantLimit());
        model.setRequestModeration(dto.getRequestModeration());
        model.setLat(dto.getLocation().getLat());
        model.setLon(dto.getLocation().getLon());
        return model;
    }

    public static Event mergeModel(Event model, UpdateEventAdminRequest dto) {
        if (dto.getTitle() != null) {
            model.setTitle(dto.getTitle());
        }
        if (dto.getAnnotation() != null) {
            model.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            model.setCategory(new Category(dto.getCategory()));
        }
        if (dto.getDescription() != null) {
            model.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            model.setEventDate(dto.getEventDate());
        }
        if (dto.getPaid() != null) {
            model.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            model.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getRequestModeration() != null) {
            model.setRequestModeration(dto.getRequestModeration());
        }
        if (dto.getStateAction() != null) {
            model.setState(EventState.valueOf(dto.getStateAction().toString()));
        }
        return model;
    }

    public static Event mergeModel(Event model, UpdateEventUserRequest dto) {
        if (dto.getTitle() != null) {
            model.setTitle(dto.getTitle());
        }
        if (dto.getAnnotation() != null) {
            model.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            model.setCategory(new Category(dto.getCategory()));
        }
        if (dto.getDescription() != null) {
            model.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            model.setEventDate(dto.getEventDate());
        }
        if (dto.getPaid() != null) {
            model.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            model.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getRequestModeration() != null) {
            model.setRequestModeration(dto.getRequestModeration());
        }
        if (dto.getStateAction() != null) {
            model.setState(EventState.valueOf(dto.getStateAction().toString()));
        }
        return model;
    }
}
