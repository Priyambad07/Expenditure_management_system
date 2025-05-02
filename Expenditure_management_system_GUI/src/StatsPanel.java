import javax.swing.*;
import java.awt.*;
import java.time.YearMonth;

public class StatsPanel extends JPanel {
    private final RegularUser user;
    private final JTextArea statsArea;
    private final JPanel chartPanel;

    public StatsPanel(RegularUser user) {
        this.user = user;
        setLayout(new BorderLayout(10, 10));

        statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(statsArea);

        chartPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        updateCharts();

        JButton refreshBtn = new JButton("Refresh Stats");
        refreshBtn.addActionListener(e -> {
            updateStats();
            updateCharts();
        });

        add(scrollPane, BorderLayout.WEST);
        add(chartPanel, BorderLayout.CENTER);
        add(refreshBtn, BorderLayout.SOUTH);

        updateStats();
    }

    private void updateStats() {
        ExpenditureStats stats = new ExpenditureStats(user.getExpenses());
        YearMonth current = YearMonth.now();

        double remaining = user.getRemainingMonthlyBudget(current.getYear(), current.getMonthValue());

        StringBuilder sb = new StringBuilder();
        sb.append("📊 Expense Statistics\n\n");
        sb.append("Total Expenses: ₹").append(String.format("%.2f", stats.getTotal())).append("\n");
        sb.append("Average Expense: ₹").append(String.format("%.2f", stats.getAverage())).append("\n");
        sb.append("Max Category: ").append(stats.getMaxCategory()).append("\n");
        sb.append("Remaining Budget (").append(current.getMonth()).append("): ₹")
                .append(String.format("%.2f", remaining)).append("\n");

        statsArea.setText(sb.toString());
    }

    private void updateCharts() {
        chartPanel.removeAll();
        chartPanel.add(GraphPanel.createBudgetBarChart(user));
        chartPanel.add(GraphPanel.createMonthlyLineChart(user));
        chartPanel.revalidate();
        chartPanel.repaint();
    }
}
