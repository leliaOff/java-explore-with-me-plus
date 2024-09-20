package ru.practicum.server.model;

import ru.practicum.dto.StatDto;

public class StatMapper {
    public static StatDto toDto(Stat model) {
        StatDto dto = new StatDto();
        dto.setApp(model.getApp());
        dto.setUri(model.getUri());
        dto.setHits(model.getHits());
        return dto;
    }
}
