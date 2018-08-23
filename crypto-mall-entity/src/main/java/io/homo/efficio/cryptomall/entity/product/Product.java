package io.homo.efficio.cryptomall.entity.product;

import io.homo.efficio.cryptomall.entity.category.Category;
import io.homo.efficio.cryptomall.entity.common.BaseEntity;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
@Entity
@Table(name = "PRODUCT")
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private double price;

    @ManyToMany(mappedBy = "products")
    private Set<Category> categories = new HashSet<>();

    public Product(Long id,
                   @NonNull String name,
                   double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(@NonNull String name,
                   double price) {
        this.name = name;
        this.price = price;
    }

    public Product(Long id,
                   @NonNull String name,
                   double price,
                   @NonNull Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categories = categories;
    }

    public Product(@NonNull String name,
                   double price,
                   @NonNull Set<Category> categories) {
        this.name = name;
        this.price = price;
        this.categories = categories;
    }

    public void changeName(@NonNull String name) {
        this.name = name;
    }

    public void setCategories(@NonNull Set<Category> categories) {
        if (this.categories.isEmpty()) {
            this.categories.addAll(categories);
        } else {
            this.categories = categories;
        }
    }

    public void addCategory(@NonNull Category category) {
        this.categories.add(category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (Double.compare(product.price, price) != 0) return false;
        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
