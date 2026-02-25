package com.ravali.social_media_blog_app_rav1.Controller;


import com.ravali.social_media_blog_app_rav1.DTO.AuthResponse;
import com.ravali.social_media_blog_app_rav1.DTO.LoginRequest;
import com.ravali.social_media_blog_app_rav1.DTO.SignUpRequest;
import com.ravali.social_media_blog_app_rav1.Service.Impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint for user registration
    //POST (/api/auth/signup)
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignUpRequest signUpRequest){
        try {
            AuthResponse response = authService.signup(signUpRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(
                    new AuthResponse(null, e.getMessage(), null, null),
                    HttpStatus.BAD_REQUEST
            );
        }

    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        try {
            AuthResponse response = authService.login(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(
                    new AuthResponse(null, e.getMessage(), null, null),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

}
