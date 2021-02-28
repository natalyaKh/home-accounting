package smilyk.homeacc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import smilyk.homeacc.model.Subcategory;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    Optional<Subcategory> findBySubcategoryNameAndUserUuid(String subcategoryName, String userUuid);

    Optional<List<Subcategory>> findByUserUuid(String userUuid);
}
