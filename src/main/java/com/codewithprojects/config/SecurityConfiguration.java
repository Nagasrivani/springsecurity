package com.codewithprojects.config;

import com.codewithprojects.entity.Role;
import com.codewithprojects.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        //call the disable method to disable the csrf(cross-site request forgery) protection
        http.csrf(AbstractHttpConfigurer::disable)
                //lets name it as request
                .authorizeHttpRequests(request -> request
                        // Allow all requests to the specified endpoint patterns without authentication
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // Allow access to the admin endpoint only for users with ADMIN role
                        .requestMatchers("/api/v1/admin").hasAnyAuthority(Role.ADMIN.name())

                        // Allow access to the admin endpoint only for users with USER role
                        .requestMatchers("/api/v1/user").hasAnyAuthority(Role.USER.name())

                        .anyRequest()//add anyRequest() before authenticated()
                        .authenticated())//requires authentication for any other request

                //sessionManagement is the starting configuration for session management
                //setting sessionCreationPolicy to STATELESS (no sessions)
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class//adding jwt request filter before the UsernamePasswordAuthenticationFilter
                );
        return http.build();//building the SecurityFilterChain object
    }
    //lets create a authenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        //lets create an object for DaoAuthenticationProvider
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        //in this, we need to set the user details
        authenticationProvider.setUserDetailsService(userService.userDetailsService());

        //we need to set our password encoder
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }
    //lets create a passwordEncoder
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }
}
