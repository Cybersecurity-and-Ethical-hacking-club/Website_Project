package esc.first.demo.Repositpries;

import esc.first.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByEmailConfirmationToken(String emailConfirmationToken);
    Optional<User> findByAdminConfirmationToken(String adminConfirmationToken);
    Boolean existsByEmail(String email);
    List<User> findByIsAdminConfirmedFalse();

    List<User> findByIsEmailConfirmedTrue();

    List<User> findByIsAdminConfirmedTrue();
    List<User> findByFirstNameIgnoreCase(String firstName);
    List<User> findByLastNameIgnoreCase(String lastName);
    List<User> findByEmailIgnoreCase(String email);

}
