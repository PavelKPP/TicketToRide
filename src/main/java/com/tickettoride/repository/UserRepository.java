package com.tickettoride.repository;

import com.tickettoride.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {

    Optional<UserModel> findByEmailAddressAndPassword(String Login, String password);
    UserModel getUserModelByTravellerId(Long id);

}
