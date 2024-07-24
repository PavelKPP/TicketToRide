package com.tickettoride.tickettoride.servicetests;

import com.tickettoride.exception.AuthorizationException;
import com.tickettoride.model.UserModel;
import com.tickettoride.repository.UserRepository;
import com.tickettoride.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserModel userModel;

    @BeforeEach
    void setUp() {
        userModel = new UserModel();
        userModel.setEmailAddress("test@example.com");
        userModel.setPassword("password");
        userModel.setFirstName("John");
        userModel.setSecondName("Doe");
        userModel.setTravellerId(12345L);
        userModel.setBalance(new BigDecimal(100));
    }

    @Test
    void registerUser_whenValidInputs_thenUserRegistered() {
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        UserModel registeredUser = userService.registerUser("test@example.com", "password", "John", "Doe");

        assertNotNull(registeredUser);
        assertEquals("test@example.com", registeredUser.getEmailAddress());
        assertEquals("John", registeredUser.getFirstName());
        assertEquals("Doe", registeredUser.getSecondName());
        assertEquals(new BigDecimal(100), registeredUser.getBalance());

        verify(userRepository, times(1)).save(any(UserModel.class));
    }

    @Test
    void registerUser_whenEmailAndPasswordNull_thenThrowAuthorizationException() {
        assertThrows(AuthorizationException.class, () -> {
            userService.registerUser(null, null, "John", "Doe");
        });

        verify(userRepository, never()).save(any(UserModel.class));
    }

    @Test
    void authenticateUser_whenValidCredentials_thenReturnUser() {
        when(userRepository.findByEmailAddressAndPassword("test@example.com", "password")).thenReturn(Optional.of(userModel));

        UserModel authenticatedUser = userService.authenticateUser("test@example.com", "password");

        assertNotNull(authenticatedUser);
        assertEquals("test@example.com", authenticatedUser.getEmailAddress());

        verify(userRepository, times(1)).findByEmailAddressAndPassword("test@example.com", "password");
    }

    @Test
    void authenticateUser_whenInvalidCredentials_thenThrowException() {
        when(userRepository.findByEmailAddressAndPassword("test@example.com", "password")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.authenticateUser("test@example.com", "password");
        });

        verify(userRepository, times(1)).findByEmailAddressAndPassword("test@example.com", "password");
    }

    @Test
    void updateTicketPriceByTravellerId_whenValidId_thenUpdateBalance() {
        when(userRepository.getUserModelByTravellerId(12345L)).thenReturn(userModel);
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        UserModel updatedUser = userService.updateTicketPriceByTravellerId(12345L, new BigDecimal(50));

        assertNotNull(updatedUser);
        assertEquals(new BigDecimal(50), updatedUser.getBalance());

        verify(userRepository, times(1)).getUserModelByTravellerId(12345L);
        verify(userRepository, times(1)).save(any(UserModel.class));
    }
}
