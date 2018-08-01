package io.homo.efficio.cryptomall.entity.order.repository;

import io.homo.efficio.cryptomall.entity.member.Member;
import io.homo.efficio.cryptomall.entity.member.MemberRepository;
import io.homo.efficio.cryptomall.entity.order.Order;
import io.homo.efficio.cryptomall.entity.order.OrderItem;
import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import io.homo.efficio.cryptomall.entity.order.exception.OrderNotFoundException;
import io.homo.efficio.cryptomall.entity.product.Product;
import io.homo.efficio.cryptomall.entity.product.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-31
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;


    private Member persistedOrderer;

    private List<OrderItem> persistedOrderItems;

    private Order persistedOrder;

    private Product persistedProduct01;
    private Product persistedProduct02;
    private Product persistedProduct03;

    @Before
    public void setup() {
        Member orderer = new Member.Required(
                "아오린", "qwer@zxc.com", "abcd!@#$", "010-1111-3333"
        ).build();
        persistedOrderer = memberRepository.save(orderer);
        memberRepository.flush();
        List<OrderItem> orderItems = new ArrayList<>();
        persistedOrderItems = orderItemRepository.saveAll(orderItems);
        final ShippingInfo shippingInfo =
                new ShippingInfo("지삭렬", "010-8888-9999","인천 서구 크리스탈로 888, 999-3333", ShippingInfo.Method.TACKBAE);
        Order order = new Order(persistedOrderer, persistedOrderItems, shippingInfo);
        persistedOrder = orderRepository.save(order);

        persistedProduct01 = productRepository.save(new Product("IOTA T-shirt type A", 20.00d));
        persistedProduct02 = productRepository.save(new Product("IOTA T-shirt type B", 20.00d));
        persistedProduct03 = productRepository.save(new Product("EOS Hood type C", 50.00d));

        productRepository.flush();
        orderRepository.flush();
        orderItemRepository.flush();
    }

    @Test
    public void jpaTestContextLoads() {}

    @Test
    public void givenUnidirectionalOneToOne__whenAdd3OrderItems__then3MoreInsertsIntoJunctionTableHappen() {

        add3OrderItemsToOrder();

        // No assertion, execute only with @OneToMany applied on orderItems of Order and just see the log to verify,
    }

    @Test
    public void givenBidirectionalOneToManyManyToOne__whenAdd3OrderItems__thenNoJunctionTableCreated() {

        add3OrderItemsToOrder();

        // No assertion, execute with @OneToMany applied on orderItems of Order and @ManyToOne applied on order of OrderItem
        // and just see the log to verify
    }

    private void add3OrderItemsToOrder() {
        
        persistedOrder.addOrderItem(orderItemRepository.save(new OrderItem(persistedProduct01, 3, persistedOrder)));
        persistedOrder.addOrderItem(orderItemRepository.save(new OrderItem(persistedProduct02, 5, persistedOrder)));
        persistedOrder.addOrderItem(orderItemRepository.save(new OrderItem(persistedProduct03, 7, persistedOrder)));


        assertThat(orderRepository.findById(persistedOrder.getId())
                        .orElseThrow(() -> new OrderNotFoundException())
                        .getShippingInfo().getMethod())
                .isEqualTo(ShippingInfo.Method.TACKBAE);
        assertThat(orderRepository.findById(persistedOrder.getId())
                        .orElseThrow(() -> new OrderNotFoundException())
                        .getStatus())
                .isEqualTo(Order.Status.PAYMENT_WAITING);
        assertThat(orderRepository.findById(persistedOrder.getId())
                        .orElseThrow(() -> new OrderNotFoundException())
                        .getOrderItems().size())
                .isEqualTo(3);
        assertThat(orderRepository.findById(persistedOrder.getId())
                        .orElseThrow(() -> new OrderNotFoundException())
                        .getOrderItems().get(1).getProduct().getName())
                .isEqualTo("IOTA T-shirt type B");
        assertThat(orderRepository.findById(persistedOrder.getId())
                        .orElseThrow(() -> new OrderNotFoundException())
                        .getOrderItems().get(2).getAmounts())
                .isEqualTo(350d);
    }

    @Test
    public void whenFindByMember__thenReturnOrderWithThatMember() {

        Optional<Order> foundOrder = orderRepository.findByOrderer(persistedOrderer);

        assertThat(foundOrder.orElseThrow(() -> new OrderNotFoundException())
                        .getOrderer().getName())
                .isEqualTo(persistedOrderer.getName());
    }
}
