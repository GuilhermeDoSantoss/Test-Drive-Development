package com.isiflix.TDD.unity;

import com.isiflix.TDD.dto.SubscriptionDTO;
import com.isiflix.TDD.exceptions.EventNotFoundException;
import com.isiflix.TDD.exceptions.SubscriptionConflictExceptions;
import com.isiflix.TDD.model.Event;
import com.isiflix.TDD.model.Subscription;
import com.isiflix.TDD.model.User;
import com.isiflix.TDD.repository.EventRepository;
import com.isiflix.TDD.repository.SubscriptionRepository;
import com.isiflix.TDD.repository.UserRepository;
import com.isiflix.TDD.service.SubscriptionServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionServiceImpl service;

    @Mock
    private SubscriptionRepository subRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @Test
    @DisplayName("Caso Base - Inserindo novo usuário em um evento existente na base")
    public void shouldAddNewUserOnExistingEvent(){
        User newUser = new User();
        newUser.setEmail("email@email.com");
        newUser.setName("Guilherme");

        User subscribed = new User();
        subscribed.setId(1);
        subscribed.setEmail("meuemail@email.com");
        subscribed.setName("Guilherme");

        Event evt = new Event();
        evt.setId(1);
        evt.setTitle("Code 2025");
        evt.setPrettyName("code-crafters-2025");
        
        Subscription sub = new Subscription();
        sub.setSubscriptionNumber(1);
        sub.setEvent(evt);
        sub.setSubscriptionUser(subscribed);
        sub.setIndicationUser(null);

        Subscription newSub = new Subscription();
        newSub.setSubscriptionNumber(1);
        newSub.setEvent(evt);
        newSub.setSubscriptionUser(subscribed);
        newSub.setIndicationUser(null);

        Mockito.when(userRepository.findByEmail(newUser.getEmail())).thenReturn(null);
        Mockito.when(userRepository.save(newUser)).thenReturn(subscribed);
        Mockito.when(eventRepository.findByPrettyName("code-crafters-2025")).thenReturn(evt);
        Mockito.when(subRepository.save(sub)).thenReturn(newSub);

        SubscriptionDTO dto = service.createSubscription("code-crafters-2025", newUser);
        assertNotNull(dto);
    }

    @Test
    @DisplayName("Caso Alternativo 1 - Inserindo usuário existente em evento existente")
    public void shouldAddExistingUserOnExistingEvent(){
        User subscribed = new User();
        subscribed.setId(100);
        subscribed.setEmail("meuemail@email.com");
        subscribed.setName("Guilherme");

        Event evt = new Event();
        evt.setId(1);
        evt.setTitle("Code 2025");
        evt.setPrettyName("code-crafters-2025");

        Subscription sub = new Subscription();
        sub.setSubscriptionNumber(100);
        sub.setEvent(evt);
        sub.setSubscriptionUser(subscribed);
        sub.setIndicationUser(null);

        Subscription newSub = new Subscription();
        newSub.setSubscriptionNumber(100);
        newSub.setEvent(evt);
        newSub.setSubscriptionUser(subscribed);
        newSub.setIndicationUser(null);

        Mockito.when(userRepository.findByEmail(subscribed.getEmail())).thenReturn(subscribed);
        Mockito.when(eventRepository.findByPrettyName("code-crafters-2025")).thenReturn(evt);
        Mockito.when(subRepository.save(sub)).thenReturn(newSub);

        SubscriptionDTO dto = service.createSubscription("code-crafters-2025", subscribed);
        assertNotNull(dto);
    }

    @Test
    @DisplayName("Caso Alternativo 2 - Não permite criar inscrição em evento que não existe")
    public void shouldNotAcceptInvalidEvent(){
        Mockito.when(eventRepository.findByPrettyName("qualquer um")).thenReturn(null);

        User subscriber = new User();
        subscriber.setId(1);
        subscriber.setEmail("teste@teste.com");
        subscriber.setName("Teste");

        assertThrows(EventNotFoundException.class, () -> {
            service.createSubscription("qualquer um", subscriber);
        });
    }

    @Test
    @DisplayName("Caso Alternativo 3 - Não pode gerar duas inscrições do mesmo usúario do mesmo evento")
    public void shouldDenyMultipleUserSubscriptionAtTheSameEvent() {
        User subscribed = new User();
        subscribed.setId(200);
        subscribed.setEmail("meuemail@email.com");
        subscribed.setName("Guilherme");

        Event evt = new Event();
        evt.setId(1);
        evt.setTitle("Code 2025");
        evt.setPrettyName("code-crafters-2025");

        Subscription sub = new Subscription();
        sub.setSubscriptionNumber(200);
        sub.setSubscriptionNumber(200);
        sub.setEvent(evt);
        sub.setSubscriptionUser(subscribed);
        sub.setIndicationUser(null);

        Mockito.when(eventRepository.findByPrettyName(evt.getPrettyName())).thenReturn(evt);
        Mockito.when(userRepository.findByEmail(subscribed.getEmail())).thenReturn(subscribed);
        Mockito.when(subRepository.findByEventAndSubscribedUser(evt, subscribed)).thenReturn(sub);

        assertThrows(SubscriptionConflictExceptions.class, () -> {
            service.createSubscription("code-crafters-2025", subscribed);
        });
    }

}
