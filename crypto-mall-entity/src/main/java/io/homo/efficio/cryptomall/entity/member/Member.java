package io.homo.efficio.cryptomall.entity.member;

import io.homo.efficio.cryptomall.entity.member.exception.IllegalMemberInfoChangeException;
import io.homo.efficio.cryptomall.entity.order.Order;
import io.homo.efficio.cryptomall.entity.order.OrderItem;
import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-22
 */
@Getter
public class Member {

    private Long id;

    private String name;

    private String password;

    private String phoneNumber;

    private ShippingInfo shippingInfo;

    private Status status;

    private Grade grade;

    public enum Status {
        ACTIVE, INACTIVE, WITHDRAWN
    }

    public enum Grade {
        NORMAL, PREMIUM, PLATINUM, FREQUENT_CANCELLER
    }

    public static class Required {

        private Long id;
        private String name;
        private String phoneNumber;
        private String password;

        private ShippingInfo shippingInfo;
        private Status status = Status.ACTIVE;
        private Grade grade = Grade.NORMAL;

        public Required(Long id,
                        @NonNull String name,
                        @NonNull String password,
                        @NonNull String phoneNumber) {
            this.id = id;
            this.name = name;
            this.password = password;
            this.phoneNumber = phoneNumber;
        }

        public Required shippingInfo(ShippingInfo shippingInfo) {
            this.shippingInfo = shippingInfo;
            return this;
        }

        public Required status(Status status) {
            this.status = status;
            return this;
        }

        public Required grade(Grade grade) {
            this.grade = grade;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }

    private Member(Required required) {
        this.id = required.id;
        this.name = required.name;
        this.password = required.password;
        this.phoneNumber = required.phoneNumber;

        this.shippingInfo = required.shippingInfo;
        this.status = required.status;
        this.grade = required.grade;
    }

    public void changeName(String newName) {
        if (isChangeable()) {
            this.name = newName;
        } else {
            throw new IllegalMemberInfoChangeException();
        }
    }

    public void changePhoneNumber(String newPhoneNumber) {
        if (isChangeable()) {
            this.phoneNumber = newPhoneNumber;
        } else {
            throw new IllegalMemberInfoChangeException();
        }
    }

    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        if (isChangeable()) {
            this.shippingInfo = newShippingInfo;
        } else {
            throw new IllegalMemberInfoChangeException();
        }
    }

    public void changeGrade(Grade newGrade) {
        if (isChangeable()) {
            this.grade = newGrade;
        } else {
            throw new IllegalMemberInfoChangeException();
        }
    }

    public void changeStatus(Status newStatus) {
        this.status = newStatus;
    }

    private boolean isChangeable() {
        return this.status == Status.ACTIVE;
    }

    public void replaceMemberInfoBy(ShippingInfo shippingInfo) {
        if (isReplaceableByShippingInfo(shippingInfo)) {
            this.phoneNumber = shippingInfo.getReceiverPhoneNumber();
        } else {
            throw new IllegalMemberInfoChangeException();
        }
    }

    private boolean isReplaceableByShippingInfo(ShippingInfo shippingInfo) {
        return isChangeable() && this.name.equals(shippingInfo.getReceiverName());
    }

    public void transferCartItemToOrderItem(Order order, OrderItem orderItem) {
        order.addOrderItem(orderItem);
    }

}
