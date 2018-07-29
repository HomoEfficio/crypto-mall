package io.homo.efficio.cryptomall.entity.member;

import io.homo.efficio.cryptomall.entity.member.exception.IllegalMemberInfoChangeException;
import io.homo.efficio.cryptomall.entity.order.Order;
import io.homo.efficio.cryptomall.entity.order.OrderItem;
import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import io.homo.efficio.cryptomall.entity.product.Product;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-22
 */
public class MemberTest {

    @Test
    public void 멤버생성_with_배송정보() {
        Member member = new Member.Required("김삼랑", "abcd!@#$", "010-2222-3333")
                .id(1L)
                .shippingInfo(new ShippingInfo("김삼랑",
                        "02-7777-8888",
                        "서울 광진구 가즈아차산 777", ShippingInfo.Method.TACKBAE))
                .build();

        assertThat(member.getName()).isEqualTo("김삼랑");
        assertThat(member.getStatus()).isEqualTo(Member.Status.ACTIVE);
        assertThat(member.getGrade()).isEqualTo(Member.Grade.NORMAL);
    }

    @Test
    public void 멤버생성_with_배송정보_비활성_우량() {
        Member member = new Member.Required("김삼랑", "abcd!@#$", "010-2222-3333")
                .id(1L)
                .shippingInfo(new ShippingInfo("김삼랑",
                        "02-7777-8888",
                        "서울 광진구 가즈아차산 777", ShippingInfo.Method.TACKBAE))
                .status(Member.Status.INACTIVE)
                .grade(Member.Grade.PREMIUM)
                .build();

        assertThat(member.getName()).isEqualTo("김삼랑");
        assertThat(member.getStatus()).isEqualTo(Member.Status.INACTIVE);
        assertThat(member.getGrade()).isEqualTo(Member.Grade.PREMIUM);
    }

    @Test
    public void 멤버_이름변경() {
        Member member = new Member
                .Required("김삼랑", "abcd!@#$", "010-2222-3333")
                .id(1L)
                .build();

        member.changeName("이나일");

        assertThat(member.getName()).isEqualTo("이나일");
    }

    @Test
    public void 멤버_배송정보변경() {
        Member member = new Member
                .Required("김삼랑", "abcd!@#$", "010-2222-3333")
                .id(1L)
                .shippingInfo(
                        new ShippingInfo(
                                "아삼유",
                                "010-2222-3333",
                                "서울특별시 특별구 건강동",
                                ShippingInfo.Method.TACKBAE
                        )
                )
                .build();

        member.changeShippingInfo(
                new ShippingInfo(
                        "아삼유",
                        "010-2222-3333",
                        "서울특별시 특별구 건강동",
                        ShippingInfo.Method.QUICK_SERVICE
                )
        );

        assertThat(member.getShippingInfo().getMethod()).isEqualTo(ShippingInfo.Method.QUICK_SERVICE);
    }

    @Test
    public void 멤버_전화번호변경() {
        Member member = new Member
                .Required("김삼랑", "abcd!@#$", "010-2222-3333")
                .id(1L)
                .build();

        member.changePhoneNumber("010-3333-4444");

        assertThat(member.getPhoneNumber()).isEqualTo("010-3333-4444");
    }

    @Test
    public void 비활성멤버_상태변경() {
        Member member = new Member
                .Required("김삼랑", "abcd!@#$", "010-2222-3333")
                .id(1L)
                .status(Member.Status.INACTIVE)
                .build();

        member.changeStatus(Member.Status.ACTIVE);

        assertThat(member.getStatus()).isEqualTo(Member.Status.ACTIVE);
    }

    @Test
    public void 멤버_등급변경() {
        Member member = new Member
                .Required("김삼랑", "abcd!@#$", "010-2222-3333")
                .id(1L)
                .grade(Member.Grade.NORMAL)
                .build();

        member.changeGrade(Member.Grade.PLATINUM);

        assertThat(member.getGrade()).isEqualTo(Member.Grade.PLATINUM);
    }

    @Test(expected = IllegalMemberInfoChangeException.class)
    public void 비활성멤버_등급변경() {
        Member member = new Member
                .Required("김삼랑", "abcd!@#$", "010-2222-3333")
                .id(1L)
                .status(Member.Status.INACTIVE)
                .grade(Member.Grade.NORMAL)
                .build();

        member.changeGrade(Member.Grade.PLATINUM);

        assertThat(member.getGrade()).isEqualTo(Member.Grade.PLATINUM);
    }

    @Test(expected = IllegalMemberInfoChangeException.class)
    public void 비활성멤버_배송정보변경() {
        Member member = new Member
                .Required("김삼랑", "abcd!@#$", "010-2222-3333")
                .id(1L)
                .status(Member.Status.INACTIVE)
                .shippingInfo(
                        new ShippingInfo(
                                "오하칠",
                                "010-5555-1111",
                                "인천 남구 만복동 777",
                                ShippingInfo.Method.TACKBAE
                        )
                )
                .build();

        member.changeShippingInfo(
                new ShippingInfo(
                        "오하칠",
                        "010-5555-1111",
                        "인천 남구 만복동 888",
                        ShippingInfo.Method.TACKBAE
                )
        );

        assertThat(member.getShippingInfo().getAddress()).isEqualTo("인천 남구 만복동 888");
    }

    @Test(expected = IllegalMemberInfoChangeException.class)
    public void 배송정보이름이회원이름과_다른경우_배송정보로_회원정보변경시_예외() {
        Member member = new Member
                .Required("김삼랑", "abcd!@#$", "010-2222-3333")
                .id(1L)
                .status(Member.Status.INACTIVE)
                .shippingInfo(
                        new ShippingInfo(
                                "오하칠",
                                "010-5555-1111",
                                "인천 남구 만복동 777",
                                ShippingInfo.Method.TACKBAE
                        )
                )
                .build();

        member.replaceMemberInfoBy(
                new ShippingInfo(
                        "오하칠",
                        "010-3333-3333",
                        "인천 남구 만복동 888",
                        ShippingInfo.Method.TACKBAE
                )
        );

        assertThat(member.getPhoneNumber()).isEqualTo("010-3333-3333");
    }

    @Test
    public void 배송정보이름이회원이름과_같을경우_배송정보로_회원정보변경() {
        Member member = new Member
                .Required("김삼랑", "abcd!@#$", "010-2222-3333")
                .id(1L)
                .status(Member.Status.ACTIVE)
                .shippingInfo(
                        new ShippingInfo(
                                "김삼랑",
                                "010-5555-1111",
                                "인천 남구 만복동 777",
                                ShippingInfo.Method.TACKBAE
                        )
                )
                .build();

        member.replaceMemberInfoBy(
                new ShippingInfo(
                        "김삼랑",
                        "010-3333-3333",
                        "인천 남구 만복동 888",
                        ShippingInfo.Method.TACKBAE
                )
        );

        assertThat(member.getPhoneNumber()).isEqualTo("010-3333-3333");
    }

    @Test(expected = IllegalMemberInfoChangeException.class)
    public void 배송정보이름이_비활성회원이름과_같을경우_배송정보로_회원정보변경시_예외() {
        Member member = new Member
                .Required("김삼랑", "abcd!@#$", "010-2222-3333")
                .id(1L)
                .status(Member.Status.INACTIVE)
                .shippingInfo(
                        new ShippingInfo(
                                "김삼랑",
                                "010-5555-1111",
                                "인천 남구 만복동 777",
                                ShippingInfo.Method.TACKBAE
                        )
                )
                .build();

        member.replaceMemberInfoBy(
                new ShippingInfo(
                        "김삼랑",
                        "010-3333-3333",
                        "인천 남구 만복동 888",
                        ShippingInfo.Method.TACKBAE
                )
        );

        assertThat(member.getPhoneNumber()).isEqualTo("010-3333-3333");
    }

    @Test
    public void 장바구니에담긴상품을_주문상품으로변경() {
        Member member = new Member.Required("김삼랑", "abcd!@#$", "010-2222-3333")
                .id(1L)
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

        Order order = new Order(member, new ArrayList<OrderItem>(), member.getShippingInfo());
        member.transferCartItemToOrderItem(order, orderItem1);

        assertThat(order.getOrderItems().size()).isEqualTo(1);
        assertThat(order.getOrderItems().get(0).getProduct().getName()).isEqualTo("끝내주는 상품");
    }

    @Test(expected = NullPointerException.class)
    public void 이름이_null_인_멤버생성_예외() {
        new Member.Required(
                null,
                "1234!@#$",
                "010-8888-9999")
                .id(1L)
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void 비밀번호_null_인_멤버생성_예외() {
        new Member.Required(
                "EOS",
                null,
                "010-8888-9999")
                .id(1L)
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void 전화번호_null_인_멤버생성_예외() {
        new Member.Required(
                "EOS",
                "3456#$$",
                null)
                .id(1L)
                .build();
    }
}
