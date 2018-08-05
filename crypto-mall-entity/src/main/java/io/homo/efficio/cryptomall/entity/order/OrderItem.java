package io.homo.efficio.cryptomall.entity.order;

import io.homo.efficio.cryptomall.entity.common.BaseEntity;
import io.homo.efficio.cryptomall.entity.product.Product;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;

/**
 * @author homo.efficio@gmail.com
 * Created on 2018-07-22.
 */
@Entity
@Table(name = "ORDER_ITEM")
@Getter
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private double amounts;

    @ManyToOne
    @JoinColumn(name = "order_id")  // @JoinColumn은 FK가 설정될 컬럼의 이름을 지어줄 뿐이며,
                                      // @JoinColumn이 없더라도 @OneToMany 쪽에서 mappedBy만 지정해주면 FK가 설정된다.
    private Order order;

    public OrderItem(@NonNull Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.amounts = this.product.getPrice() * this.quantity;
    }

    public OrderItem(@NonNull Product product, int quantity, @NonNull Order order) {
        this.product = product;
        this.quantity = quantity;
        this.amounts = this.product.getPrice() * this.quantity;
        this.order = order;
    }

    public void setOrder(@NonNull Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;

        OrderItem orderItem = (OrderItem) o;

        if (quantity != orderItem.quantity) return false;
        if (id != null ? !id.equals(orderItem.id) : orderItem.id != null) return false;
        return product.equals(orderItem.product);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + product.hashCode();
        result = 31 * result + quantity;
        return result;
    }
}
