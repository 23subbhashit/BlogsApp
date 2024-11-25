package com.example.blogs.Repositories;

import com.example.blogs.entities.Role;
import com.example.blogs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    //Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email =:email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.role =:role")
    List<User> getAdmins(@Param("role") Role role);
}
