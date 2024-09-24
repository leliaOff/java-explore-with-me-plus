package ru.practicum.ewm.mappers;

import ru.practicum.ewm.dto.eventRequest.ParticipationRequestDto;
import ru.practicum.ewm.models.EventRequest;

public class EventRequestMapper {
    public static ParticipationRequestDto toDto(EventRequest model) {
        return new ParticipationRequestDto(
                model.getId(),
                model.getCreated(),
                model.getEvent().getId(),
                model.getRequester().getId(),
                model.getStatus()
        );
    }
}
