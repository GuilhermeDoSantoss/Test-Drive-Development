package com.isiflix.TDD.service;

import com.isiflix.TDD.exceptions.EventNotFoundException;
import com.isiflix.TDD.model.Event;
import com.isiflix.TDD.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements IEventService {

    @Autowired
    private EventRepository repository;

    @Override
    public Event save(Event event) {
        event.setPrettyName(event.getTitle().replaceAll(" ", "-").toLowerCase());
        return repository.save(event);
    }

    @Override
    public List<Event> getAll() {
        return repository.findAll();
    }

    @Override
    public Event getByPrettyName(String prettyName) {
        Event res = repository.findByPrettyName(prettyName);
        if (res == null){
            throw new EventNotFoundException("Evento " + prettyName + " not found!");
        }
        return repository.findByPrettyName(prettyName);
    }
}
