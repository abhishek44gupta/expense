package com.expense.bean;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Objects;

public class Report {
    private BigDecimal totalIncome;
    private BigDecimal expenses;
    private BigDecimal totalSaving;
    private MonthlyTopExpense monthlyTopExpense;

    public static class MonthlyTopExpense {
        private Month month;
        private BigDecimal expense;

        public MonthlyTopExpense(Month month, BigDecimal expense) {
            this.month = month;
            this.expense = expense;
        }

        public Month getMonth() {
            return month;
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
            if (!(o instanceof MonthlyTopExpense)) return false;
            MonthlyTopExpense that = (MonthlyTopExpense) o;
            return month == that.month &&
                    expense.equals(that.expense);
        }

        @Override
        public int hashCode() {
            return Objects.hash(month, expense);
        }
    }

    public static class MonthlyTopExpensesBuilder {
        public static MonthlyTopExpense createMonthlyTopExpense(Month month, BigDecimal topExpense) {
            return new MonthlyTopExpense(month, topExpense);
        }
    }


    public Report(BigDecimal totalIncome, BigDecimal expenses, BigDecimal totalSaving, MonthlyTopExpense monthlyTopExpense) {
        this.totalIncome = totalIncome;
        this.expenses = expenses;
        this.totalSaving = totalSaving;
        this.monthlyTopExpense = monthlyTopExpense;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getExpenses() {
        return expenses;
    }

    public void setExpenses(BigDecimal expenses) {
        this.expenses = expenses;
    }

    public BigDecimal getTotalSaving() {
        return totalSaving;
    }

    public void setTotalSaving(BigDecimal totalSaving) {
        this.totalSaving = totalSaving;
    }

    public MonthlyTopExpense getTopExpenseAndMonth() {
        return monthlyTopExpense;
    }

    public void setTopExpenseAndMonth(MonthlyTopExpense topExpenseAndMonth) {
        this.monthlyTopExpense = topExpenseAndMonth;
    }

    @Override
    public String toString() {
        return "Report{" +
                "totalIncome=" + totalIncome +
                ", expenses=" + expenses +
                ", totalSaving=" + totalSaving +
                ", topExpenseAndMonth='" + monthlyTopExpense + '\'' +
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
                monthlyTopExpense.equals(report.monthlyTopExpense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalIncome, expenses, totalSaving, monthlyTopExpense);
    }
}
