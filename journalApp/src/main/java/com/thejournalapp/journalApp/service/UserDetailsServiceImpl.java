package com.thejournalapp.journalApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.thejournalapp.journalApp.entity.User;
import com.thejournalapp.journalApp.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Username not found " + username);

        List<String> roles = user.getRoles();
        if (roles == null || roles.isEmpty()) {
            roles = List.of("USER");
        }

        return org.springframework.security.core.userdetails.User.builder()
                                                                 .username(username)
                                                                 .password(user.getPassword())
                                                                 .roles(roles.toArray(String[]::new))
                                                                 .build();

    }
}
