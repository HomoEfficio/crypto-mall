package io.homo.efficio.cryptomall.entity.member.repository;

import io.homo.efficio.cryptomall.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-29.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
}
