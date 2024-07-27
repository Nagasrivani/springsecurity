package com.codewithprojects.dto;


import lombok.Data;

@Data
//we need a dto for register request
public class SignUpRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
