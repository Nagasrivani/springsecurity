package com.codewithprojects.controller;

import com.codewithprojects.dto.SignUpRequest;
import com.codewithprojects.entity.User;
import com.codewithprojects.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
//for sign up request
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    //the name for this method is signup
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest)
    {
        //call the signup method in authentiacationService
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }
}
