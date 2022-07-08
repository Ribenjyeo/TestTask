package ru.ribenjyeo.saoWEB.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ribenjyeo.saoWEB.model.domain.Animal;
import ru.ribenjyeo.saoWEB.model.domain.EAnimal;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {
    Optional<Animal> findByType(EAnimal type);

    Boolean existsByNickname(String nickname);

    List<Animal> findAnimalByCreatedBy(String username);
}
