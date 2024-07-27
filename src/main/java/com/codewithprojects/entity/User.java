package com.codewithprojects.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

 //Entity class representing a user in the system.
 //Implements UserDetails for Spring Security integration.
@Data
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // First name of the user
    private String firstname;

    // Last name of the user
    private String secondname;

    // Email of the user, used for login
    private String email;

    // Password of the user
    private String password;

    // Role of the user, should be an enum that implements GrantedAuthority
    @Enumerated(EnumType.STRING)
    private Role role;

     //Returns the authorities granted to the user.
     //Wraps the user's role in a singleton list.
     //@return a collection of granted authorities
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assuming Role implements GrantedAuthority; if not, convert appropriately
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    //Returns the username of the user.which is th eemail
    @Override
    public String getUsername() {
        return email;
    }

     //Indicates whether the account has expired.
     //In this implementation, we assume the account is never expired.
     // @return true if the account is non-expired, false otherwise
    @Override
    public boolean isAccountNonExpired() {
        // Implement your logic to determine if the account is expired
        return true;
    }

     //Indicates whether the account is locked.
     //In this implementation, we assume the account is never locked.
     //@return true if the account is non-locked, false otherwise
    @Override
    public boolean isAccountNonLocked() {
        // Implement your logic to determine if the account is locked
        return true;
    }

     //Indicates whether the credentials (password) have expired.
     //In this implementation, we assume the credentials are never expired.
     //@return true if the credentials are non-expired, false otherwise
    @Override
    public boolean isCredentialsNonExpired() {
        // Implement your logic to determine if the credentials are expired
        return true;
    }

     //Indicates whether the user is enabled.
     //In this implementation, we assume the user is always enabled.
     //@return true if the user is enabled, false otherwise
    @Override
    public boolean isEnabled() {
        // Implement your logic to determine if the user is enabled
        return true;
    }
}
