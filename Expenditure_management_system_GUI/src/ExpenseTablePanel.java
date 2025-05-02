import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ExpenseTablePanel extends JPanel {
    private final RegularUser user;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public ExpenseTablePanel(RegularUser user) {
        this.user = user;
        setLayout(new BorderLayout());

        // Table columns
        String[] columns = {"Date", "Category", "Amount (₹)", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        loadExpenses();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadExpenses());
        add(refreshButton, BorderLayout.SOUTH);
    }

    private void loadExpenses() {
        tableModel.setRowCount(0); // Clear existing rows

        // Fetch the list of expenses from RegularUser
        List<ExpenseEntry> expenses = user.getExpenses();

        System.out.println("Expenses List: " + expenses);

        if (expenses == null || expenses.isEmpty()) {
            System.out.println("No expenses found.");
        } else {
            // Iterate through the expenses and add them to the table
            for (ExpenseEntry entry : expenses) {
                System.out.println("Expense Entry: " + entry);
                Object[] row = {
                        entry.getDate().toString(),
                        entry.getCategory(),
                        entry.getAmount(),
                        entry.getDescription()
                };
                tableModel.addRow(row);
            }
        }
    }
}
