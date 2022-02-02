package com.train2middle.springboot.controller;

import com.train2middle.springboot.config.JwtProvider;
import com.train2middle.springboot.model.AuthCredits;
import com.train2middle.springboot.model.AuthResponse;
import com.train2middle.springboot.model.User;
import com.train2middle.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody AuthCredits registrationRequest) {
        User u = new User();
        u.setPassword(registrationRequest.getPassword());
        u.setUsername(registrationRequest.getUsername());
        return ResponseEntity.ok(userService.saveUser(u));
    }

    @PostMapping("/login")
    public AuthResponse auth(@RequestBody AuthCredits request) {
        User user = userService.findByLoginAndPassword(request.getUsername(), request.getPassword());
        String token = jwtProvider.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
