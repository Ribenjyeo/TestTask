package ru.ribenjyeo.saoWEB.controllers;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ru.ribenjyeo.saoWEB.service.jwt.JwtAuthResponse;
import ru.ribenjyeo.saoWEB.service.jwt.JwtUtils;
import ru.ribenjyeo.saoWEB.model.dto.UserDto;
import ru.ribenjyeo.saoWEB.repo.UserRepo;
import ru.ribenjyeo.saoWEB.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired(required = true)
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;


    private final String AUTHORIZATION_HEADER = "Authorization";

    private static final String CHECK_ERROR_MESSAGE = "Incorrect password";

    // Регистрация пользователя с входом в систему
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserDto.Create userDto, HttpServletResponse response) {
        HttpHeaders textPlainHeaders = new HttpHeaders ();
        textPlainHeaders.setContentType (MediaType.APPLICATION_JSON);

        if (StringUtils.isEmpty (userDto.getPassword ())) {
            return new ResponseEntity<> (CHECK_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        userService.registerAccount (userDto);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken (userDto.getUsername (), userDto.getPassword ());

        try {
            Authentication authentication = this.authenticationManager.authenticate (authenticationToken);
            SecurityContextHolder.getContext ().setAuthentication (authentication);
            String jwt = "Bearer " + jwtUtils.generateJwtToken (authentication);
            response.addHeader (AUTHORIZATION_HEADER, jwt);
            return ResponseEntity.ok (new JwtAuthResponse (jwt));

        } catch (AuthenticationException e) {
            return new ResponseEntity<> (Collections.singletonMap ("AuthenticationException", e.getLocalizedMessage ()), HttpStatus.UNAUTHORIZED);
        }
    }

    // Поиск пользователя по имени
    @GetMapping("/user/{username}")
    public ResponseEntity<UserDto.Response> getUser(@PathVariable String username) {
        return userRepo.findByUsername (username)
                .map (user -> modelMapper.map (user, UserDto.Response.class))
                .map (response -> ResponseEntity.ok ().body (response))
                .orElse (new ResponseEntity<> (HttpStatus.NOT_FOUND));
    }
}
