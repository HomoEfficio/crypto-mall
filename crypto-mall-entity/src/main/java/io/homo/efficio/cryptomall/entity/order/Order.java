package io.homo.efficio.cryptomall.entity.order;

import java.util.ArrayList;
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

    }

    public void complete() {

    }

    public boolean isOrderItemChangeable() {
        return false;
    }

    public boolean isShippingInfoChangeable() {
        return false;
    }

    public boolean isCancellable() {
        return false;
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

    public enum Status {
        PAYMENT_WAITING, PREPARING_SHIPMENT, DELIVERRING, DELIVERY_COMPLETED, CANCELED
    }
}
