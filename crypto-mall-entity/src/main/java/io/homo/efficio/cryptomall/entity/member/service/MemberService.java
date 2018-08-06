package io.homo.efficio.cryptomall.entity.member.service;

import io.homo.efficio.cryptomall.entity.member.Member;
import io.homo.efficio.cryptomall.entity.member.repository.MemberRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-08-06.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MemberService {

    @NonNull
    private MemberRepository memberRepository;

    public Member join(Member member) {
        final Member persistedMember = this.memberRepository.save(member);
        return persistedMember;
    }
}
