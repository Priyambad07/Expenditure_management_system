import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class GraphPanel {

    public static JPanel createBudgetBarChart(RegularUser user) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, Double> categoryTotals = new HashMap<>();
        double totalSpent = 0;

        for (ExpenseEntry e : user.getExpenses()) {
            categoryTotals.put(
                    e.getCategory(),
                    categoryTotals.getOrDefault(e.getCategory(), 0.0) + e.getAmount()
            );
            totalSpent += e.getAmount();
        }

        // Get current month and year for accurate monthly budget
        if (!user.getExpenses().isEmpty()) {
            ExpenseEntry firstEntry = user.getExpenses().get(user.getExpenses().size() - 1);
            int year = firstEntry.getDate().getYear();
            int month = firstEntry.getDate().getMonthValue();

            double budget = user.getMonthlyBudget(year, month);
            double savings = budget - totalSpent;

            dataset.setValue(budget, "Amount", "Budget");
            dataset.setValue(Math.max(savings, 0), "Amount", "Savings");
        }

        // Add each category as a separate bar
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            dataset.setValue(entry.getValue(), "Amount", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Budget, Savings & Category-wise Expenses",
                "Category",
                "Amount (₹)",
                dataset
        );

        return new ChartPanel(barChart);
    }

    public static JPanel createMonthlyLineChart(RegularUser user) {
        TimeSeries expenseSeries = new TimeSeries("Monthly Expenses");
        TimeSeries savingsSeries = new TimeSeries("Monthly Savings");

        List<ExpenseEntry> expenses = user.getExpenses();
        Map<String, Double> monthlySpent = new TreeMap<>();

        for (ExpenseEntry entry : expenses) {
            String key = entry.getDate().getYear() + "-" + entry.getDate().getMonthValue();
            monthlySpent.put(key,
                    monthlySpent.getOrDefault(key, 0.0) + entry.getAmount());
        }

        for (Map.Entry<String, Double> entry : monthlySpent.entrySet()) {
            String[] parts = entry.getKey().split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);

            double spent = entry.getValue();
            double monthlyBudget = user.getMonthlyBudget(year, month);
            double savings = monthlyBudget - spent;

            expenseSeries.addOrUpdate(new Month(month, year), spent);
            savingsSeries.addOrUpdate(new Month(month, year), Math.max(savings, 0));
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(expenseSeries);
        dataset.addSeries(savingsSeries);

        JFreeChart lineChart = ChartFactory.createTimeSeriesChart(
                "Monthly Expenses vs Savings",
                "Month",
                "Amount (₹)",
                dataset
        );

        return new ChartPanel(lineChart);
    }
}
