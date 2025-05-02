import javax.swing.*;
import java.awt.*;

public class SplitExpensePanel extends JPanel {
    private final RegularUser user;
    private final JTextField amountField;
    private final JTextField numPeopleField;
    private final JTextField descriptionField;
    private final JTextArea splitDetailsArea;
    private final JTextField[] nameFields; // Array to hold name fields

    public SplitExpensePanel(RegularUser user) {
        this.user = user;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        // Initialize expense fields
        amountField = new JTextField(15);
        numPeopleField = new JTextField(15);
        descriptionField = new JTextField(15);
        splitDetailsArea = new JTextArea(5, 20);
        splitDetailsArea.setEditable(false);
        splitDetailsArea.setLineWrap(true);
        splitDetailsArea.setWrapStyleWord(true);

        JButton splitButton = new JButton("Split Expense");

        // Add components to the panel with GridBagConstraints
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Total Expense Amount:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Number of People:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        add(numPeopleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        add(descriptionField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Names of People:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        add(new JLabel(""), gbc); // Empty label for layout purposes

        // Initialize name fields based on the number of people
        nameFields = new JTextField[10]; // Assuming a maximum of 10 people for simplicity
        for (int i = 0; i < nameFields.length; i++) {
            gbc.gridx = 1; gbc.gridy = 4 + i; // Adjust row for each name field
            nameFields[i] = new JTextField(15);
            add(nameFields[i], gbc);
        }

        gbc.gridx = 0; gbc.gridy = 14; // Position for the button
        gbc.gridwidth = 2; // Span across two columns
        add(splitButton, gbc);

        gbc.gridx = 0; gbc.gridy = 15; // Position for the result area
        gbc.gridwidth = 2; // Span across two columns
        add(new JScrollPane(splitDetailsArea), gbc);

        // Add action listener for the split button
        splitButton.addActionListener(e -> splitExpense());
    }

    private void splitExpense() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            int numPeople = Integer.parseInt(numPeopleField.getText());
            String description = descriptionField.getText();

            // Validate the number of people
            if (numPeople <= 0 || numPeople > nameFields.length) {
                JOptionPane.showMessageDialog(this, "Number of people must be greater than 0 and less than or equal to " + nameFields.length + ".", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate amount per person
            double amountPerPerson = amount / numPeople;
            StringBuilder details = new StringBuilder("Expense Split Details:\n");

            // Collect and display names with their owed amounts
            for (int i = 0; i < numPeople; i++) {
                String name = nameFields[i].getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a name for Person " + (i + 1) + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                details.append(name).append(" owes: ₹").append(amountPerPerson).append("\n");
            }

            // Show the split details in the text area
            splitDetailsArea.setText(details.toString());
            JOptionPane.showMessageDialog(this, "Expense split successfully!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
