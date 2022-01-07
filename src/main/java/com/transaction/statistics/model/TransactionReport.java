package com.transaction.statistics.model;

import java.math.BigDecimal;

public class TransactionReport {

    private String sum;

    private String avg;

    private String max;

    private String min;

    private Long count;

    public TransactionReport(String sum, String avg, String max, String min, Long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TransactionReport{" +
                "sum='" + sum + '\'' +
                ", avg='" + avg + '\'' +
                ", max='" + max + '\'' +
                ", min='" + min + '\'' +
                ", count=" + count +
                '}';
    }
}
