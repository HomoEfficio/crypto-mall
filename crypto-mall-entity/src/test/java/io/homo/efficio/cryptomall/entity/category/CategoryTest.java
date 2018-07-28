package io.homo.efficio.cryptomall.entity.category;

import io.homo.efficio.cryptomall.entity.category.Category;
import io.homo.efficio.cryptomall.entity.product.Product;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

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

    @Test(expected = NullPointerException.class)
    public void 카테고리에_null상품추가_throws_NullPointerException() {
        Category category = new Category(1L, "스포츠");

        category.addProduct(null);

        assertThat(category.getProducts().size()).isEqualTo(0);
    }
}
