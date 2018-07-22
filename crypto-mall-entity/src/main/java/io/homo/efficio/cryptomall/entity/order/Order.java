package io.homo.efficio.cryptomall.entity.order;

import io.homo.efficio.cryptomall.entity.order.exception.UnavailableCancellationException;
import io.homo.efficio.cryptomall.entity.order.exception.UnavailableOrderItemChangeException;
import io.homo.efficio.cryptomall.entity.order.exception.UnavailableShippingInfoException;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
@Getter
public class Order {

    private List<OrderItem> orderItems;

    private ShippingInfo shippingInfo;

    private Status status;

    public Order(List<OrderItem> orderItems, ShippingInfo shippingInfo) {
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
            throw new UnavailableOrderItemChangeException();
        }
    }

    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        if (isShippingInfoChangeable()) {
            this.shippingInfo = newShippingInfo;
        } else {
            throw new UnavailableShippingInfoException();
        }
    }

    public void cancel() {
        if (isCancellable()) {
            this.status = Status.CANCELED;
        } else {
            throw new UnavailableCancellationException();
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

    public enum Status {
        PAYMENT_WAITING, PREPARING_SHIPMENT, SHIPPED, DELIVERING, DELIVERY_COMPLETED, CANCELED
    }
}
