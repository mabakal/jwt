package com.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.users.Utilisateur;
import com.users.UtilisateurRepository;

@Service
public class UtilisateurService implements UserDetailsService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));
        return new User(utilisateur.getUsername(), utilisateur.getPassword(), new ArrayList<>());
    }
    
   	public void enregistrerUtilisateur(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		 utilisateurRepository.save(utilisateur);
	}

}
