package ru.practicum.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.StatDto;
import ru.practicum.server.service.StatService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/stats")
public class StatController {
    private final StatService statService;

    @Autowired
    StatController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping
    public Collection<StatDto> get(@RequestParam String start,
                                   @RequestParam String end,
                                   @RequestParam(required = false) List<String> uris,
                                   @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        return statService.get(start, end, uris, unique);
    }
}
