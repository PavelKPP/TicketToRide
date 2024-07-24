package com.tickettoride.service;


import com.tickettoride.exception.AuthorizationException;
import com.tickettoride.model.UserModel;
import com.tickettoride.repository.UserRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This function is taking care of user registration process.
     *
     * @param email      user's email address
     * @param password   user's password
     * @param firstName  user's first name
     * @param secondName user's second name
     * @return registered user model.
     * @throws AuthorizationException id email or password are equals to null.
     */
    public UserModel registerUser(String email, String password,
                                  String firstName, String secondName) {
        if (email == null && password == null) {
            throw new AuthorizationException();
        } else {
            UserModel userModel = new UserModel();
            userModel.setEmailAddress(email);
            userModel.setPassword(password);
            userModel.setFirstName(firstName);
            userModel.setSecondName(secondName);
            userModel.setTravellerId(generateTravellersIdForUser());
            userModel.setBalance(new BigDecimal(100));
            return userRepository.save(userModel);
        }
    }

    /**
     * This function is taking care of user's authentication process.
     *
     * @param email    user's email address.
     * @param password user's password
     * @return authenticated user model
     * @throws NoSuchElementException if user could not be found.
     */
    public UserModel authenticateUser(String email, String password) {
        return userRepository.findByEmailAddressAndPassword(email, password).orElseThrow();
    }



    /**
     * Updates user's balance by his traveller id.
     *
     * <p><b>Important Notice:</b> This function was created for feature feautures of the project,
     * such as buy ticket for an instance.</p>
     *
     * @param travellerId user's traveller id.
     * @param price       new price.
     * @return обновленная модель пользователя
     */
    public UserModel updateTicketPriceByTravellerId(Long travellerId, BigDecimal price){
        UserModel userModelToUpdate = userRepository.getUserModelByTravellerId(travellerId);
        userModelToUpdate.setBalance(price);
        return userRepository.save(userModelToUpdate);
    }


    /**
     * This function is auxiliary function for registerUser() function.
     * It was developed for generating random travellerId value for user.
     *
     * @return randomly created traveller_id value for UserModel.
     */
    private Long generateTravellersIdForUser() {
        return RandomUtils.nextLong(10000, 99999);
    }
}
