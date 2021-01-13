package smilyk.homeacc.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import smilyk.homeacc.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findUserByEmailVerificationToken(String token);
    Optional<User> findUserByUserUuidAndDeleted(String userUuid, boolean b);
    Optional<User> findByEmailAndDeleted(String email, boolean b);
    Optional<User> findByUserUuidAndDeleted(String userUuid, boolean b);
}
