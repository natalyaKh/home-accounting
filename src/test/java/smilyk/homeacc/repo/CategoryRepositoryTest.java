package smilyk.homeacc.repo;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import smilyk.homeacc.enums.CategoryType;
import smilyk.homeacc.model.Category;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
class CategoryRepositoryTest {

 @Autowired
 CategoryRepository categoryRepository;

    @Test
    void findByCategoryNameAndUserUuid() {
        Category outputCategory = Category.builder()
        .categoryName("Products")
        .categoryUuid("1111")
        .userUuid("2222")
        .deleted(false)
        .description("")
        .type(CategoryType.OUTPUT)
        .build();

        Category inputCategory = Category.builder()
            .categoryName("Dress")
            .categoryUuid("1112")
            .userUuid("2222")
            .deleted(false)
            .description("")
            .type(CategoryType.INPUT)
            .build();
        categoryRepository.save(outputCategory);
        categoryRepository.save(inputCategory);
        Category inputCat = categoryRepository.findByCategoryNameAndUserUuid("Products", "2222").get();

        assertEquals(outputCategory, inputCat);


    }
}
