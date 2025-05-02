import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class SearchFilterPanel extends JPanel {
    private final RegularUser  user;
    private final JTextField categoryField;
    private final JTextField amountField;
    private final JTextArea resultArea;

    public SearchFilterPanel(RegularUser  user) {
        this.user = user;
        setLayout(new GridLayout(4, 2, 10, 10));

        // Initialize input fields and result area
        categoryField = new JTextField();
        amountField = new JTextField();
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JButton searchButton = new JButton("Search");
        styleButton(searchButton); // Style the search button

        // Add components to the panel
        add(new JLabel("Category:"));
        add(categoryField);

        add(new JLabel("Amount (₹):"));
        add(amountField);

        add(searchButton);
        add(new JScrollPane(resultArea));

        // Action listener for the search button
        searchButton.addActionListener(e -> searchExpenses());
    }

    private void searchExpenses() {
        String category = categoryField.getText().trim();
        String amountStr = amountField.getText().trim();
        double amount = -1;

        // Validate amount input
        if (!amountStr.isEmpty()) {
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount entered. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Filter expenses based on category and amount
        List<ExpenseEntry> filteredExpenses = new ArrayList<>();
        for (ExpenseEntry entry : user.getExpenses()) {
            boolean matchesCategory = category.isEmpty() || entry.getCategory().equalsIgnoreCase(category);
            boolean matchesAmount = amount < 0 || entry.getAmount() == amount;

            if (matchesCategory && matchesAmount) {
                filteredExpenses.add(entry);
            }
        }

        displayResults(filteredExpenses);
    }

    private void displayResults(List<ExpenseEntry> filteredExpenses) {
        StringBuilder result = new StringBuilder("Filtered Results:\n");

        if (filteredExpenses.isEmpty()) {
            result.append("No expenses found.");
        } else {
            for (ExpenseEntry entry : filteredExpenses) {
                result.append(entry.getDate()).append(" | ")
                        .append(entry.getCategory()).append(" | ₹")
                        .append(entry.getAmount()).append(" | ")
                        .append(entry.getDescription()).append("\n");
            }
        }

        resultArea.setText(result.toString());
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 130, 180)); // Steel Blue
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}