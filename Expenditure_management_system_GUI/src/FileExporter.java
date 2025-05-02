import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;


public class FileExporter {

    public static void exportReport(List<ExpenseEntry> expenses, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Date,Category,Amount (₹),Description\n");

            for (ExpenseEntry entry : expenses) {
                writer.write(entry.getDate() + "," +
                        entry.getCategory() + "," +
                        entry.getAmount() + "," +
                        entry.getDescription() + "\n");
            }

            JOptionPane.showMessageDialog(null, "Report exported successfully to " + fileName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error while exporting report.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
