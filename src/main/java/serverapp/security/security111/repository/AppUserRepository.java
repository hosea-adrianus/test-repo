package serverapp.security.security111.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import serverapp.security.security111.models.AppUser;

import java.util.Optional;
@Repository
@Transactional(readOnly = true)
public interface AppUserRepository {
    Optional<AppUser> findByEmail(String email);
}
