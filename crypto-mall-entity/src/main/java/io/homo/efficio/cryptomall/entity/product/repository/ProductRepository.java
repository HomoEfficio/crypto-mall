package io.homo.efficio.cryptomall.entity.product.repository;

import io.homo.efficio.cryptomall.entity.category.Category;
import io.homo.efficio.cryptomall.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-29
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    List<Product> findByCategory(Category category);
}
