package com.thejournalapp.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.thejournalapp.journalApp.entity.User;
import com.thejournalapp.journalApp.repository.UserRepository;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @ParameterizedTest
    @ValueSource(strings={
        "sankalp",
        "Janvi",
        "khushbu"
    })
    @Disabled
    public void testFindByUsername(String name) {
        assertNotNull(userRepository.findByUsername(name), "failed for " + name);
    }

    @Disabled
    @ArgumentsSource(UserArgumentProvider.class)
    @ParameterizedTest
    public void testCreateNewUser(User user) {
        assertTrue(userService.saveNewUser(user), "Failed for " + user);
    }
}
