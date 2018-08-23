package io.homo.efficio.cryptomall.entity.category.repository;

import io.homo.efficio.cryptomall.entity.category.Category;
import io.homo.efficio.cryptomall.entity.category.exception.CategoryNotFoundException;
import io.homo.efficio.cryptomall.entity.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Test
    public void whenFindByCategory__thenReturnProductByCategory() {
        Category category = em.persist(
                new Category(
                        "헬스용품"
                )
        );
        Product product1 = new Product("라텍스 밴드 중급형", 28.00d);
        category.addProduct(product1);
        Product product2 = new Product("어디다쓰 헬스 장갑", 15.00d);
        category.addProduct(product2);

//        em.flush();  // No need to explicitly invoke flush, because it will be invoked by find***() below

        Category dbCategory = repository.findById(category.getId()).orElseThrow(CategoryNotFoundException::new);
        Set<Product> products = dbCategory.getProducts();

        assertThat(products.size()).isEqualTo(2);
        assertThat(products.contains(product1)).isTrue();
        assertThat(products.contains(product2)).isTrue();
    }
}
