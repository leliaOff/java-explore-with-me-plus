package ru.practicum.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.HitDto;
import ru.practicum.server.request.RequestHitDto;
import ru.practicum.server.service.HitService;

@RestController
@RequestMapping(path = "/hit")
public class HitController {
    private final HitService hitService;

    @Autowired
    HitController(HitService hitService) {
        this.hitService = hitService;
    }

    @PostMapping
    public HitDto create(@RequestBody RequestHitDto request) {
        return hitService.create(request);
    }
}
