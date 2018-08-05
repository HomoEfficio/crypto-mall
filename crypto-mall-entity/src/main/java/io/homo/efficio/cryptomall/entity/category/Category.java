package io.homo.efficio.cryptomall.entity.category;

import io.homo.efficio.cryptomall.entity.common.BaseEntity;
import io.homo.efficio.cryptomall.entity.product.Product;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-22
 */
@Entity
@Table(name = "CATEGORY")
@Getter
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    public void addProduct(@NonNull Product product) {
        if (!this.products.contains(product)) {
            this.products.add(product);
        }
        if (!this.equals(product.getCategory())) {
            product.setCategory(this);
        }
    }

    public Category(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    public Category(@NonNull String name) {
        this.name = name;
    }
}
