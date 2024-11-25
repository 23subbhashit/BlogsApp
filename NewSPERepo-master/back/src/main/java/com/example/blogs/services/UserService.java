package com.example.blogs.services;

import com.example.blogs.entities.User;
import com.example.blogs.payloads.UserDto;
import java.util.List;
public interface UserService {

    UserDto registerNewUser(UserDto user);
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);

    User findByUsername(String username);

    public List<User> getAdminList();

    public void addUser(User user);
}
