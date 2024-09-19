package ru.practicum.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.server.model.Hit;
import ru.practicum.server.model.HitMapper;
import ru.practicum.server.request.RequestHitDto;
import ru.practicum.server.storage.hit.HitStorage;

@Service
@Slf4j
public class HitService {
    @Qualifier("dbHitStorage")
    private final HitStorage storage;

    @Autowired
    HitService(@Qualifier("dbHitStorage") HitStorage storage) {
        this.storage = storage;
    }

    public HitDto create(RequestHitDto request) {
        Hit hit = HitMapper.toHit(request);
        hit = storage.create(hit);
        return HitMapper.toHitDto(hit);
    }
}
