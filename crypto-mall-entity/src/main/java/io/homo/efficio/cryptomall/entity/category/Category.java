package io.homo.efficio.cryptomall.entity.category;

import io.homo.efficio.cryptomall.entity.common.BaseEntity;
import io.homo.efficio.cryptomall.entity.product.Product;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.*;

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

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> childCategories = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "CATEGORY_PRODUCT",
               joinColumns = @JoinColumn(name = "category_id"),
               inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();


    public Category(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    public Category(@NonNull String name) {
        this.name = name;
    }

    public Category(@NonNull String name, @NonNull Category parent) {
        this.name = name;
        this.parent = parent;
    }

    public void addProduct(@NonNull Product product) {
        if (this.products.contains(product)) {
            throw new IllegalArgumentException("The product is already included in this category");
        } else {
            this.products.add(product);
        }
        if (!Objects.requireNonNull(
                product.getCategories(), "Product에 Categories가 null 입니다.")
                .contains(this)) {
            product.addCategory(this);
        }
    }

    public void setParent(@NonNull Category parentCategory) {
        this.parent = parentCategory;
    }

    public void addChildCategory(@NonNull Category category) {
        this.childCategories.add(category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        if (!name.equals(category.name)) return false;
        return products != null ? products.equals(category.products) : category.products == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + (products != null ? products.hashCode() : 0);
        return result;
    }
}
