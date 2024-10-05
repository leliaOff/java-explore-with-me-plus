package ru.practicum.ewm.dto.eventComment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.enums.EventCommentStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentAdminRequest {
    @Length(min = 1, max = 7000)
    private String text;
    private EventCommentStatus status;
}
