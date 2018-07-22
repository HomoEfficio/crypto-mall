package io.homo.efficio.cryptomall.entity.order;

import io.homo.efficio.cryptomall.entity.product.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
public class OrderTest {

    private Order order = null;

    @Before
    public void before() {
        final Product product01 = new Product(1L, "IOTA T-shirt type A", 20.00d);
        final Product product02 = new Product(2L, "IOTA T-shirt type B", 20.00d);
        final Product product03 = new Product(3L, "EOS Hood type C", 50.00d);

        final List<OrderItem> orderItems = Arrays.asList(
                new OrderItem(product01, 3),
                new OrderItem(product02, 5),
                new OrderItem(product03, 7)
        );

        final ShippingInfo shippingInfo = new ShippingInfo("인천 서구 크리스탈로 888, 999-3333", ShippingInfo.Method.TACKBAE);

        this.order = new Order(orderItems, shippingInfo);
    }

    @Test
    public void 주문가격계산() {

        assertThat(this.order.calculateAmounts()).isEqualTo(510d);
    }

    @Test
    public void 주문항목변경_주문수량변경() {

        final Product product02 = new Product(2L, "IOTA T-shirt type B", 20.00d);

        this.order.changeOrderItem(1, new OrderItem(product02, 6));

        assertThat(order.calculateAmounts()).isEqualTo(530d);
    }

    @Test
    public void 배송정보변경() {
        this.order.changeShippingInfo(new ShippingInfo("서울 광진구 영화사로 77길 11, 222-333", ShippingInfo.Method.QUICK_SERVICE));

        assertThat(this.order.getShippingInfo().getAddress()).isEqualTo("서울 광진구 영화사로 77길 11, 222-333");
        assertThat(this.order.getShippingInfo().getMethod()).isEqualTo(ShippingInfo.Method.QUICK_SERVICE);
    }

    @Test
    public void 주문취소() {
        this.order.cancel();

        assertThat(this.order.getStatus()).isEqualTo(Order.Status.CANCELED);
    }
}
