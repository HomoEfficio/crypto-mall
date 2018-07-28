package io.homo.efficio.cryptomall.entity.product;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-22
 */
@Getter
public class Category {

    // TODO: @NonNull 적용

    private Long id;

    private String name;

    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        Objects.requireNonNull(product, "카테고리에 추가할 상품은 반드시 있어야 합니다.");
        this.products.add(product);
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
