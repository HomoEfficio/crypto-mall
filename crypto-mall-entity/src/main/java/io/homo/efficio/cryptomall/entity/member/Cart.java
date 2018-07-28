package io.homo.efficio.cryptomall.entity.member;

import io.homo.efficio.cryptomall.entity.order.OrderItem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
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
