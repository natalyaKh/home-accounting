package smilyk.homeacc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import smilyk.homeacc.model.OutputCard;

import java.util.List;
import java.util.Optional;

public interface OutputCardRepository extends JpaRepository<OutputCard, Long> {

    Optional<List<OutputCard>> findAllByUserUuid(String userUuid);

    Optional<OutputCard> findByOutputCardUuid(String outputCardUuid);

    Optional<OutputCard> findByUserUuidAndOutputCardUuid(String userUuid, String outputCardUuid);

    @Query(value = "SELECT * FROM output WHERE user_uuid = :userUuid AND create_card_date > :date ", nativeQuery = true)
    List<OutputCard> getListOutputCatdsByUserAndDate(@Param("userUuid") String userUuid,
                                                     @Param("date") String chosenDate);
}
