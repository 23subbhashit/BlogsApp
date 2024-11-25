package com.example.blogs.services;

import com.example.blogs.Repositories.UserRepository;
import com.example.blogs.entities.Role;
import com.example.blogs.entities.User;
import com.example.blogs.exceptions.ResourceNotFoundException;
import com.example.blogs.payloads.UserDto;
import com.example.blogs.services.implementations.UserImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.blogs.entities.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserImplementation userService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");
        user.setAbout("Test user");
        user.setRole(USER);

        userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("John Doe");
        userDto.setEmail("johndoe@example.com");
        userDto.setAbout("Test user");
    }

    @Test
    void testRegisterNewUser() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(modelMapper.map(any(UserDto.class), eq(User.class))).thenReturn(user);
        when(userRepo.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDto);

        UserDto registeredUser = userService.registerNewUser(userDto);

        assertNotNull(registeredUser);
        assertEquals(userDto.getEmail(), registeredUser.getEmail());
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser() {
        when(modelMapper.map(any(UserDto.class), eq(User.class))).thenReturn(user);
        when(userRepo.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDto);

        UserDto createdUser = userService.createUser(userDto);

        assertNotNull(createdUser);
        assertEquals(userDto.getEmail(), createdUser.getEmail());
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser() {
        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDto);

        UserDto updatedUser = userService.updateUser(userDto, 1);

        assertNotNull(updatedUser);
        assertEquals(userDto.getEmail(), updatedUser.getEmail());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testUpdateUserNotFound() {
        when(userRepo.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(userDto, 1));
    }

    @Test
    void testGetUserById() {
        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));
        when(modelMapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDto);

        UserDto foundUser = userService.getUserById(1);

        assertNotNull(foundUser);
        assertEquals(userDto.getEmail(), foundUser.getEmail());
        verify(userRepo, times(1)).findById(1);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepo.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1));
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);

        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userDto);

        when(userRepo.findAll()).thenReturn(users);
        when(modelMapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDto);

        List<UserDto> allUsers = userService.getAllUsers();

        assertNotNull(allUsers);
        assertEquals(1, allUsers.size());
        verify(userRepo, times(1)).findAll();
    }

    @Test
    void testDeleteUser() {
        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));
        doNothing().when(userRepo).delete(any(User.class));

        userService.deleteUser(1);

        verify(userRepo, times(1)).delete(user);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepo.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1));
    }
}
