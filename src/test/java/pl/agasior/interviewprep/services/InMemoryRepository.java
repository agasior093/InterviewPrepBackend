package pl.agasior.interviewprep.services;

import pl.agasior.interviewprep.entities.Identity;

import java.util.*;

public abstract class InMemoryRepository<T extends Identity>  {
    private final Map<String, T> repository = new HashMap<>();

    protected abstract String generateId();

    public T save(T entity) {
        final var id = generateId();
        entity.setId(id);
        repository.put(id, entity);
        return entity;
    }

    public Optional<T> findById(String id) {
        return Optional.ofNullable(repository.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(repository.values());
    }

    public T update(T entity) {
        Objects.requireNonNull(entity.getId());
        return repository.put(entity.getId(), entity);
    }
}
