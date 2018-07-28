package io.homo.efficio.cryptomall.entity.product;

import org.junit.Test;

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
    public void 카테고리_있는_상품생성() {
        Product product = new Product(1L, "IOTA", 1.1d,
                new Category(1L, "암호화폐"));

        assertThat(product.getName()).isEqualTo("IOTA");
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
