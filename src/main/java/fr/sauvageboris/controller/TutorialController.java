package fr.sauvageboris.controller;

import fr.sauvageboris.repository.entity.Tutorial;
import fr.sauvageboris.repository.TutorialRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/tutorials")
public class TutorialController {

    private TutorialRepository tutorialRepository;

    @GetMapping
    public ResponseEntity<?> fetchAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tutorialRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createTutorial(@RequestBody Tutorial tutorial) {
        tutorialRepository.save(tutorial);
        return ResponseEntity
                .status(HttpStatus.CREATED).build();
    }


}

