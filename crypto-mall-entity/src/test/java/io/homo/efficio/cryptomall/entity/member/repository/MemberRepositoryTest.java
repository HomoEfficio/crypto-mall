package io.homo.efficio.cryptomall.entity.member.repository;

import io.homo.efficio.cryptomall.entity.member.Cart;
import io.homo.efficio.cryptomall.entity.member.Member;
import io.homo.efficio.cryptomall.entity.member.MemberRepository;
import io.homo.efficio.cryptomall.entity.order.OrderItem;
import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import io.homo.efficio.cryptomall.entity.order.repository.OrderItemRepository;
import io.homo.efficio.cryptomall.entity.product.Product;
import io.homo.efficio.cryptomall.entity.product.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-29.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private Member member;

    @Before
    public void setup() {
        member = new Member.Required("김삼랑", "abcdef@ghi.com", "010-2222-3333", "abcd!@#$")
                .shippingInfo(new ShippingInfo("김삼랑",
                        "02-7777-8888",
                        "서울 광진구 가즈아차산 777", ShippingInfo.Method.TACKBAE))
                .build();
    }

    @Test
    public void jpaTestContextLoads() {}

    @Test
    public void whenFindByEmail__thenReturnMember() {

        em.persist(member);
        em.flush();

        Member persistedMember = memberRepository.findByEmail("abcdef@ghi.com");

        assertThat(persistedMember.getName()).isEqualTo("김삼랑");
        assertThat(persistedMember.getShippingInfo().getMethod()).isEqualTo(ShippingInfo.Method.TACKBAE);
    }

    @Test
    public void whenSave__thenReturnMember() {
        final Member persistedMember = memberRepository.save(member);

        final Optional<Member> foundMember = memberRepository.findById(persistedMember.getId());
        assertThat(foundMember.get().getShippingInfo().getAddress())
                .isEqualTo("서울 광진구 가즈아차산 777");
        assertThat(foundMember.get().getEmail())
                .isEqualTo("abcdef@ghi.com");
    }

    @Test(expected = RuntimeException.class)
    public void whenSaveWithDupEmail__thenThrowException() {
        final Member persistedMember1 = memberRepository.save(member);
        final Member persistedMember2 = memberRepository.save(
                new Member.Required("오푼젤", "abcdef@ghi.com", "010-2222-3333", "abcd!@#$")
                        .shippingInfo(new ShippingInfo("뚱띠기",
                                "02-7777-8888",
                                "인천 서구 청르아가즈아 777", ShippingInfo.Method.TACKBAE))
                        .build()
        );
        memberRepository.flush();

//        em.persist(member);
//        em.persist(
//                new Member.Required("오푼젤", "abcdef@ghi.com", "010-2222-3333", "abcd!@#$")
//                        .shippingInfo(new ShippingInfo("뚱띠기",
//                                "02-7777-8888",
//                                "인천 서구 청르아가즈아 777", ShippingInfo.Method.TACKBAE))
//                        .build()
//        );
//        em.flush();
    }

    @Test
    public void whenSaveCart__thenReturnCartWithOwnerAndItems() {
        final Member persistedMember = memberRepository.save(member);
        final Cart cart = new Cart(persistedMember);
        final Cart persistedCart = cartRepository.save(cart);
        final Product product1 = new Product("끝내주는 상품", 17.00d);
        final Product persistedProduct1 = productRepository.save(product1);
        productRepository.flush();
        final OrderItem orderItem1 = new OrderItem(persistedProduct1, 3);
        final OrderItem persistedOrderItem1 = orderItemRepository.save(orderItem1);
        orderItemRepository.flush();
        memberRepository.flush();

        persistedCart.addItem(persistedOrderItem1);
        cartRepository.flush();

        assertThat(persistedCart.getItems().size()).isEqualTo(1);
        assertThat(persistedCart.getOwner().getName()).isEqualTo("김삼랑");
        assertThat(persistedCart.getItems().get(0).getProduct().getName())
                .isEqualTo("끝내주는 상품");
        assertThat(persistedCart.getItems().get(0).getQuantity())
                .isEqualTo(3);
    }
}
