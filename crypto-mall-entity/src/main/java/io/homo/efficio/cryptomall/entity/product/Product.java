package io.homo.efficio.cryptomall.entity.product;

import lombok.Getter;

import javax.persistence.*;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
@Entity
@Table(name = "PRODUCT")
@Getter
public class Product {

    // TODO: @NonNull 적용

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    private double price;

    private Category category;

    public Product(Long id, String name, double price) {
        this(id, name, price, null);
    }

    public Product(Long id, String name, double price, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

}
