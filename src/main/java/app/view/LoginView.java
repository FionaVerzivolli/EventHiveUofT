package app.view;

import app.interface_adapter.login.LoginController;
import app.interface_adapter.login.LoginState;
import app.interface_adapter.login.LoginViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements PropertyChangeListener {
    private static final String VIEW_NAME = "login";

    // Color palette
    private static final Color BG_COLOR = new Color(250, 250, 252);                // Very light warm gray
    private static final Color BUTTON_BG = Color.WHITE;                            // White
    private static final Color BUTTON_BG_HOVER = new Color(2, 36, 56, 30);         // Vibrant blue, very light
    private static final Color BUTTON_BORDER = new Color(2, 36, 56);               // Vibrant blue
    private static final Color BUTTON_TEXT = BUTTON_BORDER;                        // Vibrant blue
    private static final Color TITLE_COLOR = BUTTON_BORDER;                        // Vibrant blue
    private static final Color INPUT_BG = Color.WHITE;
    private static final Color INPUT_BORDER = new Color(220, 225, 235);
    private static final Color INPUT_BORDER_FOCUS = BUTTON_BORDER;
    private static final Color INPUT_LABEL = new Color(90, 100, 120);

    private final LoginViewModel loginViewModel;
    private final JTextField usernameInputField;
    private final JPasswordField passwordInputField;
    private final JButton loginButton;
    private final JButton registerButton;
    private LoginController loginController;

    public LoginView(LoginViewModel loginViewModel, LoginController controller) {
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);
        this.loginController = controller;

        setLayout(new GridBagLayout());
        setBackground(BG_COLOR);

        GridBagConstraints outerGbc = new GridBagConstraints();
        outerGbc.gridx = 0;
        outerGbc.gridy = 0;
        outerGbc.insets = new Insets(20, 0, 20, 0);
        outerGbc.anchor = GridBagConstraints.NORTH;

        // Title label
        JLabel titleLabel = new JLabel("EventHiveUofT");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 28));
        titleLabel.setForeground(TITLE_COLOR);
        add(titleLabel, outerGbc);

        outerGbc.gridy = 1;
        outerGbc.insets = new Insets(0, 0, 0, 0);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(true);
        formPanel.setBackground(BG_COLOR);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(INPUT_BORDER, 1, true),
                new EmptyBorder(32, 32, 32, 32)
        ));
        formPanel.setPreferredSize(new Dimension(400, 260));
        formPanel.setMaximumSize(new Dimension(400, 260));
        formPanel.setMinimumSize(new Dimension(400, 260));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        usernameInputField = createInputField("Enter your username...");
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Username:"), gbc);
        gbc.gridx = 1;
        formPanel.add(usernameInputField, gbc);

        // Password
        passwordInputField = createPasswordField("Enter your password...");
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel("Password:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passwordInputField, gbc);

        // Buttons
        loginButton = createStyledButton("Login", e -> handleLoginAction());
        registerButton = createStyledButton("Register", e -> handleRegisterAction());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, outerGbc);
    }

    private JTextField createInputField(String placeholder) {
        JTextField field = new JTextField(18);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(INPUT_BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setForeground(Color.GRAY);
        field.setBackground(INPUT_BG);
        field.setText(placeholder);
        field.setToolTipText(placeholder);

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(INPUT_BORDER_FOCUS, 2, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(INPUT_BORDER, 1, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        return field;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(18);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(INPUT_BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setForeground(Color.GRAY);
        field.setBackground(INPUT_BG);
        field.setEchoChar((char) 0);
        field.setText(placeholder);
        field.setToolTipText(placeholder);

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    field.setEchoChar('•');
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(INPUT_BORDER_FOCUS, 2, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
            public void focusLost(FocusEvent e) {
                if (String.valueOf(field.getPassword()).isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setEchoChar((char) 0);
                    field.setText(placeholder);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(INPUT_BORDER, 1, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        return field;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Inter", Font.BOLD, 15));
        label.setForeground(INPUT_LABEL);
        return label;
    }

    private JButton createStyledButton(String text, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? BUTTON_BG_HOVER : BUTTON_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.setColor(BUTTON_BORDER);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 18, 18);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 28, 10, 28));
        button.setFont(new Font("Inter", Font.BOLD, 15));
        button.setForeground(BUTTON_TEXT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(actionListener);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.repaint(); }
            public void mouseExited(java.awt.event.MouseEvent evt) { button.repaint(); }
        });
        return button;
    }

    private void handleLoginAction() {
        String username = usernameInputField.getText().trim();
        String password = String.valueOf(passwordInputField.getPassword()).trim();
        loginController.execute(username, password);
    }

    private void handleRegisterAction() {
        loginController.switchToRegisterView();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoginState state = (LoginState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError(), "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setLoginController(LoginController controller) {
        this.loginController = controller;
    }
}