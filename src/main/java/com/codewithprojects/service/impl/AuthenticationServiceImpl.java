package com.codewithprojects.service.impl;

import com.codewithprojects.dto.SignUpRequest;
import com.codewithprojects.entity.Role;
import com.codewithprojects.entity.User;
import com.codewithprojects.exception.UserAlreadyExistsException;
import com.codewithprojects.repository.UserRepository;
import com.codewithprojects.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //lets create a method to accept the sign up request and create a user and save it in the repository
    public User signup(SignUpRequest signUpRequest)
    {
        // Check if the email already exists
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException(
                    String.format("A user with the email '%s' already exists.", signUpRequest.getEmail()));
        }
        //in this method, lets create a new user
        User user = new User();
        //now we need to set the details for this user
        user.setEmail(signUpRequest.getEmail());
        user.setFirstname(signUpRequest.getFirstName());
        user.setSecondname(signUpRequest.getLastName());
        //lets assign a role to this user
        user.setRole(Role.USER);
        //set the password for this user, we will use passwordEncoder to encrypt a raw password
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        //at last, save this user in the user repository
        return userRepository.save(user);
    }
}
