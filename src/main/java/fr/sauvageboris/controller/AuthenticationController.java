package fr.sauvageboris.controller;

import fr.sauvageboris.dto.request.SignUpRequest;
import fr.sauvageboris.dto.request.SigninRequest;
import fr.sauvageboris.dto.response.JwtAuthenticationResponse;
import fr.sauvageboris.security.SecurityConstants;
import fr.sauvageboris.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(SecurityConstants.SIGN_UP_URI_ENDING)
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }


    @PostMapping(SecurityConstants.SIGN_IN_URI_ENDING)
    public ResponseEntity<JwtAuthenticationResponse> authenticate(@RequestBody SigninRequest dto) {
        JwtAuthenticationResponse token = authenticationService.signin(dto);
        return ResponseEntity.ok(token);
    }


}
