package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.constraints.EventDateConstraintOrNull;
import ru.practicum.ewm.enums.EventUserStateAction;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest {
    @Length(min = 3, max = 120)
    private String title;
    @Length(min = 20, max = 2000)
    private String annotation;
    private Long category;
    @Length(min = 20, max = 7000)
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @EventDateConstraintOrNull
    private LocalDateTime eventDate;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration;
    private EventUserStateAction stateAction;
}
