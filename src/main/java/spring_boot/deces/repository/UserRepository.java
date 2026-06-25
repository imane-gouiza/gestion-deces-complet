package spring_boot.deces.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_boot.deces.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); // Vérifie bien que c'est "User" et pas "Optional<User>"
}