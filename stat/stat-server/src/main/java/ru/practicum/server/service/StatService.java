package ru.practicum.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.dto.StatDto;
import ru.practicum.server.helpers.DateTimeHelper;
import ru.practicum.server.model.StatMapper;
import ru.practicum.server.repository.HitRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatService {
    private final HitRepository repository;

    @Autowired
    StatService(HitRepository repository) {
        this.repository = repository;
    }

    public Collection<StatDto> get(String start, String end, List<String> uris, Boolean unique) {
        if (unique) {
            if (uris == null) {
                return repository.getUniqueStat(DateTimeHelper.toInstant(start), DateTimeHelper.toInstant(end)).stream().map(StatMapper::toDto).collect(Collectors.toList());
            }
            return repository.getUniqueStat(DateTimeHelper.toInstant(start), DateTimeHelper.toInstant(end), uris).stream().map(StatMapper::toDto).collect(Collectors.toList());
        }
        if (uris == null) {
            return repository.getStat(DateTimeHelper.toInstant(start), DateTimeHelper.toInstant(end)).stream().map(StatMapper::toDto).collect(Collectors.toList());
        }
        return repository.getStat(DateTimeHelper.toInstant(start), DateTimeHelper.toInstant(end), uris).stream().map(StatMapper::toDto).collect(Collectors.toList());
    }
}
