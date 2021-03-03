package smilyk.homeacc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import smilyk.homeacc.model.InputCard;
import smilyk.homeacc.model.OutputCard;

import java.util.List;
import java.util.Optional;

public interface OutputCardRepository extends JpaRepository<OutputCard, Long> {

    Optional<List<OutputCard>> findAllByUserUuid(String userUuid);

    Optional<OutputCard> findByOutputCardUuid(String outputCardUuid);

    Optional<OutputCard> findByUserUuidAndOutputCardUuid(String userUuid, String outputCardUuid);
}
