package com.javarush.quest.core.repository;

import java.util.HashMap;
import java.util.Map;

public abstract class Repository<K, T> {
    protected Map<K, T> repository = new HashMap<>();

    public void save(K id, T entity) { repository.put(id, entity); }

    public void saveAll(Map<K, T> someMap) {
        repository.putAll(someMap);
    }

    public T getById(K id) {
        return repository.get(id);
    }

    public boolean isExists(K id) {
        return repository.containsKey(id);
    }

    public boolean isEmpty() {
        return repository.isEmpty();
    }
}
