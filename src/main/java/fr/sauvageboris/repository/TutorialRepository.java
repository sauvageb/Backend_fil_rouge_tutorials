package fr.sauvageboris.repository;

import fr.sauvageboris.repository.entity.Tutorial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorialRepository extends CrudRepository<Tutorial, Long> {
}
