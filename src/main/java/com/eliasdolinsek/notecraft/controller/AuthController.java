package com.eliasdolinsek.notecraft.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliasdolinsek.notecraft.models.User;
import com.eliasdolinsek.notecraft.payload.request.LoginRequest;
import com.eliasdolinsek.notecraft.payload.request.SignupRequest;
import com.eliasdolinsek.notecraft.payload.response.JwtResponse;
import com.eliasdolinsek.notecraft.payload.response.MessageResponse;
import com.eliasdolinsek.notecraft.repository.UserRepository;
import com.eliasdolinsek.notecraft.security.jwt.JwtUtils;
import com.eliasdolinsek.notecraft.security.services.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwtToken, "Bearer", userDetails.getId(), userDetails.getEmail(),
                userDetails.getAuthoritiesAsStrings()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
