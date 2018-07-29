package io.homo.efficio.cryptomall.entity.product.repository;

import io.homo.efficio.cryptomall.entity.category.Category;
import io.homo.efficio.cryptomall.entity.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-29
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ProductRepository repository;

    @Test
    public void jpaTest() {

    }

    @Test
    public void whenFindByName__thenReturnProduct() {
        Category category = em.persist(
                new Category(
                        "헬스용품"
                )
        );
        em.persist(
                new Product(
                        "라텍스 밴드 중급형", 28.00d,
                        category
                )
        );
        em.flush();

        Product product = repository.findByName("라텍스 밴드 중급형");

        assertThat(product.getName()).isEqualTo("라텍스 밴드 중급형");
        assertThat(product.getCategory().getName()).isEqualTo("헬스용품");
    }

    @Test
    public void whenFindByCategory__thenReturnProductByCategory() {
        Category category = em.persist(
                new Category(
                        "헬스용품"
                )
        );
        em.persist(
                new Product(
                        "라텍스 밴드 중급형", 28.00d,
                        category
                )
        );
        em.persist(
                new Product(
                        "어디다쓰 헬스 장갑", 15.00d,
                        category
                )
        );
        em.flush();

        List<Product> productsByCategory = repository.findByCategory(category);

        assertThat(productsByCategory.size()).isEqualTo(2);
        assertThat(productsByCategory.get(0).getPrice()).isEqualTo(28.00d);
        assertThat(productsByCategory.get(1).getName()).isEqualTo("어디다쓰 헬스 장갑");
    }
}
