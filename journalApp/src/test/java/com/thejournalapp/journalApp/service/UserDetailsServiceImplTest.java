package com.thejournalapp.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.thejournalapp.journalApp.entity.User;
import com.thejournalapp.journalApp.repository.UserRepository;

public class UserDetailsServiceImplTest {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Disabled
    @Test
    public void loadByUsernameTest() {
        User userEntity = new User("abc", "password");
        userEntity.setRoles(List.of("USER"));

        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(userEntity);

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("abc");

        assertNotNull(userDetails);
        assertEquals("abc", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

    @Disabled
    @Test
    public void loadByUsername_NotFound_ThrowsException() {
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userDetailsServiceImpl.loadUserByUsername("unknown"));
    }
}
