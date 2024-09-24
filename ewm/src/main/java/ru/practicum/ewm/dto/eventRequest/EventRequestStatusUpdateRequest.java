package ru.practicum.ewm.dto.eventRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.enums.EventRequestStatus;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds;
    private EventRequestStatus status;
}
