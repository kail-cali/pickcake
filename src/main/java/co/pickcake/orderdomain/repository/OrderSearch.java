package co.pickcake.orderdomain.repository;

import co.pickcake.orderdomain.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;


}
