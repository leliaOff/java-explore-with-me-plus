package ru.practicum.ewm.dto.compilation;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class NewCompilationDto {
    @NotBlank
    private String title;
    private Boolean pinned;
    private List<Long> events;
}
