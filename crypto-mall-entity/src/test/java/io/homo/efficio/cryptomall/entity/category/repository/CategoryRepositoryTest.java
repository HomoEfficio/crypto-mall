package io.homo.efficio.cryptomall.entity.category.repository;

import io.homo.efficio.cryptomall.entity.category.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-29
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void jpaTest() {

    }

    @Test
    public void whenFindByName_thenReturnCategory() {
        em.persist(
                new Category(
                        "헬스용품"
                )
        );
        em.flush();

        Category category = repository.findByName("헬스용품");

        assertThat(category.getName()).isEqualTo("헬스용품");
    }
}
