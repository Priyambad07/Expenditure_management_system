import javax.swing.*;
import java.awt.*;
import java.time.YearMonth;

public class UserDashboard extends JFrame {
    private final RegularUser user;
    private JLabel budgetLabel;
    private JComboBox<String> monthSelector;

    public UserDashboard(RegularUser user) {
        this.user = user;
        setTitle("Expenditure Manager - Dashboard (" + user.getUsername() + ")");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(70, 130, 180));

        // Month selector
        String[] months = {
                "01-January", "02-February", "03-March", "04-April", "05-May", "06-June",
                "07-July", "08-August", "09-September", "10-October", "11-November", "12-December"
        };
        monthSelector = new JComboBox<>(months);
        monthSelector.setSelectedIndex(YearMonth.now().getMonthValue() - 1);
        topPanel.add(new JLabel("Month:"));
        topPanel.add(monthSelector);

        // Budget label
        budgetLabel = new JLabel();
        budgetLabel.setFont(new Font("Arial", Font.BOLD, 16));
        budgetLabel.setForeground(Color.WHITE);
        topPanel.add(budgetLabel);

        updateDisplayedBudget();  // Initial update

        // Button to update selected month's budget
        JButton updateBudgetButton = new JButton("Set Budget");
        styleButton(updateBudgetButton);
        updateBudgetButton.addActionListener(e -> updateMonthlyBudget());
        topPanel.add(updateBudgetButton);

        // Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Expense", new AddExpensePanel(user));
        tabbedPane.addTab("All Expenses", new ExpenseTablePanel(user));
        tabbedPane.addTab("Statistics", new StatsPanel(user));
        tabbedPane.addTab("Generate Report", new ReportPanel(user.getExpenses()));
        tabbedPane.addTab("Search & Filter", new SearchFilterPanel(user));
        tabbedPane.addTab("Splitwise", new SplitExpensePanel(user));

        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);

        // Refresh displayed budget when month is changed
        monthSelector.addActionListener(e -> updateDisplayedBudget());
    }

    private void updateMonthlyBudget() {
        String selected = (String) monthSelector.getSelectedItem();
        int month = Integer.parseInt(selected.split("-")[0]);
        int year = YearMonth.now().getYear();

        String input = JOptionPane.showInputDialog(this, "Enter budget for " + selected.split("-")[1] + ":");
        try {
            double budget = Double.parseDouble(input);
            if (budget >= 0) {
                user.setMonthlyBudget(year, month, budget);
                updateDisplayedBudget();
            } else {
                JOptionPane.showMessageDialog(this, "Budget cannot be negative!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDisplayedBudget() {
        int month = monthSelector.getSelectedIndex() + 1;
        int year = YearMonth.now().getYear();
        double budget = user.getMonthlyBudget(year, month);

        String monthName = monthSelector.getSelectedItem().toString().split("-")[1];
        budgetLabel.setText("  Budget for " + monthName + ": ₹" + String.format("%.2f", budget));
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
