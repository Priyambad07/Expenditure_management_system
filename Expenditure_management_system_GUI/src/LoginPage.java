import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
public class LoginPage {
    private final Map<String, String> userDatabase = new HashMap<>();
    public LoginPage() {
        userDatabase.put("testuser", "password123");

        JFrame frame = new JFrame("Expenditure Manager - Login");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        frame.getContentPane().setBackground(new Color(240, 240, 240));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField userField = new JTextField(15);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JPasswordField passField = new JPasswordField(15);

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        styleButton(loginBtn);
        styleButton(registerBtn);

        gbc.gridx = 0; gbc.gridy = 0; frame.add(userLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; frame.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; frame.add(passLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; frame.add(passField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; frame.add(loginBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 2; frame.add(registerBtn, gbc);

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
                JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                RegularUser  user = new RegularUser (username, password);
                new UserDashboard(user); // Direct reference to UserDashboard
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (userDatabase.containsKey(username)) {
                JOptionPane.showMessageDialog(frame, "User  already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                userDatabase.put(username, password);
                JOptionPane.showMessageDialog(frame, "User  registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 130, 180)); // Steel Blue
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}