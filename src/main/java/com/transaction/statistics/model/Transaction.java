package com.transaction.statistics.model;


public class Transaction {

    private String amount;

    private String timestamp;

    public Transaction(String amount, String timestamp) {
        super();
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Transaction() {}

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
