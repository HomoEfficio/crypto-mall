package io.homo.efficio.cryptomall.entity.member;

import io.homo.efficio.cryptomall.entity.order.OrderItem;
import io.homo.efficio.cryptomall.entity.order.exception.OrderItemNotFoundException;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-26.
 */
@Entity
@Table(name = "CART")
@Getter
public class Cart {

    @Id
    @OneToOne
    private Member owner;

    @ElementCollection
    @CollectionTable(name = "CART_ITEMS",
                     joinColumns = { @JoinColumn(name = "owner") })
    private List<OrderItem> items = new ArrayList<>();

    public Cart(Member owner) {
        this.owner = owner;
    }

    public void addItem(OrderItem orderItem) {
        this.items.add(orderItem);
    }

    public void removeItem(OrderItem orderItem) {
        if (this.items.contains(orderItem)) {
            this.items.remove(orderItem);
        } else {
            throw new OrderItemNotFoundException();
        }
    }

    public void clear() {
        Iterator<OrderItem> iterator = this.items.iterator();
        while (iterator.hasNext()) {
            OrderItem nextItem = iterator.next();
            iterator.remove();
        }
        // 아래의 코드는 java.util.ConcurrentModificationException 을 일으킨다.
//        for (OrderItem orderItem: this.items) {
//            this.items.remove(orderItem);
//        }
    }
}
