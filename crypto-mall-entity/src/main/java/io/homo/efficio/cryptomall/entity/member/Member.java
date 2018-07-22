package io.homo.efficio.cryptomall.entity.member;

import io.homo.efficio.cryptomall.entity.order.ShippingInfo;
import lombok.Getter;

/**
 * @author homo.efficio@gmail.com
 * created on 2018-07-22
 */
@Getter
public class Member {

    private Long id;

    private String name;

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

        private ShippingInfo shippingInfo;
        private Status status = Status.ACTIVE;
        private Grade grade = Grade.NORMAL;

        public Required(Long id, String name, String phoneNumber) {
            this.id = id;
            this.name = name;
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
        this.phoneNumber = required.phoneNumber;

        this.shippingInfo = required.shippingInfo;
        this.status = required.status;
        this.grade = required.grade;
    }

    public void changeName() {

    }

    public void changePhoneNumber() {

    }

    public void changeShippingInfo() {

    }

    public void changeGrade() {

    }

    public void changeStatus() {

    }
}
