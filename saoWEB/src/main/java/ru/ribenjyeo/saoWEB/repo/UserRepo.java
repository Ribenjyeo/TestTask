package ru.ribenjyeo.saoWEB.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ribenjyeo.saoWEB.model.domain.User;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<Object> findOneById(Long userId);
}
