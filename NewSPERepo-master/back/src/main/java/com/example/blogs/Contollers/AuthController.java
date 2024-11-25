package com.example.blogs.Controllers;

import com.example.blogs.Repositories.UserRepository;
import com.example.blogs.entities.User;
import com.example.blogs.payloads.JwtAuthRequest;
import com.example.blogs.payloads.JwtAuthResponse;
import com.example.blogs.payloads.UserDto;
import com.example.blogs.security.JwtTokenHelper;
import com.example.blogs.services.UserService;
import com.example.blogs.exceptions.ApiException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.example.blogs.security.CustomUserDetailService;

@RestController
//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger logger = LogManager.getLogger(AuthController.class);

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        try {
            this.authenticate(request.getUsername(), request.getPassword());
            User userDetails = userService.findByUsername(request.getUsername());

            if (passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
                String token = jwtTokenHelper.generateToken(userDetails.getUsername());

                JwtAuthResponse response = new JwtAuthResponse();
                response.setToken(token);
                response.setUser(this.mapper.map(userDetails, UserDto.class));
                logger.info("User logged in successfully: " + userDetails.getUsername());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                logger.warn("Failed login attempt for username: " + request.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            logger.error("Login failed for user: " + request.getUsername(), e);
            throw e;
        }
    }

    private void authenticate(String username, String password) throws ApiException {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            logger.error("Authentication failed for user: " + username, e);
            throw new ApiException("Invalid username or password !!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            UserDto registeredUser = this.userService.registerNewUser(userDto);
            logger.info("New user registered: " + userDto.getEmail());
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Registration failed for user: " + userDto.getEmail(), e);
            throw new ApiException("Registration failed", e);
        }
    }

    // Uncomment and update if needed
    // @GetMapping("/current-user/")
    // public ResponseEntity<UserDto> getUser(Principal principal) {
    //     User user = this.userRepo.findByEmail(principal.getName()).get();
    //     return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
    // }
}
