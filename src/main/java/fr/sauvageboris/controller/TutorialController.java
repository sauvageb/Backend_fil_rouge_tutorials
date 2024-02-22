package fr.sauvageboris.controller;

import fr.sauvageboris.dto.request.CreateTutorial;
import fr.sauvageboris.repository.CategoryRepository;
import fr.sauvageboris.repository.TutorialRepository;
import fr.sauvageboris.repository.entity.Tutorial;
import fr.sauvageboris.repository.entity.User;
import fr.sauvageboris.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/tutorials")
public class TutorialController {

    private TutorialRepository tutorialRepository;
    private CategoryRepository categoryRepository;
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> fetchAll() {
        List<Tutorial> tutorialList = (List<Tutorial>) tutorialRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tutorialList);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createTutorial(@RequestBody CreateTutorial dto) {
        Tutorial tutorial = new Tutorial();
        tutorial.setTitle(dto.getTitle());
        tutorial.setDescription(dto.getDescription());
        tutorial.setContent(dto.getContent());
        tutorial.setCategory(categoryRepository.findById(dto.getCategory().getId()).get());

        tutorial.setCreatedAt(LocalDateTime.now());

        Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();
        String connectedUserEmail = ((UserDetails) authenticationToken.getPrincipal()).getUsername();
        User user = (User) userService.userDetailsService().loadUserByUsername(connectedUserEmail);
        tutorial.setAuthor(user);


        tutorialRepository.save(tutorial);
        return ResponseEntity
                .status(HttpStatus.CREATED).build();
    }

    @PostMapping("/anonymous")
    @Transactional
    public ResponseEntity<?> createTutorialAnonymois(@RequestBody CreateTutorial dto) {
        Tutorial tutorial = new Tutorial();
        tutorial.setTitle(dto.getTitle());
        tutorial.setDescription(dto.getDescription());
        tutorial.setContent(dto.getContent());
        tutorial.setCategory(categoryRepository.findById(dto.getCategory().getId()).get());

        tutorial.setCreatedAt(LocalDateTime.now());

        User user = (User) userService.userDetailsService().loadUserByUsername("sauvageboris.pro@tutorial.fr");
        tutorial.setAuthor(user);

        tutorialRepository.save(tutorial);
        return ResponseEntity
                .status(HttpStatus.CREATED).build();
    }


}

