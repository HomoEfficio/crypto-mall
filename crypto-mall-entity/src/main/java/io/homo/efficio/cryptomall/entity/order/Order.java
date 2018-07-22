package io.homo.efficio.cryptomall.entity.order;

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
        this.orderItems.set(i, newOrderItem);
    }

    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        this.shippingInfo = newShippingInfo;
    }

    public void cancel() {
        if (isCancellable()) {
            this.status = Status.CANCELED;
        }
    }

    public void complete() {

    }

    public boolean isOrderItemChangeable() {
        return false;
    }

    public boolean isShippingInfoChangeable() {
        return false;
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
