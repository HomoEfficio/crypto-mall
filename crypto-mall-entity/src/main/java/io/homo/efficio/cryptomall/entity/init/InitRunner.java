package io.homo.efficio.cryptomall.entity.init;

import io.homo.efficio.cryptomall.entity.product.Product;
import io.homo.efficio.cryptomall.entity.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-08-05.
 */
@Component
public class InitRunner implements CommandLineRunner {

    @Autowired
    private ProductRepository repository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        final Product product = repository.save(
                new Product(
                        "어디다쓰 헬스 장갑", 15.00d
                )
        );

        product.setName("나이스 헬스 장갑");
    }
}
