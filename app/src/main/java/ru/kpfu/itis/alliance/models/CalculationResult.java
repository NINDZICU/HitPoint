package ru.kpfu.itis.alliance.models;

import java.io.Serializable;

/**
 * Created by Bulat on 05.12.2017 at 19:48.
 */

public class CalculationResult implements Serializable {

    private String title;
    private Integer resultValue;
    private Double totalPrice;

    public CalculationResult(String title, Integer resultValue, Double totalPrice) {
        this.title = title;
        this.resultValue = resultValue;
        this.totalPrice = totalPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getResultValue() {
        return resultValue;
    }

    public void setResultValue(Integer resultValue) {
        this.resultValue = resultValue;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
