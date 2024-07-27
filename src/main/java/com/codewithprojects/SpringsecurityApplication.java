package com.codewithprojects;

import com.codewithprojects.entity.Role;
import com.codewithprojects.entity.User;
import com.codewithprojects.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@SpringBootApplication
//1.ADMIN
//2.USER
//we will create an admin account by default at the start of our application
//to implement this, use CommandLineRunner
public class SpringsecurityApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityApplication.class, args);
    }
    //we write our logic to create an admin in this run method, but before that in the userRepository, we need to create
    //another method to find the user by the role
    public void run(String... args)
    {
        User adminAccount = userRepository.findByRole(Role.ADMIN);
        //if adminAccount is equal to null, then only we create an adminAccount
        if(null == adminAccount)
        {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setFirstname("admin");
            user.setSecondname("admin");
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }
}
