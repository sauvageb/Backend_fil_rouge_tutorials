package fr.sauvageboris.dto.request;

import fr.sauvageboris.repository.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTutorial {
    private String title;
    private String description;
    private String content;
    private Category category;
}

