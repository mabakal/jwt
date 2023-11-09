package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.securityconfig.JwtUtil;
import com.services.UtilisateurService;
import com.users.Utilisateur;

@RestController
public class Registration {

    @Autowired
    private UtilisateurService utilisateurService;
    
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/inscription")
    public ResponseEntity<String> inscrireUtilisateur(@RequestBody Utilisateur utilisateur) {
       

        utilisateurService.enregistrerUtilisateur(utilisateur);
        
        final UserDetails userDetails = utilisateurService.loadUserByUsername(utilisateur.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(jwt);

    }
}
