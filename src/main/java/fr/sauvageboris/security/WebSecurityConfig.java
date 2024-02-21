package fr.sauvageboris.security;

import fr.sauvageboris.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static fr.sauvageboris.security.SecurityConstants.SIGN_IN_URI_ENDING;
import static fr.sauvageboris.security.SecurityConstants.SIGN_UP_URI_ENDING;

@Configuration
@EnableWebSecurity // Activation de la configuration personnalisée de la sécurité
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserService userService;
    private final AuthJwtTokenFilter authenticationJwtTokenFilter;

    // Configuration du fournisseur d'authentification de Spring Security
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    //
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Initialisation d'une instance de Bcrypt pour hacher les mots de passes
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Définition des filtres sur les URLs
    // Accès en fonction d'un état authentifié ou non
    // Accès en fonction de l'utilisateur authentifié (désactivé, expiré..etc)
    // Accès en fonction des roles
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Configuration de la session Spring sécurité : AUCUNE session ne sera créée coté serveur
        // Moins coûteux et inutile lorsque nous sommes dans une configuration RESTFul
        http.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())

                // Configuration des règles d'autorisations concernant les requêtes HTTP
                .authorizeHttpRequests(requests -> {
                    requests
                            // Toutes les requêtes HTTP /api/users sont autorisées pour tout le monde (authentifié ou non)
//                            .requestMatchers("/api/users").permitAll()
                            .requestMatchers(SIGN_IN_URI_ENDING).permitAll()
                            .requestMatchers(SIGN_UP_URI_ENDING).permitAll()
                            .requestMatchers("/api/categories").permitAll()
                            .requestMatchers("/api/tutorials").permitAll()
                            .requestMatchers("api/admin/**").hasAuthority("ADMIN")
                            // Toutes les autres requêtes HTTP nécessitent une authentification
                            .anyRequest().authenticated();
                })
                // désactivation de la gestion des en-têtes CORS au sein de Spring Security
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                // désactivation du CSRF (Cross-Site Request Forgery)
                .csrf(new Customizer<CsrfConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
                        httpSecurityCsrfConfigurer.disable();
                    }
                })


                // Ajout un filtre personnalisé qui s'executera avant le filtre UsernamePasswordAuthenticationFilter
                // Ce filtre pour gérer l'authentification basée sur le JWT reçu dans les en-têtes des requêtes
                // Le filtre UsernamePasswordAuthenticationFilter est un filtre de base de Spring Security
                // Il est exécuté pour gérer l'authentification par username et mot de passe
                .addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class)

                // Configuration de la session Spring sécurité : AUCUNE session ne sera créée coté serveur
                // Moins coûteux et inutile lorsque nous sommes dans une configuration RESTFul
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //Make the below setting as * to allow connection from any hos
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
