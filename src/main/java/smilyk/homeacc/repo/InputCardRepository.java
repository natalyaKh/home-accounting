package smilyk.homeacc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import smilyk.homeacc.model.InputCard;

import java.util.List;
import java.util.Optional;

public interface InputCardRepository extends JpaRepository<InputCard, Long> {
    Optional<List<InputCard>> findAllByUserUuid(String userUuid);

    Optional<InputCard> findByInputCardUuid(String inputCardUuid);

    Optional<InputCard> findByUserUuidAndInputCardUuid(String userUuid, String inputCardUuid);

    @Query(value = "SELECT * FROM input WHERE user_uuid = :userUuid AND create_card_date > :date ", nativeQuery = true)
    List<InputCard> getListInputsCardByUserAndDate(@Param("userUuid") String userUuid,
                                                   @Param("date") String chosenDate);
}
