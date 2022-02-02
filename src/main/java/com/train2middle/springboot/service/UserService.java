package com.train2middle.springboot.service;

import com.train2middle.springboot.model.User;
import com.train2middle.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User User) {
        User.setPassword(passwordEncoder.encode(User.getPassword()));
        return userRepository.save(User);
    }

    public User findByLogin(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByLoginAndPassword(String username, String password) {
        User User = findByLogin(username);
        if (User != null) {
            if (passwordEncoder.matches(password, User.getPassword())) {
                return User;
            }
        }
        return null;
    }
}
