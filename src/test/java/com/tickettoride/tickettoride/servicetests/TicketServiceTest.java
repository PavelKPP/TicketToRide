package com.tickettoride.tickettoride.servicetests;

import com.tickettoride.model.TicketModel;
import com.tickettoride.repository.TicketRepository;
import com.tickettoride.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private TicketModel ticketModel;

    @BeforeEach
    void setUp() {
        ticketModel = new TicketModel();
        ticketModel.setTravellers_id(12345L);
        ticketModel.setDepartureCity("Birmingham");
        ticketModel.setArrivalCity("London");
        ticketModel.setPrice(new BigDecimal(200));
        ticketModel.setSegments(1);
    }

    @Test
    void registerTicket_whenValidInputs_thenTicketRegistered() {
        when(ticketRepository.save(any(TicketModel.class))).thenReturn(ticketModel);

        TicketModel registeredTicket = ticketService.registerTicket(12345L, "Birmingham", "London", new BigDecimal(200), 1);

        assertNotNull(registeredTicket);
        assertEquals(12345L, registeredTicket.getTravellers_id());
        assertEquals("Birmingham", registeredTicket.getDepartureCity());
        assertEquals("London", registeredTicket.getArrivalCity());
        assertEquals(new BigDecimal(200), registeredTicket.getPrice());
        assertEquals(1, registeredTicket.getSegments());

        verify(ticketRepository, times(1)).save(any(TicketModel.class));
    }

    @Test
    void registerTicket_whenTravellerIdIsNull_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ticketService.registerTicket(null, "Birmingham", "London", new BigDecimal(200), 1);
        });

        verify(ticketRepository, never()).save(any(TicketModel.class));
    }

}

