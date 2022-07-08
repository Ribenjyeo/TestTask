package ru.ribenjyeo.saoWEB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ribenjyeo.saoWEB.model.request.LoginRequest;
import ru.ribenjyeo.saoWEB.repo.UserRepo;
import ru.ribenjyeo.saoWEB.service.jwt.JwtAuthResponse;
import ru.ribenjyeo.saoWEB.service.jwt.JwtUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired(required = true)
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private JwtUtils jwtUtils;

    private final String AUTHORIZATION_HEADER = "Authorization";

    // Авторизация пользователя путем получения токена
    @PostMapping("/token")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest request,
                                                       HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken (request.getUsername (), request.getPassword ());
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

}
