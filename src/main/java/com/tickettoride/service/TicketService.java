package com.tickettoride.service;

import com.tickettoride.model.TicketModel;
import com.tickettoride.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }


    /**
     * This function was developed to register ticket and save it to the
     * database.
     *
     * @param travellerId       traveller id taken from UserModel.
     * @param departureCity     departure city.
     * @param arrivalCity       arrival city.
     * @param price             price of the ticket based on findOptimalRoute() func
     * @param segmentsQuantity  segment quantity in the most optimal route.
     *
     * @return registered user model
     */
    public TicketModel registerTicket(Long travellerId, String departureCity,
                                      String arrivalCity, BigDecimal price, int segmentsQuantity){
        TicketModel ticket = new TicketModel();
        ticket.setTravellers_id(travellerId);
        ticket.setDepartureCity(departureCity);
        ticket.setArrivalCity(arrivalCity);
        ticket.setPrice(price);
        ticket.setSegments(segmentsQuantity);
        return ticketRepository.save(ticket);
    }
}
