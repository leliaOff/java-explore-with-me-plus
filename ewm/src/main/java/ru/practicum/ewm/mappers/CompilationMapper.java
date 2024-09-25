package ru.practicum.ewm.mappers;

import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.dto.compilation.UpdateCompilationDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.models.Compilation;

import java.util.Collection;
import java.util.stream.Collectors;

public class CompilationMapper {
    public static CompilationDto toDto(Compilation model) {
        Collection<EventShortDto> events = model.getEvents().stream()
                .map(event -> EventMapper.toShortDto(event, 0))
                .collect(Collectors.toList());
        return new CompilationDto(
                model.getId(),
                model.getTitle(),
                model.getPinned(),
                events
        );
    }

    public static Compilation toModel(NewCompilationDto dto) {
        Compilation model = new Compilation();
        model.setTitle(dto.getTitle());
        model.setPinned(dto.getPinned());
        return model;
    }

    public static Compilation mergeModel(Compilation model, UpdateCompilationDto dto) {
        if (dto.getTitle() != null) {
            model.setTitle(dto.getTitle());
        }
        if (dto.getPinned() != null) {
            model.setPinned(dto.getPinned());
        }
        return model;
    }
}
