package fr.sauvageboris.controller;

import fr.sauvageboris.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<?> fetchAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryRepository.findAll());
    }

}
