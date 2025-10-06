package com.isiflix.TDD.service;

import com.isiflix.TDD.dto.SubscriptionDTO;
import com.isiflix.TDD.exceptions.EventNotFoundException;
import com.isiflix.TDD.exceptions.SubscriptionConflictExceptions;
import com.isiflix.TDD.model.Event;
import com.isiflix.TDD.model.Subscription;
import com.isiflix.TDD.model.User;
import com.isiflix.TDD.repository.EventRepository;
import com.isiflix.TDD.repository.SubscriptionRepository;
import com.isiflix.TDD.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements ISubscriptionService {

    @Autowired
    private SubscriptionRepository subRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public SubscriptionDTO createSubscription(String prettyName, User subscriber) {
        // passo 1 - verificar se o evento existe
        Event evt = eventRepository.findByPrettyName(prettyName);
        if (evt != null) {
            User user = userRepository.findByEmail(subscriber.getEmail());
            if (user == null) {
                user = userRepository.save(subscriber);
            }

            if (subRepository.findByEventAndSubscribedUser(evt, user) != null){
                throw new SubscriptionConflictExceptions("Inscrição ja existe para o usuario");
            }
            Subscription subscription = new Subscription();
            subscription.setSubscriptionUser(user);
            subscription.setEvent(evt);
            subscription.setIndicationUser(null);
            Subscription res = subRepository.save(subscription);
            return new SubscriptionDTO(res.getSubscriptionNumber(), "http://devstage.com/" + prettyName + "/" + user.getId());
        } else {
            throw new EventNotFoundException("Evento não existe!");
        }
    }
}
