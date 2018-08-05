package io.homo.efficio.cryptomall.entity.category;

import io.homo.efficio.cryptomall.entity.category.Category;
import io.homo.efficio.cryptomall.entity.product.Product;
import org.junit.Test;

import java.util.List;

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
    public void 이름이_null인_카테고리생성_예외() {
        Category category = new Category(1L, null);
    }

    @Test(expected = NullPointerException.class)
    public void 카테고리에_null상품추가_throws_NullPointerException() {
        Category category = new Category(1L, "스포츠");

        category.addProduct(null);

        assertThat(category.getProducts().size()).isEqualTo(0);
    }

    @Test
    public void whenProductAdded__thenTheCategoryOfTheProductIsSetToBeThisCategory() {
        //given
        Category category = new Category(1L, "스포츠");

        //when
        category.addProduct(
                new Product(1L, "뉴발란술 운동화 Type Z", 55.0d)
        );

        //then
        final List<Product> products = category.getProducts();
        assertThat(products.get(products.size() - 1).getCategory())
                .isEqualTo(category);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenTheSameProductAddedTwice__thenThrowException() {
        //given
        Category category = new Category(1L, "스포츠");

        //when
        category.addProduct(
                new Product(1L, "뉴발란술 운동화 Type Z", 55.0d)
        );
        category.addProduct(
                new Product(1L, "뉴발란술 운동화 Type Z", 55.0d)
        );

        //then
        assertThat(category.getProducts().size()).isEqualTo(1);
    }
}
