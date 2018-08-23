package io.homo.efficio.cryptomall.entity.product;

import io.homo.efficio.cryptomall.entity.category.Category;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-28
 */
public class ProductTest {

    @Test
    public void 카테고리_없는_상품생성() {
        Product product = new Product(1L, "테일러메이드 7W", 88.0d);

        assertThat(product.getName()).isEqualTo("테일러메이드 7W");
        assertThat(product.getPrice()).isEqualTo(88.0d);
    }

    @Test
    public void whenProductWith2Category__thenProductWith2Category() {
        Category category1 = new Category(1L, "암호화폐");
        Category category2 = new Category(2L, "DAG");
        Set<Category> categories = new HashSet<>();
        categories.addAll(
                Arrays.asList(
                        category1,
                        category2
                )
        );
        Product product = new Product(1L, "IOTA", 1.1d, categories);

        assertThat(product.getCategories().size()).isEqualTo(2);
        assertThat(product.getCategories()).contains(category1);
        assertThat(product.getCategories()).contains(category2);
    }

    @Test
    public void 카테고리_있는_상품생성() {
        Product product = new Product(1L, "IOTA", 1.1d,
                new HashSet<>(Arrays.asList(new Category(1L, "암호화폐")))
        );

        assertThat(product.getName()).isEqualTo("IOTA");
        assertThat((product.getCategories().toArray(new Category[1]))[0].getName()).isEqualTo("암호화폐");
    }

    @Test(expected = NullPointerException.class)
    public void 카테고리가_null인_상품생성() {
        Product product = new Product(1L, "여름 쿠션 방석", 3.3d, null);
    }

    @Test(expected = NullPointerException.class)
    public void 이름이_null인_상품생성() {
        new Product(1L, null, 2.2d);
    }
}
