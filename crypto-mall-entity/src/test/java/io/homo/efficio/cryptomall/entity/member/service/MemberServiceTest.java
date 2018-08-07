package io.homo.efficio.cryptomall.entity.member.service;

import io.homo.efficio.cryptomall.entity.member.Member;
import io.homo.efficio.cryptomall.entity.member.exception.MemberNotFoundException;
import io.homo.efficio.cryptomall.entity.member.repository.MemberRepository;
import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-08-06.
 */
@RunWith(SpringRunner.class)
public class MemberServiceTest {

    @MockBean
    private MemberRepository memberRepository;

    private MemberService memberService;


    @Before
    public void setup() {
        this.memberService = new MemberService(this.memberRepository);
    }


    @Test
    public void 회원가입성공() {
        // given
        Member member = new Member.Required("김삼랑", "zcxv@qwer.com", "010-2222-3333", "abcd!@#$")
                .id(1L)
                .shippingInfo(new ShippingInfo("김삼랑",
                        "02-7777-8888",
                        "서울 광진구 가즈아차산 777", ShippingInfo.Method.TACKBAE))
                .build();
        Mockito.when(this.memberRepository.save(member))
                .thenReturn(member);
        Mockito.when(this.memberRepository.findById(member.getId()))
                .thenReturn(Optional.of(member));

        // when
        Member persistedMember = this.memberService.join(member);

        // then
        assertThat(this.memberRepository.findById(persistedMember.getId()).orElseThrow(() -> new MemberNotFoundException()).getEmail())
                .isEqualTo(persistedMember.getEmail());
    }

    @Test
    public void whenNameChanged__thenNameIsChanged() {
        // given
        Member member = new Member.Required("김삼랑", "zcxv@qwer.com", "010-2222-3333", "abcd!@#$")
                .id(1L)
                .shippingInfo(new ShippingInfo("김삼랑",
                        "02-7777-8888",
                        "서울 광진구 가즈아차산 777", ShippingInfo.Method.TACKBAE))
                .build();
        Mockito.when(this.memberRepository.save(member))
                .thenReturn(member);
        Member persistedMember = this.memberService.join(member);
        Mockito.when(this.memberRepository.save(persistedMember))
                .thenReturn(persistedMember);
        Mockito.when(this.memberRepository.findByEmail("zcxv@qwer.com"))
                .thenReturn(persistedMember);

        // when
        persistedMember.changeName("김오랑");
        this.memberRepository.saveAndFlush(persistedMember);

        // then
        assertThat(this.memberRepository.findByEmail("zcxv@qwer.com").getName())
                .isEqualTo("김오랑");
    }
}
