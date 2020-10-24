package com.galaxytaste.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name="orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne(cascade = {DETACH,MERGE,PERSIST,REFRESH})
    @JoinColumn(name="user_id")
    private User user;
    private Date orderDate;
    private Date shipDate;
    private boolean shippingStatus;
    @OneToMany(cascade= ALL,fetch = FetchType.LAZY,mappedBy="order")
    @JsonIgnore
    private List<OrderDetail> orderDetailList;

    public void addToOrderList(OrderDetail detail){
        if(this.orderDetailList==null){
            this.orderDetailList=new ArrayList<>();
        }
        detail.setOrder(this);
        this.orderDetailList.add(detail);
    }

    public Order() {
    }

    public Order(Long id, User user, Date orderDate, Date shipDate, boolean shippingStatus) {
        this.id = id;
        this.user = user;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
        this.shippingStatus = shippingStatus;
    }

    public Order(User user, Date orderDate, Date shipDate, boolean shippingStatus) {
        this.user = user;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
        this.shippingStatus = shippingStatus;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public boolean isShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(boolean shippingStatus) {
        this.shippingStatus = shippingStatus;
    }
}
