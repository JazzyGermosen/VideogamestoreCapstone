package org.yearup.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {
    private Integer orderId;
    private Integer userId;
    private LocalDate date;
    private String address;
    private String state;
    private String zip;
    private BigDecimal total;

    public Order() {}

    public Order(Integer orderId, Integer userId, LocalDate date, String address, String state, String zip, BigDecimal total) {
        this.orderId = orderId;
        this.userId = userId;
        this.date = date;
        this.address = address;
        this.state = state;
        this.zip = zip;
        this.total = total;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
