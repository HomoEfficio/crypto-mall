package io.homo.efficio.cryptomall.entity.member;

import io.homo.efficio.cryptomall.entity.member.exception.IllegalMemberInfoChangeException;
import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-22
 */
public class MemberTest {

    @Test
    public void 멤버생성_with_배송정보() {
        Member member = new Member.Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
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
        Member member = new Member.Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
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
                .Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
                .build();

        member.changeName("이나일");

        assertThat(member.getName()).isEqualTo("이나일");
    }

    @Test
    public void 멤버_배송정보변경() {
        Member member = new Member
                .Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
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
                .Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
                .build();

        member.changePhoneNumber("010-3333-4444");

        assertThat(member.getPhoneNumber()).isEqualTo("010-3333-4444");
    }

    @Test
    public void 비활성멤버_상태변경() {
        Member member = new Member
                .Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
                .status(Member.Status.INACTIVE)
                .build();

        member.changeStatus(Member.Status.ACTIVE);

        assertThat(member.getStatus()).isEqualTo(Member.Status.ACTIVE);
    }

    @Test
    public void 멤버_등급변경() {
        Member member = new Member
                .Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
                .grade(Member.Grade.NORMAL)
                .build();

        member.changeGrade(Member.Grade.PLATINUM);

        assertThat(member.getGrade()).isEqualTo(Member.Grade.PLATINUM);
    }

    @Test(expected = IllegalMemberInfoChangeException.class)
    public void 비활성멤버_등급변경() {
        Member member = new Member
                .Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
                .status(Member.Status.INACTIVE)
                .grade(Member.Grade.NORMAL)
                .build();

        member.changeGrade(Member.Grade.PLATINUM);

        assertThat(member.getGrade()).isEqualTo(Member.Grade.PLATINUM);
    }

    @Test(expected = IllegalMemberInfoChangeException.class)
    public void 비활성멤버_배송정보변경() {
        Member member = new Member
                .Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
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
                .Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
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
                .Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
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
                .Required(1L, "김삼랑", "abcd!@#$", "010-2222-3333")
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
}
