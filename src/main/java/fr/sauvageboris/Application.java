package fr.sauvageboris;

import com.github.javafaker.Faker;
import fr.sauvageboris.repository.CategoryRepository;
import fr.sauvageboris.repository.TutorialRepository;
import fr.sauvageboris.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final TutorialRepository tutorialRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner commandLineRunner() {
        return args -> {

//            // Categories
//            categoryRepository.save(new Category("Java"));
//            categoryRepository.save(new Category("Javascript"));
//            categoryRepository.save(new Category("Typescript"));
//            categoryRepository.save(new Category("PHP"));
//            categoryRepository.save(new Category("C#"));
//            categoryRepository.save(new Category("Angular"));
//            categoryRepository.save(new Category("Ruby"));
//            categoryRepository.save(new Category("Python"));
//            categoryRepository.save(new Category("C++"));
//            categoryRepository.save(new Category("Rust"));
//            Category category = new Category("VBA");
//
//            // Tutorials
//            User author = User
//                    .builder()
//                    .email("sauvageboris.pro@tutorial.fr")
//                    .firstName("Boris")
//                    .lastName("Sauvage")
//                    .password(passwordEncoder.encode("tutorial"))
//                    .roleList(Arrays.asList(new Role(RoleEnum.ROLE_USER)))
//                    .build();
//
//            List<Comment> comments = Arrays.asList(
//                    new Comment("Super tuto ! Bravo Jane", author),
//                    new Comment("Très intéressant, merci pour le partage", author)
//            );
//
//            Tutorial tutorial = new Tutorial(
//                    "Initiation au macro VBA",
//                    "Lorem ipsum dolor sit amet, consectetur adipisicing elit",
//                    "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Alias animi aperiam aspernatur cupiditate deserunt dignissimos dolor doloremque eligendi eos est excepturi expedita ipsam laboriosam laborum laudantium natus omnis quaerat quas, qui quibusdam ratione reiciendis tenetur ullam unde vel velit vitae voluptatum. Illum inventore ipsam odit perferendis ratione. At consequatur, ratione!",
//                    author,
//                    category,
//                    LocalDateTime.of(2023, 10, 01, 10, 00),
//                    comments
//            );
//            tutorialRepository.save(tutorial);
        };
    }

    @Bean
    Faker faker() {
        return new Faker(new Locale("fr"));
    }

}
