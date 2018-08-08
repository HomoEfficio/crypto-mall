package io.homo.efficio.cryptomall.entity.product.service;

import io.homo.efficio.cryptomall.entity.product.Product;
import io.homo.efficio.cryptomall.entity.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-08-08
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addNewProduct(Product product) {
        Product persistedProduct = this.productRepository.save(product);
        return persistedProduct;
    }
}
