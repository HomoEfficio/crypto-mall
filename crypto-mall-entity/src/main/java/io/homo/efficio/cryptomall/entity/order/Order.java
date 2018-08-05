package io.homo.efficio.cryptomall.entity.order;

import io.homo.efficio.cryptomall.entity.common.BaseEntity;
import io.homo.efficio.cryptomall.entity.member.Member;
import io.homo.efficio.cryptomall.entity.order.exception.IllegalCancellationException;
import io.homo.efficio.cryptomall.entity.order.exception.IllegalOrderItemChangeException;
import io.homo.efficio.cryptomall.entity.order.exception.IllegalShippingInfoChangeException;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
@Entity
@Table(name = "ORDERS")
@Getter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orderer_id")
    private Member orderer;

    // TODO: Eager, Lazy Test
    @OneToMany(mappedBy = "order")  // mappedBy = "order"가 없으면 FK가 생기지 않고 Junction Table이 생긴다.
    private List<OrderItem> orderItems;

    @Embedded
    private ShippingInfo shippingInfo;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Order(@NonNull Member orderer,
                 @NonNull List<OrderItem> orderItems,
                 @NonNull ShippingInfo shippingInfo) {
        this.orderer = orderer;
        this.orderItems = orderItems;
        this.shippingInfo = shippingInfo;
        this.status = Status.PAYMENT_WAITING;
    }

    public double calculateAmounts() {
        Objects.requireNonNull(this.orderItems);
        return this.orderItems.stream()
                .map(OrderItem::getAmounts)
                .reduce((a, b) -> a + b)
                .orElse(0.0d);
    }

    public void changeOrderItem(int i, OrderItem newOrderItem) {
        if (isOrderItemChangeable()) {
            this.orderItems.set(i, newOrderItem);
        } else {
            throw new IllegalOrderItemChangeException();
        }
    }

    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        if (isShippingInfoChangeable()) {
            this.shippingInfo = newShippingInfo;
        } else {
            throw new IllegalShippingInfoChangeException();
        }
    }

    public void cancel() {
        if (isCancellable()) {
            this.status = Status.CANCELED;
        } else {
            throw new IllegalCancellationException();
        }
    }

    private boolean isOrderItemChangeable() {
        return this.status == Status.PAYMENT_WAITING;
    }

    private boolean isShippingInfoChangeable() {
        return this.status == Status.PAYMENT_WAITING
                || this.status == Status.PREPARING_SHIPMENT;
    }

    private boolean isCancellable() {
        return this.status == Status.PAYMENT_WAITING
                || this.status == Status.PREPARING_SHIPMENT;
    }

    public void changeStatus(Status newStatus) {
        this.status = newStatus;
    }

    public void addOrderItem(@NonNull OrderItem orderItem) {
        if (this.orderItems.contains(orderItem)) {
            throw new IllegalArgumentException("The same orderItem is already included in the order");
        } else {
            this.orderItems.add(orderItem);
        }
        if (!this.equals(orderItem.getOrder())) {
            orderItem.setOrder(this);
        }
    }

    public enum Status {
        PAYMENT_WAITING, PREPARING_SHIPMENT, SHIPPED, DELIVERING, DELIVERY_COMPLETED, CANCELED
    }
}
