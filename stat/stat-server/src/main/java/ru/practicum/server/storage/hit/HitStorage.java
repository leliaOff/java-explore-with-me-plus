package ru.practicum.server.storage.hit;

import ru.practicum.server.model.Hit;

public interface HitStorage {

    Hit create(Hit hit);
}
