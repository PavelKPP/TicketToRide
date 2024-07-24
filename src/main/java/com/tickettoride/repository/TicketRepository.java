package com.tickettoride.repository;

import com.tickettoride.model.TicketModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<TicketModel, Long> {

}
