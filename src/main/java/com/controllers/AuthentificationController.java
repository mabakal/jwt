package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.AuthentificationRequest;
import com.dto.AuthentificationResponse;
import com.securityconfig.JwtUtil;
import com.services.UtilisateurService;

@RestController
public class AuthentificationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UtilisateurService utilisateurService;

    @PostMapping("/authentification")
    public ResponseEntity<?> authentifier(@RequestBody AuthentificationRequest authentificationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentificationRequest.getUsername(), authentificationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Identifiants invalides", e);
        }

        final UserDetails userDetails = utilisateurService.loadUserByUsername(authentificationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthentificationResponse(jwt));
    }
}
