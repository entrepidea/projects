package com.entrepidea.service.register;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
//@RequiredArgsConstructor
public class RegisterService {

    //Version#1: field autowiring is not a good idea
    //@Autowired
    //private UserRepository userRepository;

    //Version#2: Since Spring 5.x, no need to use keyword @Autowired for constructor injection.

    private final UserRepository userRepository;
    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //Version#3: thanks to Lombok, the constructor autowiring can be removed as boiler plate code.
    //NOTE: seem not working - not automatically wired. TODO
    //private UserRepository userRepository;

    public User registerUser(User user) {
        user.setRegistrationDate(LocalDateTime.now());
        return userRepository.save(user);
    }
}
