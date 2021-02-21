package tom.test.expense;

import com.expense.Expense;
import com.expense.bean.Report;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.Collections;

import static com.expense.Expense.collectTransactions;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseTest {


    @Test
    public void report_NullTransaction() {
        BigDecimal zero = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        Report report = Expense.generateReport(null);
        Report expected = new Report(zero,zero,zero, Report.MonthlyExpensesBuilder.build(null, zero));
        assertEquals(expected, report);
    }

    @Test
    public void report_BlankTransaction() {
        BigDecimal zero = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        Report report = Expense.generateReport(Collections.emptyList());
        Report expected = new Report(zero,zero,zero, Report.MonthlyExpensesBuilder.build(null,zero));
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
                Report.MonthlyExpensesBuilder.build(Month.FEBRUARY, new BigDecimal("6000.80").setScale(Expense.SCALE, RoundingMode.HALF_EVEN)));
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
                Report.MonthlyExpensesBuilder.build(Month.JANUARY, new BigDecimal("4000.00").setScale(Expense.SCALE, RoundingMode.HALF_EVEN)));
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
                Report.MonthlyExpensesBuilder.build(null, new BigDecimal("0").setScale(Expense.SCALE, RoundingMode.HALF_EVEN)));
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
                Report.MonthlyExpensesBuilder.build(Month.JANUARY, new BigDecimal("4000").setScale(Expense.SCALE, RoundingMode.HALF_EVEN)));
        assertEquals(expected, report);
    }
}
