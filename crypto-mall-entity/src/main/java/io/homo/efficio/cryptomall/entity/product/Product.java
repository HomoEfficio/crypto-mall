package io.homo.efficio.cryptomall.entity.product;

import io.homo.efficio.cryptomall.entity.category.Category;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
@Entity
@Table(name = "PRODUCT")
@Getter
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(Long id,
                   @NonNull String name,
                   double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(Long id,
                   @NonNull String name,
                   double price,
                   @NonNull Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

}
