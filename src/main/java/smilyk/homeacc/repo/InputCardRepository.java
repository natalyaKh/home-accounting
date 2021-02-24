package smilyk.homeacc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import smilyk.homeacc.model.OutputCard;

public interface InputCardRepository extends JpaRepository<OutputCard, Long> {

}
