package io.homo.efficio.cryptomall.entity.member.repository;

import io.homo.efficio.cryptomall.entity.member.Cart;
import io.homo.efficio.cryptomall.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-29.
 */
public interface CartRepository extends JpaRepository<Cart, Member> {
}
