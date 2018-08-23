package io.homo.efficio.cryptomall.entity.category;

import io.homo.efficio.cryptomall.entity.product.Product;
import org.junit.Test;

import java.util.Set;

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
        Product product1 = new Product(1L, "아디닥스 운동화 Type A", 70.0d);

        category.addProduct(product1);

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
        Product product = new Product(1L, "뉴발란술 운동화 Type Z", 55.0d);

        //when
        category.addProduct(product);

        //then
        final Set<Product> products = category.getProducts();
        assertThat((products.toArray(new Product[1]))[0])
                .isEqualTo(product);
    }

    @Test
    public void whenRetriveProducts__thenReturnProductsIncludedInThisCategory() {
        //given
        Category category1 = new Category(1L, "스포츠");
        Product product1 = new Product(1L, "뉴발란술 운동화 Type Z", 55.0d);
        Product product2 = new Product(2L, "원더아머 농구화 Z80", 58.0d);
        Category category2 = new Category(2L, "서적");
        Product product3 = new Product(2L, "스프링 마이크로서비스 2e", 33.0d);

        //when
        category1.addProduct(product1);
        category1.addProduct(product2);
        category2.addProduct(product3);

        //then
        final Set<Product> products1 = category1.getProducts();
        assertThat(products1.size()).isEqualTo(2);
        assertThat(products1.contains(product1)).isTrue();
        assertThat(products1.contains(product2)).isTrue();

        final Set<Product> products2 = category2.getProducts();
        assertThat(products2.size()).isEqualTo(1);
        assertThat((products1.toArray(new Product[1]))[0])
                .isEqualTo(product2);
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
