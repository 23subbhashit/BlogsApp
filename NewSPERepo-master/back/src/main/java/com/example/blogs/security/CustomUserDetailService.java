package com.example.blogs.security;

import com.example.blogs.Repositories.UserRepository;
//import com.example.blogs.entities.User;
import com.example.blogs.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //User user=this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","email: "+username,0));
        //return user;
        return userRepo.findByEmail(username);
    }
}
