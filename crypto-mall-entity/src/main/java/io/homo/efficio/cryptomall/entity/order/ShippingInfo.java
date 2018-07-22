package io.homo.efficio.cryptomall.entity.order;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
public class ShippingInfo {

    private String address;

    private Method method;

    public enum Method {
        TACKBAE, QUICK_SERVICE
    }

    public ShippingInfo(String address, Method method) {
        this.address = address;
        this.method = method;
    }

    public String getAddress() {
        return address;
    }

    public Method getMethod() {
        return method;
    }
}
