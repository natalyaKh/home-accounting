package smilyk.homeacc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import smilyk.homeacc.model.Category;
//TODO - спрятать удаленные категории
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryNameAndUserUuid(String categoryName, String userUuid);

    Optional<List<Category>> findByUserUuid(String userUuid);

    Optional<Category> findByCategoryUuidAndUserUuid(String categoryUuid, String userUuid);

    Optional<Category> findByCategoryUuid(String categoryUuid);
}
