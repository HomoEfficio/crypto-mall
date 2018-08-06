package io.homo.efficio.cryptomall.entity.member.repository;

import io.homo.efficio.cryptomall.entity.member.Cart;
import io.homo.efficio.cryptomall.entity.member.Member;
import io.homo.efficio.cryptomall.entity.order.OrderItem;
import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import io.homo.efficio.cryptomall.entity.order.repository.OrderItemRepository;
import io.homo.efficio.cryptomall.entity.product.Product;
import io.homo.efficio.cryptomall.entity.product.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-31.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CartRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
//    @Transactional  // @Transactional applied on test method does not invoke flush()
    public void whenSaveCart__thenReturnCartWithOwnerAndItems() {
        Member member = new Member.Required("김삼랑", "abcdef@ghi.com", "010-2222-3333", "abcd!@#$")
                .shippingInfo(new ShippingInfo("김삼랑",
                        "02-7777-8888",
                        "서울 광진구 가즈아차산 777", ShippingInfo.Method.TACKBAE))
                .build();
        final Member persistedMember = memberRepository.save(member);
        final Cart cart = new Cart(persistedMember);
        final Cart persistedCart = cartRepository.save(cart);
        final Product product1 = new Product("끝내주는 상품", 17.00d);
        final Product persistedProduct1 = productRepository.save(product1);
        final Product product2 = new Product("잘나가는 상품", 7.00d);
        final Product persistedProduct2 = productRepository.save(product2);

        final OrderItem orderItem1 = new OrderItem(persistedProduct1, 3);
        final OrderItem orderItem2 = new OrderItem(persistedProduct2, 2);
        final OrderItem persistedOrderItem1 = orderItemRepository.save(orderItem1);
        final OrderItem persistedOrderItem2 = orderItemRepository.save(orderItem2);

        // Any one flush invocation flushes all pending changes not only in MemberRepository but also in other Repos.
        memberRepository.flush();

        persistedCart.addItem(persistedOrderItem1);
        persistedCart.addItem(persistedOrderItem2);

        // If this flush is commented out, the cart_items addition above will not be flushed to the DB
        memberRepository.flush();


        assertThat(persistedCart.getItems().size()).isEqualTo(2);
        assertThat(persistedCart.getOwner().getName()).isEqualTo("김삼랑");
        assertThat(persistedCart.getItems().get(0).getProduct().getName())
                .isEqualTo("끝내주는 상품");
        assertThat(persistedCart.getItems().get(0).getQuantity())
                .isEqualTo(3);
        assertThat(persistedCart.getItems().get(1).getAmounts())
                .isEqualTo(14);
    }
}
