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

    @Test
    public void findAlTest() {
        em.persist(new Category("헬스용품"));
        em.persist(new Category("서적"));
        em.persist(new Category("의류"));

        List<Category> all = this.repository.findAll();

        assertThat(all.size()).isEqualTo(3);

    }

    @Test
    public void self_reference_parent_child_test() {
        Category 의류 = new Category("의류");
        Category 아웃도어 = new Category("아웃도어", 의류);
        의류.addChildCategory(아웃도어);
        Category 아동복 = new Category("아동복", 의류);
        의류.addChildCategory(아동복);
        Category 등산모자 = new Category("등산모자", 아웃도어);
        아웃도어.addChildCategory(등산모자);
        Category 보타이 = new Category("보타이", 아동복);
        아동복.addChildCategory(보타이);
        Category 나비넥타이 = new Category("나비넥타이", 아동복);
        아동복.addChildCategory(나비넥타이);

        em.persist(의류);
        em.persist(아웃도어);
        em.persist(아동복);
        em.persist(등산모자);
        em.persist(보타이);
        em.persist(나비넥타이);

        List<Category> all = this.repository.findAll();

        assertThat(all.size()).isEqualTo(6);
    }
}
