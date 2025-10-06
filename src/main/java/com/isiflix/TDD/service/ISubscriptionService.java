package com.isiflix.TDD.service;

import com.isiflix.TDD.dto.SubscriptionDTO;
import com.isiflix.TDD.model.User;

public interface ISubscriptionService {

    public SubscriptionDTO createSubscription(String prettyName, User subscriber);
}
