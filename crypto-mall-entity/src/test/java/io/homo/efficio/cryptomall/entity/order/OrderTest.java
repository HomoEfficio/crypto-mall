package io.homo.efficio.cryptomall.entity.order;

import io.homo.efficio.cryptomall.entity.product.Product;
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

    @Test
    public void 주문가격계산() {
        final Product product01 = new Product(1L, "IOTA T-shirt type A", 20.00d);
        final Product product02 = new Product(2L, "IOTA T-shirt type B", 20.00d);
        final Product product03 = new Product(3L, "EOS Hood type C", 50.00d);

        final List<OrderItem> orderItems = Arrays.asList(
                new OrderItem(product01, 3),
                new OrderItem(product02, 5),
                new OrderItem(product03, 7)
        );

        final Order order = new Order(orderItems);

        assertThat(order.calculateAmounts()).isEqualTo(510d);
    }

    @Test
    public void 주문항목변경_주문수량변경() {
        final Product product01 = new Product(1L, "IOTA T-shirt type A", 20.00d);
        final Product product02 = new Product(2L, "IOTA T-shirt type B", 20.00d);
        final Product product03 = new Product(3L, "EOS Hood type C", 50.00d);

        final List<OrderItem> orderItems = Arrays.asList(
                new OrderItem(product01, 3),
                new OrderItem(product02, 5),
                new OrderItem(product03, 7)
        );

        final Order order = new Order(orderItems);

        order.changeOrderItem(1, new OrderItem(product02, 6));

        assertThat(order.calculateAmounts()).isEqualTo(530d);
    }
}
