package io.homo.efficio.cryptomall.entity.order.repository;

import io.homo.efficio.cryptomall.entity.member.Member;
import io.homo.efficio.cryptomall.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-31
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderer(Member orderer);

    Optional<Order> findByStatus(Order.Status status);
}
