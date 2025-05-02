import java.util.*;

public class RegularUser extends User {
    private final List<ExpenseEntry> expenses;
    private final Map<String, Double> monthlyBudgets;

    public RegularUser(String username, String password) {
        super(username, password);
        this.expenses = new ArrayList<>();
        this.monthlyBudgets = new HashMap<>();
    }

    // Set monthly budget (e.g. March 2025 → setMonthlyBudget(2025, 3, 10000))
    public void setMonthlyBudget(int year, int month, double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Budget cannot be negative.");
        }
        String key = year + "-" + month;
        monthlyBudgets.put(key, amount);
    }

    // Get monthly budget for a given month/year
    public double getMonthlyBudget(int year, int month) {
        String key = year + "-" + month;
        return monthlyBudgets.getOrDefault(key, 0.0);
    }

    // Get remaining monthly budget
    public double getRemainingMonthlyBudget(int year, int month) {
        double budget = getMonthlyBudget(year, month);
        double spent = expenses.stream()
                .filter(e -> e.getDate().getYear() == year && e.getDate().getMonthValue() == month)
                .mapToDouble(ExpenseEntry::getAmount)
                .sum();
        return budget - spent;
    }

    // Add expense and track it against the correct month
    public synchronized void addExpense(ExpenseEntry expense) {
        if (expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive.");
        }
        expenses.add(expense);
        // No direct deduction since we calculate budget spent by date
    }

    public List<ExpenseEntry> getExpenses() {
        return Collections.unmodifiableList(expenses);
    }

    public boolean isBudgetExceeded(int year, int month) {
        return getRemainingMonthlyBudget(year, month) < 0;
    }

    @Override
    public void displayInfo() {
        System.out.println("User: " + getUsername());
    }
}
