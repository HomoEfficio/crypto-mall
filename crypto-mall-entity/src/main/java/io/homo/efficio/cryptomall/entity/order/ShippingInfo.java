package io.homo.efficio.cryptomall.entity.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
@Embeddable
@Getter
public class ShippingInfo {

    private String receiverName;

    private String receiverPhoneNumber;

    private String address;

    @Enumerated(EnumType.STRING)
    private Method method;

    public enum Method {
        TACKBAE, QUICK_SERVICE
    }

    protected ShippingInfo() {
    }

    public ShippingInfo(String receiverName, String receiverPhoneNumber, String address, Method method) {
        this.receiverName = receiverName;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.address = address;
        this.method = method;
    }
}
