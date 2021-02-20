package com.expense;

import com.expense.bean.Report;
import com.expense.bean.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Expense {

    public static final int SCALE = 2;

    public static Report generateReport(List<Transaction> allTransactions) {
        BigDecimal zero = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        if (allTransactions == null || allTransactions.isEmpty()) {
            return new Report(zero,zero,zero, Report.MonthlyTopExpensesBuilder.createMonthlyTopExpense(null, zero));
        }
        BigDecimal totalIncome = getTotalIncome(allTransactions);
        BigDecimal totalExpense = getTotalExpense(allTransactions);
        BigDecimal totalSaving = totalIncome.subtract(totalExpense);
        Report.MonthlyTopExpense topExpense = topExpense(allTransactions);

        return new Report(totalIncome, totalExpense, totalSaving, topExpense);
    }

    private static BigDecimal getTotalIncome(List<Transaction> allTransactions) {
        return allTransactions.stream().filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) >= 0).map(Transaction::getAmount).reduce(BigDecimal::add)
                .orElseGet(() -> BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));
    }

    private static BigDecimal getTotalExpense(List<Transaction> allTransactions) {
        return allTransactions.stream().filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) < 0).map(t -> t.getAmount().abs()).reduce(BigDecimal::add)
                .orElseGet(() -> BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));
    }

    private static Report.MonthlyTopExpense topExpense(List<Transaction> allTransactions) {
        Map<Month, List<Transaction>> monthListMap = allTransactions.stream().filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) < 0)
                .collect(Collectors.groupingBy(t -> t.getDate().getMonth()));
        BigDecimal maxExpense = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        Month relatedMonth = null;
        for (Month month : monthListMap.keySet()) {
            BigDecimal totalExpenseOfMonth = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
            for (Transaction transaction : monthListMap.get(month)) {
                totalExpenseOfMonth = totalExpenseOfMonth.add(transaction.getAmount().abs());
            }
            if (totalExpenseOfMonth.compareTo(maxExpense) > 0) {
                maxExpense = totalExpenseOfMonth;
                relatedMonth = month;
            }
        }
        return Report.MonthlyTopExpensesBuilder.createMonthlyTopExpense(relatedMonth, maxExpense);
    }
}
