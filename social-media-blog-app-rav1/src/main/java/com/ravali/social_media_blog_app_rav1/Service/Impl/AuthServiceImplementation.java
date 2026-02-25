package com.ravali.social_media_blog_app_rav1.Service.Impl;

import com.ravali.social_media_blog_app_rav1.DTO.AuthResponse;
import com.ravali.social_media_blog_app_rav1.DTO.LoginRequest;
import com.ravali.social_media_blog_app_rav1.DTO.SignUpRequest;
import com.ravali.social_media_blog_app_rav1.Entity.User;
import com.ravali.social_media_blog_app_rav1.Repository.UserRepository;
import com.ravali.social_media_blog_app_rav1.Utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImplementation implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public AuthResponse signup(SignUpRequest signUpRequest) {

        //Check if username already exists
        if(userRepository.findByUsername(signUpRequest.getUsername()).isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }

        //check if email already exists
        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }

        //Create new user and save to database
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setFullName(signUpRequest.getFullName());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setCreatedAt(System.currentTimeMillis());
        user.setUpdatedAt(System.currentTimeMillis());

        User savedUser = userRepository.save(user);

        //Generate token
        String token = jwtUtil.generateToken(savedUser.getId(), savedUser.getUsername());

        return new AuthResponse(token, "User registered successfully", savedUser.getId(), savedUser.getUsername());
    }



    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        //Find user by username
       User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

       //validate password
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Invalid username or password");
        }

        //Generate token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        return new AuthResponse(token, "Login successful", user.getId(), user.getUsername());
    }
}
