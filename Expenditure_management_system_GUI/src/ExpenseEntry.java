import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExpenseEntry implements Comparable<ExpenseEntry> {
    private final String category;
    private final double amount;
    private final LocalDate date;
    private final String description;

    public ExpenseEntry(String category, double amount, LocalDate date, String description) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "] " +
                category + " - ₹" + amount + " (" + description + ")";
    }

    @Override
    public int compareTo(ExpenseEntry other) {
        return this.date.compareTo(other.date);
    }
}
