import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportPanel extends JPanel {
    private JTable reportTable;
    private JButton exportButton;
    private DefaultTableModel tableModel;

    public ReportPanel(List<ExpenseEntry> expenseEntries) {
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Date", "Category", "Amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reportTable = new JTable(tableModel);

        for (ExpenseEntry entry : expenseEntries) {
            tableModel.addRow(new Object[]{entry.getDate(), entry.getCategory(), entry.getAmount()});
        }

        JScrollPane scrollPane = new JScrollPane(reportTable);
        add(scrollPane, BorderLayout.CENTER);

        exportButton = new JButton("Export Report");
        exportButton.addActionListener(e -> exportReport(expenseEntries));
        add(exportButton, BorderLayout.SOUTH);
    }

    private void exportReport(List<ExpenseEntry> expenseEntries) {
        String fileName = JOptionPane.showInputDialog(this, "Enter file name (without extension):");
        if (fileName != null && !fileName.isEmpty()) {
            try (FileWriter writer = new FileWriter(fileName + ".csv")) {
                writer.write("Date,Category,Amount\n"); // CSV header
                for (ExpenseEntry entry : expenseEntries) {
                    // Handle commas in entries by quoting the values
                    writer.write("\"" + entry.getDate() + "\",\"" + entry.getCategory() + "\",\"" + entry.getAmount() + "\"\n");
                }
                JOptionPane.showMessageDialog(this, "Report exported successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error exporting report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid file name entered.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
