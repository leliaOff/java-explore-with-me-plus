package ru.practicum.server.storage.stat;

import ru.practicum.server.model.Stat;

import java.util.Collection;
import java.util.List;

public interface StatStorage {

    Collection<Stat> get(String start, String end);

    Collection<Stat> get(String start, String end, List<String> uris);

    Collection<Stat> getUnique(String start, String end);

    Collection<Stat> getUnique(String start, String end, List<String> uris);
}
