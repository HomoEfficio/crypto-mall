package io.homo.efficio.cryptomall.entity.order;

import io.homo.efficio.cryptomall.entity.product.Product;
import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
@Embeddable
@Getter
public class OrderItem {

    private Product product;

    private int quantity;

    private double amounts;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.amounts = this.product.getPrice() * this.quantity;
    }
}
