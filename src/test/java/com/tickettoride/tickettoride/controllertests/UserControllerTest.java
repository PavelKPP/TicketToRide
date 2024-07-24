package com.tickettoride.tickettoride.controllertests;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.tickettoride.controller.UserController;
import com.tickettoride.mailingsystem.MailService;
import com.tickettoride.model.TicketModel;
import com.tickettoride.model.UserModel;
import com.tickettoride.pricecalculatorservice.OptimalRouteFinder;
import com.tickettoride.service.TicketService;
import com.tickettoride.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TicketService ticketService;

    @Mock
    private MailService mailService;

    @InjectMocks
    private UserController userController;

    private Model model;
    private UserModel userModel;
    private TicketModel ticketModel;

    @BeforeEach
    void setUp() {
        model = new BindingAwareModelMap();
        userModel = new UserModel();
        userModel.setEmailAddress("test@example.com");
        userModel.setPassword("password");
        userModel.setFirstName("Pavlo");
        userModel.setSecondName("Kuzhel");
        userModel.setTravellerId(43423L);
        userModel.setBalance(new BigDecimal(100));

        ticketModel = new TicketModel();
        ticketModel.setTravellers_id(43423L);
        ticketModel.setDepartureCity("Birmingham");
        ticketModel.setArrivalCity("London");
        ticketModel.setPrice(new BigDecimal(200));
        ticketModel.setSegments(1);
    }

    @Test
    void getRegisterPage() {
        String viewName = userController.getRegisterPage(model);
        assertEquals("register_page", viewName);
        assertTrue(model.containsAttribute("registerRequest"));
    }

    @Test
    void getMainMenuPage() {
        String viewName = userController.getMainMenuPage(userModel, model);
        assertEquals("main_menu", viewName);
        assertTrue(model.containsAttribute("userLogin"));
        assertTrue(model.containsAttribute("userBalance"));
        assertTrue(model.containsAttribute("optimalTicketRequest"));
    }

    @Test
    void getSubmitTicketPage() {
        String viewName = userController.getSubmitTicketPage(userModel, ticketModel, model);
        assertEquals("optimal_ticket", viewName);
        assertTrue(model.containsAttribute("userFirtsName"));
        assertTrue(model.containsAttribute("userSecondName"));
        assertTrue(model.containsAttribute("userBalance"));
        assertTrue(model.containsAttribute("emailAddress"));
        assertTrue(model.containsAttribute("departureCity"));
        assertTrue(model.containsAttribute("arrivalCity"));
        assertTrue(model.containsAttribute("ticketPrice"));
        assertTrue(model.containsAttribute("segmentsQuantity"));
    }

    @Test
    void getLoginPage() {
        String viewName = userController.getLoginPage(model);
        assertEquals("login_page", viewName);
        assertTrue(model.containsAttribute("loginRequest"));
    }

    @Test
    void register_whenSuccessful_thenRedirectToLogin() throws UnirestException {
        when(userService.registerUser(anyString(), anyString(), anyString(), anyString())).thenReturn(userModel);

        String viewName = userController.register(userModel);

        assertEquals("redirect:/login", viewName);
        verify(mailService, times(1)).sendRegistrationMessage(anyString());
    }

    @Test
    void register_whenUserServiceReturnsNull_thenShowErrorPage() throws UnirestException {
        when(userService.registerUser(anyString(), anyString(), anyString(), anyString())).thenReturn(null);

        String viewName = userController.register(userModel);

        assertEquals("error_page", viewName);
        verify(mailService, never()).sendRegistrationMessage(anyString());
    }

    @Test
    void login_whenSuccessful_thenRedirectToMainMenu() {
        when(userService.authenticateUser(anyString(), anyString())).thenReturn(userModel);

        String viewName = userController.login(userModel, model);

        assertEquals("redirect:/main_menu", viewName);
        assertTrue(model.containsAttribute("userModel"));
    }

    @Test
    void login_whenAuthenticationFails_thenShowErrorPage() {
        when(userService.authenticateUser(anyString(), anyString())).thenReturn(null);

        String viewName = userController.login(userModel, model);

        assertEquals("error_page", viewName);
        assertFalse(model.containsAttribute("userModel"));
    }
}