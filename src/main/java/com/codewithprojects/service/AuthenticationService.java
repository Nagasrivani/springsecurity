package com.codewithprojects.service;

import com.codewithprojects.dto.SignUpRequest;
import com.codewithprojects.entity.User;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);
}
