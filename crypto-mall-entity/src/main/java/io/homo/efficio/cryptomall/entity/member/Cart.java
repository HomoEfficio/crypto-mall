package io.homo.efficio.cryptomall.entity.member;

import io.homo.efficio.cryptomall.entity.order.OrderItem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-26.
 */
@Getter
public class Cart {

    private Member owner;

    private List<OrderItem> items = new ArrayList<>();

    public Cart(Member owner) {
        this.owner = owner;
    }

    public void addItem(OrderItem orderItem) {
        this.items.add(orderItem);
    }

    public void removeItem(OrderItem orderItem) {
        this.items.remove(orderItem);
    }

    public void transferToOrder() {

    }

    public void clear() {

    }
}
