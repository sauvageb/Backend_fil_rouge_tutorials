package fr.sauvageboris.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    @Column(length = 3000)
    private String content;
    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    private User author;
    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;
    @NonNull
    private LocalDateTime createdAt;
    @NonNull
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Comment> comments;
}
