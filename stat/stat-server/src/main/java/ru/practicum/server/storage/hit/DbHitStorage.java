package ru.practicum.server.storage.hit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.practicum.server.model.Hit;
import ru.practicum.server.repository.HitRepository;

@Component
@Qualifier("dbHitStorage")
public class DbHitStorage implements HitStorage {
    private final HitRepository repository;

    public DbHitStorage(HitRepository repository) {
        this.repository = repository;
    }

    public Hit create(Hit hit) {
        return repository.save(hit);
    }
}
