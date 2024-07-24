package com.tickettoride;

import com.tickettoride.mailingsystem.MailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TicketToRideApplication {

    @Bean
    public MailService mailService() {
        return new MailService();
    }

    public static void main(String[] args) {
        SpringApplication.run(TicketToRideApplication.class, args);
    }

}
