package io.homo.efficio.cryptomall.entity.member.service;

import io.homo.efficio.cryptomall.entity.member.Member;
import io.homo.efficio.cryptomall.entity.member.repository.MemberRepository;
import io.homo.efficio.cryptomall.entity.member.exception.MemberNotFoundException;
import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-08-06.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원가입성공() {
        // given
        Member member = new Member.Required("김삼랑", "zcxv@qwer.com", "abcd!@#$", "010-2222-3333")
                .id(1L)
                .shippingInfo(new ShippingInfo("김삼랑",
                        "02-7777-8888",
                        "서울 광진구 가즈아차산 777", ShippingInfo.Method.TACKBAE))
                .build();

        // when
        Member persistedMember = this.memberService.join(member);

        // then
        assertThat(this.memberRepository.findById(persistedMember.getId()).orElseThrow(() -> new MemberNotFoundException()).getEmail())
                .isEqualTo(persistedMember.getEmail());
    }
}
