package io.homo.efficio.cryptomall.entity.order.repository;

import io.homo.efficio.cryptomall.entity.member.Member;
import io.homo.efficio.cryptomall.entity.member.MemberRepository;
import io.homo.efficio.cryptomall.entity.order.Order;
import io.homo.efficio.cryptomall.entity.order.OrderItem;
import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import io.homo.efficio.cryptomall.entity.product.Product;
import io.homo.efficio.cryptomall.entity.product.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void jpaTestContextLoads() {}

    @Test
    public void givenUnidirectionalOneToOne__whenAdd3OrderItems__then3MoreInsertsIntoJunctionTableHappen() {

        Member orderer = new Member.Required(
                "아오린", "qwer@zxc.com", "abcd!@#$", "010-1111-3333"
        ).build();
        Member persistedOrderer = memberRepository.save(orderer);
        memberRepository.flush();
        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItem> persistedOrderItems = orderItemRepository.saveAll(orderItems);
        final ShippingInfo shippingInfo =
                new ShippingInfo("지삭렬", "010-8888-9999","인천 서구 크리스탈로 888, 999-3333", ShippingInfo.Method.TACKBAE);
        Order order = new Order(persistedOrderer, persistedOrderItems, shippingInfo);


        final Product persistedProduct01 = productRepository.save(new Product("IOTA T-shirt type A", 20.00d));
        final Product persistedProduct02 = productRepository.save(new Product("IOTA T-shirt type B", 20.00d));
        final Product persistedProduct03 = productRepository.save(new Product("EOS Hood type C", 50.00d));
        productRepository.flush();
        order.addOrderItem(orderItemRepository.save(new OrderItem(persistedProduct01, 3)));
        order.addOrderItem(orderItemRepository.save(new OrderItem(persistedProduct02, 5)));
        order.addOrderItem(orderItemRepository.save(new OrderItem(persistedProduct03, 7)));
        orderItemRepository.flush();
        orderRepository.save(order);
        orderRepository.flush();
    }
}
