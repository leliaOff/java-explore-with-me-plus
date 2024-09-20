package ru.practicum.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.dto.StatDto;
import ru.practicum.server.model.StatMapper;
import ru.practicum.server.storage.stat.StatStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatService {
    @Qualifier("dbStatStorage")
    private final StatStorage storage;

    @Autowired
    StatService(@Qualifier("dbStatStorage") StatStorage storage) {
        this.storage = storage;
    }

    public Collection<StatDto> get(String start, String end, List<String> uris, Boolean unique) {
        if (unique) {
            if (uris == null) {
                return storage.getUnique(start, end).stream().map(StatMapper::toDto).collect(Collectors.toList());
            }
            return storage.getUnique(start, end, uris).stream().map(StatMapper::toDto).collect(Collectors.toList());
        }
        if (uris == null) {
            return storage.get(start, end).stream().map(StatMapper::toDto).collect(Collectors.toList());
        }
        return storage.get(start, end, uris).stream().map(StatMapper::toDto).collect(Collectors.toList());
    }
}
