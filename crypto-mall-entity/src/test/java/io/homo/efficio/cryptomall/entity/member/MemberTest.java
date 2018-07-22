package io.homo.efficio.cryptomall.entity.member;

import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-22
 */
public class MemberTest {

    @Test
    public void 멤버생성_with_배송정보() {
        Member member = new Member.Required(1L, "김삼랑", "010-2222-3333")
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
        Member member = new Member.Required(1L, "김삼랑", "010-2222-3333")
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
}
