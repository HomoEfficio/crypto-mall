package io.homo.efficio.cryptomall.entity.member;

import io.homo.efficio.cryptomall.entity.order.OrderItem;
import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import io.homo.efficio.cryptomall.entity.product.Product;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-26.
 */
public class CartTest {

    @Test
    public void 아이템_추가() {
        Member member = new Member.Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
                .shippingInfo(new ShippingInfo("김삼랑",
                        "02-7777-8888",
                        "서울 광진구 가즈아차산 777", ShippingInfo.Method.TACKBAE))
                .build();
        final Cart cart = new Cart(member);
        final Product product1 = new Product(1L, "끝내주는 상품", 17.00d);
        final OrderItem orderItem1 = new OrderItem(product1, 3);

        cart.addItem(orderItem1);

        assertThat(cart.getItems().size()).isEqualTo(1);
        assertThat(cart.getItems().get(0).getProduct().getName()).isEqualTo("끝내주는 상품");
    }

    @Test
    public void 아이템_삭제() {
        Member member = new Member.Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
                .shippingInfo(new ShippingInfo("김삼랑",
                        "02-7777-8888",
                        "서울 광진구 가즈아차산 777", ShippingInfo.Method.TACKBAE))
                .build();
        final Cart cart = new Cart(member);
        final OrderItem orderItem1 = new OrderItem(new Product(1L, "끝내주는 상품", 17.00d), 1);
        final OrderItem orderItem2 = new OrderItem(new Product(2L, "믿고사는 상품", 22.00d), 2);
        final OrderItem orderItem3 = new OrderItem(new Product(3L, "호구되는 상품", 27.00d), 3);
        cart.addItem(orderItem1);
        cart.addItem(orderItem2);
        cart.addItem(orderItem3);

        cart.removeItem(orderItem2);

        assertThat(cart.getItems().size()).isEqualTo(2);
        assertThat(cart.getItems().get(1).getProduct().getName()).isEqualTo("호구되는 상품");
        assertThat(cart.getItems().get(0).getQuantity()).isEqualTo(1);
        assertThat(cart.getItems().get(0).getAmounts()).isEqualTo(17);
    }

    @Test
    public void 장바구니_삭제() {
        Member member = new Member.Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
                .shippingInfo(new ShippingInfo("김삼랑",
                        "02-7777-8888",
                        "서울 광진구 가즈아차산 777", ShippingInfo.Method.TACKBAE))
                .build();
        final Cart cart = new Cart(member);
        final OrderItem orderItem1 = new OrderItem(new Product(1L, "끝내주는 상품", 17.00d), 1);
        final OrderItem orderItem2 = new OrderItem(new Product(2L, "믿고사는 상품", 22.00d), 2);
        final OrderItem orderItem3 = new OrderItem(new Product(3L, "호구되는 상품", 27.00d), 3);
        cart.addItem(orderItem1);
        cart.addItem(orderItem2);
        cart.addItem(orderItem3);

        cart.clear();

        assertThat(cart.getItems().size()).isEqualTo(0);
    }
}
