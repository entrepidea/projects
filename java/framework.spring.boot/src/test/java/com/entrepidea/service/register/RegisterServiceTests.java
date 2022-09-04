package com.entrepidea.service.register;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
//import static org.junit.Assert.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 *
 * A demo of properly unit testing.
 * https://reflectoring.io/unit-testing-spring-boot/
 *
 * NOTE: it appears that once spring-boot-starter-test is added, Mockito, slf4j etc all come along.
 *
 * Date: 11/19/21
 *
 * */

//ExtendWith annotation is supported since Junit 5
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RegisterServiceTests {

    @Test
    public void savedUserHasRegistrationDate() {
        UserRepository userRepository = mock(UserRepository.class);
        RegisterService registerService = new RegisterService(userRepository);
        User user = new User("zaphod", "zaphod@mail.com");
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        User savedUser = registerService.registerUser(user);
        assertThat("saveuser is not null", savedUser.getRegistrationDate()!=null);
    }

}
