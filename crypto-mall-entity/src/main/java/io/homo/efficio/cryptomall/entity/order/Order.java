package io.homo.efficio.cryptomall.entity.order;

import io.homo.efficio.cryptomall.entity.order.exception.UnavailableCancellationException;
import io.homo.efficio.cryptomall.entity.order.exception.UnavailableOrderItemChangeException;
import io.homo.efficio.cryptomall.entity.order.exception.UnavailableShippingInfoException;

import java.util.List;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
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

    public void complete() {

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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public Status getStatus() {
        return status;
    }

    public void changeStatus(Status newStatus) {
        this.status = newStatus;
    }

    public enum Status {
        PAYMENT_WAITING, PREPARING_SHIPMENT, SHIPPED, DELIVERING, DELIVERY_COMPLETED, CANCELED
    }
}
