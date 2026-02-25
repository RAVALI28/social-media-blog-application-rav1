package com.ravali.social_media_blog_app_rav1.Service.Impl;

import com.ravali.social_media_blog_app_rav1.DTO.AuthResponse;
import com.ravali.social_media_blog_app_rav1.DTO.LoginRequest;
import com.ravali.social_media_blog_app_rav1.DTO.SignUpRequest;

public interface AuthService {

    AuthResponse signup(SignUpRequest signUpRequest);

    AuthResponse login(LoginRequest loginRequest);
}
