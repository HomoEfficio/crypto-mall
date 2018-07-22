package io.homo.efficio.cryptomall.entity.product;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-22
 */
public class CategoryTest {

    @Test
    public void 카테고리생성() {
        Category category = new Category(1L, "스포츠");
        Product 아디닥스운동화1 = new Product(1L, "아디닥스 운동화 Type A", 70.0d);

        category.addProduct(아디닥스운동화1);

        assertThat(category.getProducts().size()).isEqualTo(1);
    }
}
