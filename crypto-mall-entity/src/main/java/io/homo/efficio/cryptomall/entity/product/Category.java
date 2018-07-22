package io.homo.efficio.cryptomall.entity.product;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-22
 */
@Getter
public class Category {

    private Long id;

    private String name;

    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {

    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
