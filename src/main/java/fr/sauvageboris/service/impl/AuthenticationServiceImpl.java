package fr.sauvageboris.service.impl;

import fr.sauvageboris.dto.request.SignUpRequest;
import fr.sauvageboris.dto.request.SigninRequest;
import fr.sauvageboris.dto.response.JwtAuthenticationResponse;
import fr.sauvageboris.repository.UserRepository;
import fr.sauvageboris.repository.entity.Role;
import fr.sauvageboris.repository.entity.RoleEnum;
import fr.sauvageboris.repository.entity.User;
import fr.sauvageboris.service.AuthenticationService;
import fr.sauvageboris.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Implémentation du service d'authentification

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        // Crée un nouvel utilisateur avec les détails fournis
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .roleList(Arrays.asList(new Role(RoleEnum.ROLE_USER))).build();

        // Enregistre l'utilisateur dans la base de données
        userRepository.save(user);

        // Génère un jeton JWT pour l'utilisateur
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest dto) {
        // Authentifie l'utilisateur avec l'AuthenticationManager
        UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordToken);
        // Mise à jour du contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Recherche l'utilisateur par e-mail
        User user = userRepository
                .findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        // Génère un jeton JWT pour l'utilisateur authentifié
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
