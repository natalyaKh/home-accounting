package smilyk.homeacc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import smilyk.homeacc.model.InputCard;

public interface InputCardRepository extends JpaRepository<InputCard, Long> {
}
