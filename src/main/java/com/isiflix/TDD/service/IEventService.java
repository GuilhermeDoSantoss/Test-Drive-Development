package com.isiflix.TDD.service;

import com.isiflix.TDD.model.Event;

import java.util.List;

public interface IEventService {

    public Event save(Event event);
    public List<Event> getAll();
    public Event getByPrettyName(String prettyName);
}
