package io.homo.efficio.cryptomall.entity.product;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
public class Product {

    private Long id;

    private String name;

    private double price;

    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
