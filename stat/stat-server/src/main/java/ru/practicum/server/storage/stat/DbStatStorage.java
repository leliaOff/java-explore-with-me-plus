package ru.practicum.server.storage.stat;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.server.model.Stat;
import ru.practicum.server.repository.HitRepository;

import java.util.Collection;
import java.util.List;

@Component
@Qualifier("dbStatStorage")
public class DbStatStorage implements StatStorage {
    private final HitRepository repository;

    public DbStatStorage(HitRepository repository) {
        this.repository = repository;
    }

    public Collection<Stat> get(String start, String end) {
        return repository.getStat(start, end);
    }

    public Collection<Stat> get(String start, String end, List<String> uris) {
        return repository.getStat(start, end, uris);
    }

    public Collection<Stat> getUnique(String start, String end) {
        return repository.getUniqueStat(start, end);
    }

    public Collection<Stat> getUnique(String start, String end, List<String> uris) {
        return repository.getUniqueStat(start, end, uris);
    }
}
