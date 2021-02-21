package com.expense;

import com.expense.bean.Report;
import com.expense.bean.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Expense {

    public static final int SCALE = 2;

    public static Report generateReport(List<Transaction> allTransactions) {
        BigDecimal zero = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        if (allTransactions == null || allTransactions.isEmpty()) {
            return new Report(zero,zero,zero, Report.MonthlyExpensesBuilder.build(null, zero));
        }
        BigDecimal totalIncome = getTotalIncome(allTransactions);
        BigDecimal totalExpense = getTotalExpense(allTransactions);
        BigDecimal totalSaving = totalIncome.subtract(totalExpense);
        Report.MonthlyExpense topExpense = topExpense(allTransactions);

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

    private static <T extends  Transaction> Report.MonthlyExpense topExpense(List<T> allTransactions) {
        return allTransactions.stream().filter(t -> t.getAmount().compareTo(BigDecimal.ZERO) < 0)
                .collect(Collectors.groupingBy(t -> t.getDate().getMonth(),Collectors.mapping(t -> t.getAmount().abs().doubleValue(), Collectors.summingDouble(Double::doubleValue))))
                .entrySet().stream().map(e -> Report.MonthlyExpensesBuilder.build(e.getKey(), new BigDecimal(e.getValue()).setScale(2,RoundingMode.HALF_EVEN)))
                .max(Comparator.comparing(Report.MonthlyExpense::getExpense)).orElseGet(() -> Report.MonthlyExpensesBuilder.build(null,BigDecimal.ZERO.setScale(2,BigDecimal.ROUND_HALF_EVEN)));
    }
}
