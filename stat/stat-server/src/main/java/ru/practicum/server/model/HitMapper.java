package ru.practicum.server.model;

import ru.practicum.dto.HitDto;
import ru.practicum.server.helpers.DateTimeHelper;

public class HitMapper {
    public static HitDto toHitDto(Hit hit) {
        HitDto hitDto = new HitDto();
        hitDto.setId(hit.getId());
        hitDto.setApp(hit.getApp());
        hitDto.setUri(hit.getUri());
        hitDto.setIp(hit.getIp());
        hitDto.setTimestamp(DateTimeHelper.toString(hit.getTimestamp()));

        return hitDto;
    }

    public static Hit toHit(HitDto hitDto) {
        Hit hit = new Hit();
        hit.setId(hitDto.getId());
        hit.setApp(hitDto.getApp());
        hit.setUri(hitDto.getUri());
        hit.setIp(hitDto.getIp());
        hit.setTimestamp(DateTimeHelper.toInstant(hitDto.getTimestamp()));

        return hit;
    }
}
