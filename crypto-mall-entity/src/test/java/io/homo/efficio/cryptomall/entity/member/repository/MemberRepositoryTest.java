package io.homo.efficio.cryptomall.entity.member.repository;

import io.homo.efficio.cryptomall.entity.member.Member;
import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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

//        em.flush();  // No need to explicitly invoke flush, because it will be invoked by find***() below

        Member persistedMember = memberRepository.findByEmail("abcdef@ghi.com");

        assertThat(persistedMember.getName()).isEqualTo("김삼랑");
        assertThat(persistedMember.getShippingInfo().getMethod()).isEqualTo(ShippingInfo.Method.TACKBAE);
    }

    @Test
    public void whenSave__thenReturnMember() {
        final Member persistedMember = memberRepository.save(member);

        // memberRepository.flush();  // No need to explicitly invoke flush, because it will be invoked by find***() below

        final Optional<Member> foundMember = memberRepository.findById(persistedMember.getId());
        assertThat(foundMember.get().getShippingInfo().getAddress())
                .isEqualTo("서울 광진구 가즈아차산 777");
        assertThat(foundMember.get().getEmail())
                .isEqualTo("abcdef@ghi.com");
    }

    @Test(expected = RuntimeException.class)
    @Transactional  // @Transactional in Test method does NOT invoke flush()
    public void whenSaveWithDupEmail__thenThrowException() {
        final Member persistedMember1 = memberRepository.save(member);
        final Member persistedMember2 = memberRepository.save(
                new Member.Required("오푼젤", "abcdef@ghi.com", "010-2222-3333", "abcd!@#$")
                        .shippingInfo(new ShippingInfo("뚱띠기",
                                "02-7777-8888",
                                "인천 서구 청르아가즈아 777", ShippingInfo.Method.TACKBAE))
                        .build()
        );
        // Need to be explicitly invoked because there is nothing left to invoke flush().
        // @Transactional applied on test method does not invoke flush()
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
}
