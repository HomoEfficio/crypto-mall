package io.homo.efficio.cryptomall.entity.product.service;

import io.homo.efficio.cryptomall.entity.product.repository.ProductRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

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
}
