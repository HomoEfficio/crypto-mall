package io.homo.efficio.cryptomall.entity.order.repository;

import io.homo.efficio.cryptomall.entity.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-30.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
