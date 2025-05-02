import java.util.*;

public class SplitExpenseManager {
    private final Map<String, Double> balances;
    private final List<String> splitHistory;

    public SplitExpenseManager() {
        balances = new HashMap<>();
        splitHistory = new ArrayList<>();
    }

    public void splitExpense(String title, double totalAmount, List<String> people) {
        double amountPerPerson = totalAmount / people.size();

        for (String person : people) {
            balances.put(person, balances.getOrDefault(person, 0.0) + amountPerPerson);
        }

        splitHistory.add("🔹 " + title + " - ₹" + totalAmount + " split among " + people.size());
    }

    public void settleBalance(String person, double amountPaid) {
        balances.put(person, balances.getOrDefault(person, 0.0) - amountPaid);
    }

    public Map<String, Double> getBalances() {
        return balances;
    }

    public List<String> getSplitHistory() {
        return splitHistory;
    }
}
