package smilyk.homeacc.repo;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import smilyk.homeacc.model.Category;
import smilyk.homeacc.model.Subcategory;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
class SubcategoryRepositoryTest {

    @Autowired
    SubcategoryRepository subcategoryRepository;

    @Test
    void findBySubcategoryNameAndUserUuid() {
        Subcategory subcategory = Subcategory.builder()
            .userUuid("2222")
            .deleted(false)
            .description("this is subcategory")
            .subcategoryName("shoes")
            .subcategoryUuid("1111")
            .build();
        subcategoryRepository.save(subcategory);

        Subcategory restoredSubcategory = subcategoryRepository.findBySubcategoryNameAndUserUuid(
            subcategory.getSubcategoryName(), subcategory.getUserUuid()
        ).get();
        Assert.assertEquals(subcategory, restoredSubcategory);
    }
}
