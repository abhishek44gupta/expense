package com.expense.bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    private LocalDate date;
    private BigDecimal amount;
    private String transactionType;

    public Transaction(LocalDate date, BigDecimal amount, String transactionType) {
        this.date = date;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return amount == that.amount &&
                date.equals(that.date) &&
                transactionType.equals(that.transactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount, transactionType);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}

