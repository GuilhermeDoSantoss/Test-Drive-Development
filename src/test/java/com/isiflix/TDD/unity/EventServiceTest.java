package com.isiflix.TDD.unity;

import com.isiflix.TDD.exceptions.EventNotFoundException;
import com.isiflix.TDD.model.Event;
import com.isiflix.TDD.repository.EventRepository;
import com.isiflix.TDD.service.EventServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventServiceTest {

    @InjectMocks
    private EventServiceImpl service;

    @Mock
    private EventRepository repository;


    @Test
    public void shouldAddNewEvent() {
        // given
        Event evt = new Event();
        evt.setTitle("Masterclass TDD 2025");
        evt.setLocation("Online");
        evt.setStartDate(LocalDate.parse("2025-09-07"));
        evt.setEndDate(LocalDate.parse("2025-03-07"));
        evt.setStartTime(LocalTime.parse("20:30:00"));
        evt.setEndTime(LocalTime.parse("23:00:00"));
        evt.setPrice(0.0);

        Mockito.when(repository.save(evt)).thenReturn(evt);


        Event res = service.save(evt);
        assertNotNull(res);
        assertEquals(res.getPrettyName(), "masterclass-tdd-2025");
    }

    @DisplayName("Recuperando todos os eventos")
    @Test
    public void shouldReturnAllEvents() {
        Event evt1 = new Event();
        Event evt2 = new Event();
        Mockito.when(repository.findAll()).thenReturn(List.of(evt1, evt2));
        assertTrue(service.getAll().size() > 0);
    }

    @DisplayName("Caminho feliz recuperando um evento pelo seu pretty name")
    @Test
    public void shouldReturnValidEvent(){
        Event evt = new Event();
        evt.setId(1);
        evt.setLocation("Online");
        evt.setStartDate(LocalDate.parse("2025-09-07"));
        evt.setEndDate(LocalDate.parse("2025-03-07"));
        evt.setStartTime(LocalTime.parse("20:30:00"));
        evt.setEndTime(LocalTime.parse("23:00:00"));
        evt.setPrice(0.0);
        Mockito.when(repository.findByPrettyName("masterclass-tdd-2025")).thenReturn(evt);

        assertNotNull(service.getByPrettyName("masterclass-tdd-2025"));
    }

    @Test
    @DisplayName("caminho triste para recuperar evento que não existe")
    public void shouldNotReturnInvalidEvent() {
        Mockito.when(repository.findByPrettyName("masterclass-tdd-2026")).thenReturn(null);

        assertThrows(EventNotFoundException.class, () -> {
            service.getByPrettyName("masterclass-tdd-2026");
        });
    }

}
