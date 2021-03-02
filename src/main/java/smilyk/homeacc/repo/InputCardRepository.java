package smilyk.homeacc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import smilyk.homeacc.model.InputCard;

import java.util.List;
import java.util.Optional;

public interface InputCardRepository extends JpaRepository<InputCard, Long> {
    Optional<List<InputCard>> findAllByUserUuid(String userUuid);
}
