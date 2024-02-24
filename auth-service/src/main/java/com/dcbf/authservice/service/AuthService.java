package com.dcbf.authservice.service;


import com.dcbf.authservice.config.JwtService;
import com.dcbf.authservice.constant.Role;
import com.dcbf.authservice.entity.User;
import com.dcbf.authservice.exception.UserAlreadyExistException;
import com.dcbf.authservice.exception.UserNotFoundException;
import com.dcbf.authservice.payload.AuthRequest;
import com.dcbf.authservice.payload.AuthResponse;
import com.dcbf.authservice.payload.SignupRequest;
import com.dcbf.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse signup(SignupRequest request) {
        var dbUser = userRepository.findByEmail(request.getEmail());
//        if (dbUser.isPresent()) {
//            throw new UserAlreadyExistException("User already registered.");
//        }
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw new UserNotFoundException("Not found user for username: " + request.getEmail());
        }
        var jwtToken = jwtService.generateToken(user.get());
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
