package io.homo.efficio.cryptomall.entity.product;

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
public class Category {

    // TODO: @NonNull 적용

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    public void addProduct(@NonNull Product product) {
        this.products.add(product);
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
