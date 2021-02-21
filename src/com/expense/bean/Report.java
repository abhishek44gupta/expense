package com.expense.bean;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Objects;

public class Report {
    private BigDecimal totalIncome;
    private BigDecimal expenses;
    private BigDecimal totalSaving;
    private MonthlyExpense monthlyExpense;

    public static class MonthlyExpense {
        private Month month;
        private BigDecimal expense;

        public MonthlyExpense(Month month, BigDecimal expense) {
            this.month = month;
            this.expense = expense;
        }

        public BigDecimal getExpense() {
            return expense;
        }

        @Override
        public String toString() {
            return expense + "@" + (month == null ? "" : month);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MonthlyExpense)) return false;
            MonthlyExpense that = (MonthlyExpense) o;
            return month == that.month &&
                    expense.equals(that.expense);
        }

        @Override
        public int hashCode() {
            return Objects.hash(month, expense);
        }
    }

    public static class MonthlyExpensesBuilder {
        public static MonthlyExpense build(Month month, BigDecimal topExpense) {
            return new MonthlyExpense(month, topExpense);
        }
    }


    public Report(BigDecimal totalIncome, BigDecimal expenses, BigDecimal totalSaving, MonthlyExpense monthlyExpense) {
        this.totalIncome = totalIncome;
        this.expenses = expenses;
        this.totalSaving = totalSaving;
        this.monthlyExpense = monthlyExpense;
    }

    @Override
    public String toString() {
        return "Report{" +
                "totalIncome=" + totalIncome +
                ", expenses=" + expenses +
                ", totalSaving=" + totalSaving +
                ", topExpenseAndMonth='" + monthlyExpense + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report report = (Report) o;
        return totalIncome.equals(report.totalIncome) &&
                expenses.equals(report.expenses) &&
                totalSaving.equals(report.totalSaving) &&
                monthlyExpense.equals(report.monthlyExpense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalIncome, expenses, totalSaving, monthlyExpense);
    }
}
