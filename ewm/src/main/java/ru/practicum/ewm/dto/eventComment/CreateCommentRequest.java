package ru.practicum.ewm.dto.eventComment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {
    @NotBlank
    @Length(min = 1, max = 7000)
    private String text;
    @NotBlank
    private Long eventId;
}
