package io.homo.efficio.cryptomall.entity.order;

import io.homo.efficio.cryptomall.entity.product.Product;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
public class OrderItem {

    private Product product;

    private int quantity;

    private double amounts;

    public OrderItem(Product product, int quantity, double amounts) {
        this.product = product;
        this.quantity = quantity;
        this.amounts = this.product.getPrice() * this.quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAmounts() {
        return amounts;
    }
}
