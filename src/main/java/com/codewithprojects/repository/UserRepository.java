package com.codewithprojects.repository;

import com.codewithprojects.entity.Role;
import com.codewithprojects.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    //on the start up of our application, we need to check if the admin account is not there in our database
    //then only we can create an admin account, otherwise we can skip
    User findByRole(Role role);

}
