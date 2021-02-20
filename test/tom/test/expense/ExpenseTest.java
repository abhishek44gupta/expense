package tom.test.expense;

import com.expense.Expense;
import com.expense.bean.Report;
import com.expense.bean.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseTest {

    private static List<Transaction> collectTransactions(String... transactions) {
        List<Transaction> allTransactions = new ArrayList<>();
        Arrays.stream(transactions).forEach(t -> {
            String[] allFields = t.split(",");
            allTransactions.add(new Transaction(LocalDate.parse(allFields[0], DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    new BigDecimal(allFields[1]).setScale(Expense.SCALE, RoundingMode.HALF_EVEN), allFields[2]));
        });
        return allTransactions;
    }


    @Test
    public void report_NullTransaction() {
        BigDecimal zero = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        Report report = Expense.generateReport(null);
        Report expected = new Report(zero,zero,zero, Report.MonthlyTopExpensesBuilder.createMonthlyTopExpense(null, zero));
        assertEquals(expected, report);
    }

    @Test
    public void report_BlankTransaction() {
        BigDecimal zero = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        Report report = Expense.generateReport(Collections.emptyList());
        Report expected = new Report(zero,zero,zero, Report.MonthlyTopExpensesBuilder.createMonthlyTopExpense(null,zero));
        assertEquals(expected, report);
    }

    @Test
    public void report_Transactions() {
        String[] transactions = new String[]{
                "25-01-2020,-4000,SBI MF",
                "28-01-2020,-1740.6,Stationary",
                "25-02-2020,-2800.3,Grocery",
                "28-02-2020,-3200.5,Swiggy",
                "20-02-2020,1200.8,Royalty",
                "17-03-2020,-200,Stationary",
                "15-03-2020,-2600.60,Grocery",
                "10-03-2020,-1500.99,Grocery",
                "05-03-2020,20000.6,Salary"
        };
        Report report = Expense.generateReport(collectTransactions(transactions));
        Report expected = new Report(
                new BigDecimal("21201.40").setScale(Expense.SCALE, RoundingMode.HALF_EVEN),
                new BigDecimal("16042.99").setScale(Expense.SCALE, RoundingMode.HALF_EVEN),
                new BigDecimal("5158.41").setScale(Expense.SCALE, RoundingMode.HALF_EVEN),
                Report.MonthlyTopExpensesBuilder.createMonthlyTopExpense(Month.FEBRUARY, new BigDecimal("6000.80").setScale(Expense.SCALE, RoundingMode.HALF_EVEN)));
        assertEquals(expected, report);
    }

    @Test
    public void report_SingleTransactionsExpense() {
        String[] transactions = new String[]{
                "25-01-2020,-4000,SBI MF"
        };
        Report report = Expense.generateReport(collectTransactions(transactions));
        Report expected = new Report(
                new BigDecimal("0").setScale(Expense.SCALE, RoundingMode.HALF_EVEN),
                new BigDecimal("4000").setScale(Expense.SCALE, RoundingMode.HALF_EVEN),
                new BigDecimal("-4000").setScale(Expense.SCALE, RoundingMode.HALF_EVEN),
                Report.MonthlyTopExpensesBuilder.createMonthlyTopExpense(Month.JANUARY, new BigDecimal("4000.00").setScale(Expense.SCALE, RoundingMode.HALF_EVEN)));
        assertEquals(expected, report);
    }

    @Test
    public void report_SingleTransactionsIncome() {
        String[] transactions = new String[]{
                "25-01-2020,4000,SBI MF"
        };
        Report report = Expense.generateReport(collectTransactions(transactions));
        Report expected = new Report(
                new BigDecimal("4000").setScale(Expense.SCALE, RoundingMode.HALF_EVEN),
                new BigDecimal("0").setScale(Expense.SCALE, RoundingMode.HALF_EVEN),
                new BigDecimal("4000").setScale(Expense.SCALE, RoundingMode.HALF_EVEN),
                Report.MonthlyTopExpensesBuilder.createMonthlyTopExpense(null, new BigDecimal("0").setScale(Expense.SCALE, RoundingMode.HALF_EVEN)));
        assertEquals(expected, report);
    }

    @Test
    public void report_EqualExpenseAndIncome() {
        String[] transactions = new String[]{
                "01-01-2020,4000,SBI MF",
                "26-01-2020,-4000,SBI MF"
        };
        Report report = Expense.generateReport(collectTransactions(transactions));
        Report expected = new Report(
                new BigDecimal("4000").setScale(Expense.SCALE, RoundingMode.HALF_EVEN),
                new BigDecimal("4000").setScale(Expense.SCALE, RoundingMode.HALF_EVEN),
                new BigDecimal("0").setScale(Expense.SCALE, RoundingMode.HALF_EVEN),
                Report.MonthlyTopExpensesBuilder.createMonthlyTopExpense(Month.JANUARY, new BigDecimal("4000").setScale(Expense.SCALE, RoundingMode.HALF_EVEN)));
        assertEquals(expected, report);
    }
}
