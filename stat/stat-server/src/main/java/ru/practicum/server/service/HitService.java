package ru.practicum.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.server.model.Hit;
import ru.practicum.server.model.HitMapper;
import ru.practicum.server.repository.HitRepository;
import ru.practicum.server.request.RequestHitDto;

@Service
@Slf4j
public class HitService {
    private final HitRepository repository;

    @Autowired
    HitService(HitRepository repository) {
        this.repository = repository;
    }

    public HitDto create(RequestHitDto request) {
        Hit hit = HitMapper.toHit(request);
        hit = repository.save(hit);
        return HitMapper.toHitDto(hit);
    }
}
