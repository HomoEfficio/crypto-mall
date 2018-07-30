package io.homo.efficio.cryptomall.entity.order;

import io.homo.efficio.cryptomall.entity.product.Product;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
@Entity
@Table(name = "ORDER_ITEM")
@Getter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private double amounts;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.amounts = this.product.getPrice() * this.quantity;
    }
}
