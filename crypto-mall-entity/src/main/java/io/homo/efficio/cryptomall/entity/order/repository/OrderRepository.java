package io.homo.efficio.cryptomall.entity.order.repository;

import io.homo.efficio.cryptomall.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-31
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
