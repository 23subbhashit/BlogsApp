package com.example.blogs.services.implementations;

import com.example.blogs.Repositories.UserRepository;
import com.example.blogs.entities.Role;
import com.example.blogs.entities.User;
import com.example.blogs.payloads.UserDto;
import com.example.blogs.services.UserService;
import com.example.blogs.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.blogs.entities.Role.USER;

@Service
public class UserImplementation implements UserService {

    private static final Logger logger = LogManager.getLogger(UserImplementation.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);

        // encode the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // set role
        user.setRole(USER);

        User newUser = this.userRepo.save(user);
        logger.info("New user registered successfully: " + user.getEmail());
        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        logger.info("User created successfully: " + savedUser.getEmail());
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());

        User updatedUser = this.userRepo.save(user);
        logger.info("User updated successfully: " + updatedUser.getEmail());
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        logger.info("User retrieved successfully with ID: " + userId);
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(this::userToDto).collect(Collectors.toList());
        logger.info("All users retrieved successfully");
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
        logger.info("User deleted successfully with ID: " + userId);
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepo.findByEmail(username);
        logger.info("User found by username: " + username);
        return user;
    }

    private User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        logger.debug("Converted UserDto to User: " + userDto.getEmail());
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        logger.debug("Converted User to UserDto: " + user.getEmail());
        return userDto;
    }

    public List<User> getAdminList() {
        List<User> admins = userRepo.getAdmins(Role.ADMIN);
        logger.info("Admin list retrieved successfully");
        return admins;
    }

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        logger.info("User added successfully: " + user.getEmail());
    }
}
