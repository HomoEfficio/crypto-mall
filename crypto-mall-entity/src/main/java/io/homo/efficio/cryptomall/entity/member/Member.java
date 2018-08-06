package io.homo.efficio.cryptomall.entity.member;

import io.homo.efficio.cryptomall.entity.common.BaseEntity;
import io.homo.efficio.cryptomall.entity.member.exception.IllegalMemberInfoChangeException;
import io.homo.efficio.cryptomall.entity.order.Order;
import io.homo.efficio.cryptomall.entity.order.OrderItem;
import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-22
 */
@Entity
@Table(name = "MEMBER",
       indexes = { @Index(name = "IDX_MEMBER_EMAIL", columnList = "email", unique = true) })
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String password;

    @Embedded
    private ShippingInfo shippingInfo;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
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
        private String email;
        private String phoneNumber;
        private String password;

        private ShippingInfo shippingInfo;
        private Status status = Status.ACTIVE;
        private Grade grade = Grade.NORMAL;

        public Required(@NonNull String name,
                        @NonNull String email,
                        @NonNull String phoneNumber,
                        @NonNull String password) {
            this.name = name;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.password = password;
        }

        public Required id(Long id) {
            this.id = id;
            return this;
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
        this.name = required.name;
        this.email = required.email;
        this.phoneNumber = required.phoneNumber;
        this.password = required.password;

        this.id = required.id;
        this.shippingInfo = required.shippingInfo;
        this.status = required.status;
        this.grade = required.grade;
    }

    protected Member() {}

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
