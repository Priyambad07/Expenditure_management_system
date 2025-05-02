import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ExpenditureStats {
    private final List<ExpenseEntry> expenses;

    public ExpenditureStats(List<ExpenseEntry> expenses) {
        this.expenses = expenses;
    }

    public double getTotal() {
        return expenses.stream().mapToDouble(ExpenseEntry::getAmount).sum();
    }

    public double getAverage() {
        return expenses.isEmpty() ? 0 : getTotal() / expenses.size();
    }

    public String getMaxCategory() {
        Map<String, Double> categoryTotals = new HashMap<>();

        for (ExpenseEntry e : expenses) {
            categoryTotals.put(e.getCategory(),
                    categoryTotals.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
        }

        return categoryTotals.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
    }
}
