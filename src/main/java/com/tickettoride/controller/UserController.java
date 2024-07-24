package com.tickettoride.controller;


import com.mashape.unirest.http.exceptions.UnirestException;
import com.tickettoride.mailingsystem.MailService;
import com.tickettoride.model.TicketModel;
import com.tickettoride.model.UserModel;
import com.tickettoride.pricecalculatorservice.OptimalRouteFinder;
import com.tickettoride.service.TicketService;
import com.tickettoride.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.math.BigDecimal;


/**
 * Controller class for managing user requests.
 * This class handles registration, login, and ticket processing requests.
 */
@Controller
@SessionAttributes({"userModel", "ticketModel"})
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final TicketService ticketService;
    private final MailService mailService;

    @Autowired
    public UserController(UserService userService, TicketService ticketService, MailService mailService) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.mailService = mailService;
    }



    /**
     * Creates a new user model to have only one for 1 user session.
     * globally per each function.
     *
     * @return new user's model.
     */
    @ModelAttribute("userModel")
    public UserModel userModel() {
        return new UserModel();
    }

    /**
     * Creates a new ticket model to have only one for 1 user session.
     * globally per each function.
     *
     * @return new ticket's model
     */
    @ModelAttribute("ticketModel")
    public TicketModel ticketModel() {
        return new TicketModel();
    }


    /**
     * Handles GET-request for the registration page.
     *
     * @param model presentation model.
     * @return registration page presentation name.
     */
    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new UserModel());
        return "register_page";
    }

    /**
     * Handles GET-request for the main menu page.
     *
     * @param userModel user's model which is global per session.
     * @param model     presentation model.
     * @return main menu page presentation name.
     */
    @GetMapping("/main_menu")
    public String getMainMenuPage(@ModelAttribute("userModel") UserModel userModel, Model model){
        model.addAttribute("userLogin", userModel.getFirstName());
        model.addAttribute("userBalance", userModel.getBalance());
        model.addAttribute("optimalTicketRequest", new TicketModel());
        return "main_menu";
    }

    /**
     * Handles GET-request for the optimal ticket page.
     *
     * @param userModel  user's model which is global per session.
     * @param ticketModel ticket's model.
     * @param model      presentation model.
     * @return optimal ticket page presentation name.
     */
    @GetMapping("/optimal_ticket")
    public String getSubmitTicketPage(@ModelAttribute("userModel") UserModel userModel, @ModelAttribute("ticketModel") TicketModel ticketModel, Model model) {
        model.addAttribute("userFirtsName", userModel.getFirstName());
        model.addAttribute("userSecondName", userModel.getSecondName());
        model.addAttribute("userBalance", userModel.getBalance());
        model.addAttribute("emailAddress", userModel.getEmailAddress());
        model.addAttribute("departureCity", ticketModel.getDepartureCity());
        model.addAttribute("arrivalCity", ticketModel.getArrivalCity());
        model.addAttribute("ticketPrice", ticketModel.getPrice());
        model.addAttribute("segmentsQuantity", ticketModel.getSegments());

        return "optimal_ticket";
    }


    /**
     * Handles GET-request for the login page.
     *
     * @param model presentation model.
     * @return login page presentation name.
     */
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new UserModel());
        return "login_page";
    }


    /**
     * Handles POST-request for user registration.
     *
     * @param userModel user's model which is global per session.
     * @return name of the presentational model, depending on registration result.
     * @throws UnirestException if error is occured during email sending.
     */
    @PostMapping("/register")
    public String register(@ModelAttribute UserModel userModel) throws UnirestException {
        log.info("register request" + userModel);
        UserModel registeredUser = userService.registerUser(userModel.getEmailAddress(), userModel.getPassword(),
                userModel.getFirstName(), userModel.getSecondName());

        if (registeredUser == null) {
            return "error_page";
        } else {
                mailService.sendRegistrationMessage(userModel.getEmailAddress());
                return "redirect:/login";

        }
//        return registeredUser == null ? "error_page" : "redirect:/login";
    }

    /**
     * Handles POST-request for logining in the system.
     *
     * @param userModel user's model which is global per session.
     * @param model     presentation model.
     * @return name of the presentational model, depending on logining result.
     */
    @PostMapping("/login")
    public String login(@ModelAttribute UserModel userModel, Model model) {
        log.info("login request");
        UserModel authenticatedUser = userService.authenticateUser(userModel.getEmailAddress(), userModel.getPassword());
        if(authenticatedUser != null) {
            model.addAttribute("userModel", authenticatedUser);
            return "redirect:/main_menu";
        } else {
            return "error_page";
        }
    }

    /**
     * Обрабатывает POST-запрос для обработки оптимального билета.
     * Handles POST-request to process seeking of the most optimal ticket.
     *
     * @param ticketModel ticket's model, which is global per session
     * @param userModel   user's model, which is global per session.
     * @param model       presentation model.
     * @return name of the presentational model after process of seeking the most optimal ticket.
     * @throws UnirestException if error is occurred due to mail sending.
     */
    @PostMapping("/optimal_ticket")
    public String processOptimalTicket(@ModelAttribute TicketModel ticketModel, @ModelAttribute("userModel") UserModel userModel, Model model) throws UnirestException {
        log.info("process optimal ticket request: " + ticketModel);

        int[] result = OptimalRouteFinder.findOptimalRoute(ticketModel.getDepartureCity(), ticketModel.getArrivalCity());
        int cost = result[0];
        int segments = result[1];

        ticketModel.setPrice(BigDecimal.valueOf(cost));
        ticketModel.setSegments(segments);
        model.addAttribute("ticketModel", ticketModel);

        ticketService.registerTicket(userModel.getTravellerId(), ticketModel.getDepartureCity(), ticketModel.getArrivalCity(), new BigDecimal(cost), segments);

        mailService.sendOptimalTicketMessage(userModel.getEmailAddress());

        return "redirect:/optimal_ticket";
    }
}
