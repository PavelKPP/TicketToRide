package com.tickettoride.mailingsystem;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class MailService {

    private static String DOMIAN_NAME = "sandbox7f046085cc614ab0a9c41f783f9a6879.mailgun.org";
    private static String  API_KEY = "0f1db83d-968ab546";
    private static String subjectOfEmailRegistrationEmail = "Welcome to the TicketToRide!";
    private static String textOfEmailRegistrationEmail = "Thanks for choosing us! We have a bonus for u! Your balance is now 100GBP";
    private static String subjectOfEmailWithOptimalTicket = "ticket";
    private static String textOfEmailWithOptimalTicket = "here is the most optimal ticket for you!:";

    private static String fromWho = "tickettoride887@gmail.com";


    public MailService() {

    }



    public int sendRegistrationMessage(String emailToSend) throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + DOMIAN_NAME + "/messages")
                .basicAuth("api", API_KEY)
                .queryString("from", fromWho)
                .queryString("to", emailToSend)
                .queryString("subject", subjectOfEmailRegistrationEmail)
                .queryString("text", textOfEmailRegistrationEmail)
                .asJson();
        System.out.println(request.getBody());
        return request.getStatus();
    }

    public int sendOptimalTicketMessage(String emailToSend) throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + DOMIAN_NAME + "/messages")
                .basicAuth("api", API_KEY)
                .queryString("from", fromWho)
                .queryString("to", emailToSend)
                .queryString("subject", subjectOfEmailWithOptimalTicket)
                .queryString("text", textOfEmailWithOptimalTicket)
                .asJson();
        System.out.println(request.getBody());
        return request.getStatus();
    }
}
