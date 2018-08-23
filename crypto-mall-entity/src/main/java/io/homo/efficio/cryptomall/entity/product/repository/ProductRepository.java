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

    // 카테고리와 상품이 다대다 이므로 아래와 같이 카테고리에 속한 상품을 조회하는 기능은 카테고리에서 구현
//    List<Product> findByCategory(Category category);
}
