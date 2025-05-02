import javax.swing.*;
import javax.swing.text.BadLocationException;  // Correct import
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddExpensePanel extends JPanel {
    private final RegularUser user;
    private final JTextField amountField;
    private final JTextField customCategoryField;
    private final JTextField descriptionField;
    private final JComboBox<String> predefinedCategories;
    private final JFormattedTextField dateField;

    public AddExpensePanel(RegularUser user) {
        this.user = user;
        setLayout(new GridLayout(8, 2, 10, 10));

        // Predefined categories
        predefinedCategories = new JComboBox<>(new String[] {
                "Food", "Rent", "Transport", "Shopping", "Bills", "Other"
        });
        predefinedCategories.setEditable(false);

        customCategoryField = new JTextField();
        amountField = new JTextField();
        dateField = new JFormattedTextField("yyyy-MM-dd");
        descriptionField = new JTextField();

        // Set character limit for description field
        descriptionField.setDocument(new JTextFieldLimit(100));

        JButton addButton = new JButton("Add Expense");

        add(new JLabel("Predefined Category:"));
        add(predefinedCategories);

        add(new JLabel("Or Custom Category:"));
        add(customCategoryField);

        add(new JLabel("Amount (₹):"));
        add(amountField);

        add(new JLabel("Date (yyyy-MM-dd):"));
        add(dateField);

        add(new JLabel("Description (max 100 characters):"));
        add(descriptionField);

        addButton.addActionListener(e -> addExpense());
        add(new JLabel());
        add(addButton);
    }

    private void addExpense() {
        String category = customCategoryField.getText().isEmpty()
                ? (String) predefinedCategories.getSelectedItem()
                : customCategoryField.getText();

        try {
            double amount = parseAmount(amountField.getText());
            LocalDate date = parseDate(dateField.getText());
            String description = descriptionField.getText().trim();

            ExpenseEntry entry = new ExpenseEntry(category, amount, date, description);
            user.addExpense(entry);

            JOptionPane.showMessageDialog(this, "Expense added successfully!");
            resetFields();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double parseAmount(String amountText) throws NumberFormatException {
        amountText = amountText.trim().replaceAll("[^\\d.]", "");
        double amount = Double.parseDouble(amountText);
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        return amount;
    }

    private LocalDate parseDate(String dateText) throws DateTimeParseException {
        LocalDate date = LocalDate.parse(dateText);
        return date;
    }

    private void resetFields() {
        amountField.setText("");
        customCategoryField.setText("");
        dateField.setText("");
        descriptionField.setText("");
    }

    // Custom Document Filter for limiting the description length
    private class JTextFieldLimit extends javax.swing.text.PlainDocument {
        private final int limit;

        public JTextFieldLimit(int limit) {
            this.limit = limit;
        }

        @Override
        public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException {  // Correct exception handling
            if (str != null && getLength() + str.length() <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }
}
