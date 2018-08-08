package io.homo.efficio.cryptomall.entity.product.service;

import io.homo.efficio.cryptomall.entity.product.Product;
import io.homo.efficio.cryptomall.entity.product.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-08-08
 */
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    private ProductService productService;

    @Before
    public void setup() {
        this.productService = new ProductService(this.productRepository);
    }

    @Test
    public void whenSave__thenReturnProduct() {
        // given
        Product product = new Product("테일러메이드 7W", 88.0d);
        Mockito.when(this.productRepository.save(product))
                .thenReturn(product);

        // when
        Product persistedProduct = this.productService.addNewProduct(product);

        // then
        assertThat(persistedProduct.getPrice()).isEqualTo(88.0d);
    }
}
