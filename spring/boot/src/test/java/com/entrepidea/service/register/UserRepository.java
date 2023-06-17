package com.entrepidea.service.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    public User save(User user){
        LOGGER.info("user={},user.getTime={}", user, user.getRegistrationDate());
        return user;
    }
}
