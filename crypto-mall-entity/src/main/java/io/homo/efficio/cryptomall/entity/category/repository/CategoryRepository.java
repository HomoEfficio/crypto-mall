package io.homo.efficio.cryptomall.entity.category.repository;

import io.homo.efficio.cryptomall.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-29
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
