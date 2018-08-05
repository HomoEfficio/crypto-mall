package io.homo.efficio.cryptomall.entity.init;

import io.homo.efficio.cryptomall.entity.category.Category;
import io.homo.efficio.cryptomall.entity.category.repository.CategoryRepository;
import io.homo.efficio.cryptomall.entity.product.Product;
import io.homo.efficio.cryptomall.entity.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-08-05.
 */
@Component
@Slf4j
public class InitRunner implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
//    @Transactional
    public void run(String... args) throws Exception {
        final Product product = productRepository.save(
                new Product(
                        "어디다쓰 헬스 장갑", 15.00d
                )
        );

        product.setName("나이스 헬스 장갑");

        productRepository.flush();

        log.warn("### product id: {}", product.getId());

        final Category category = categoryRepository.save(
                new Category(
                        "스포츠 용품"
                )
        );

        log.warn("### category id: {}", category.getId());
    }
}
