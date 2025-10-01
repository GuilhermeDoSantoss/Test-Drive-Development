package com.isiflix.TDD.repository;

import com.isiflix.TDD.model.Event;
import org.springframework.data.repository.ListCrudRepository;

public interface EventRepository extends ListCrudRepository<Event, Integer> {
}
