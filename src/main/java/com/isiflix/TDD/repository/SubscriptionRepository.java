package com.isiflix.TDD.repository;

import com.isiflix.TDD.model.Event;
import com.isiflix.TDD.model.Subscription;
import com.isiflix.TDD.model.User;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscribedUser(Event evt, User user);
}
