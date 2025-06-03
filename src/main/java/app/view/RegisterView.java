package app.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;

import app.interface_adapter.register.RegisterController;
import app.interface_adapter.register.RegisterState;
import app.interface_adapter.register.RegisterViewModel;

public class RegisterView extends JPanel implements PropertyChangeListener {
    private static final String VIEW_NAME = "register";

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

    private static final int PANEL_PADDING = 20;
    private static final int INPUT_FIELD_WIDTH = 400;
    private static final int INPUT_FIELD_HEIGHT = 30;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 40;
    private static final int VERTICAL_SPACING_HIGH = 5;
    private static final int VERTICAL_SPACING_SMALL = 10;
    private static final int VERTICAL_SPACING_MEDIUM = 20;
    private static final int VERTICAL_SPACING_EXTRA_LARGE = 50;

    // Font Sizes
    private static final int TITLE_FONT_SIZE = 28;
    private static final int INPUT_LABEL_FONT_SIZE = 15;
    private static final int BUTTON_FONT_SIZE = 15;

    private final RegisterViewModel registerViewModel;
    private final JTextField usernameInputField;
    private final JTextField emailInputField;
    private final JPasswordField passwordInputField;
    private final JButton signupButton;
    private final JButton loginButton;
    private RegisterController registerController;



    public RegisterView(RegisterViewModel registerViewModel) {
        this.registerViewModel = registerViewModel;
        this.registerViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(BG_COLOR);
        this.setBorder(new EmptyBorder(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));

        // Title
        JLabel titleLabel = new JLabel("EventHiveUofT", JLabel.CENTER);
        titleLabel.setFont(getFontWithFallback("Inter", Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setForeground(TITLE_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Input fields
        usernameInputField = createInputField("Enter your username...");
        emailInputField = createInputField("Enter your email...");
        passwordInputField = createPasswordField("Enter your password...");

        // Buttons
        signupButton = createStyledButton("Sign up", evt -> handleSignupAction());
        loginButton = createStyledButton("Log in", evt -> handleLoginAction());

        addDocumentListener(usernameInputField, () -> updateState("username"));
        addDocumentListener(emailInputField, () -> updateState("email"));
        addDocumentListener(passwordInputField, () -> updateState("password"));

        // Add components, centering each main section
        this.add(Box.createVerticalStrut(VERTICAL_SPACING_SMALL));
        this.add(titleLabel);
        this.add(Box.createVerticalStrut(VERTICAL_SPACING_MEDIUM));

        JPanel usernamePanel = createLabelTextPanel("Username:", usernameInputField);
        usernamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(usernamePanel);

        this.add(Box.createVerticalStrut(VERTICAL_SPACING_SMALL));

        JPanel emailPanel = createLabelTextPanel("Email:", emailInputField);
        emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(emailPanel);

        this.add(Box.createVerticalStrut(INPUT_FIELD_HEIGHT));

        JPanel passwordPanel = createLabelTextPanel("Password:", passwordInputField);
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(passwordPanel);

        this.add(Box.createVerticalStrut(VERTICAL_SPACING_EXTRA_LARGE));

        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(buttonPanel);
    }

    private JTextField createInputField(String placeholder) {
        JTextField inputField = new JTextField(18);
        configureInputField(inputField, placeholder);
        return inputField;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(18);
        configureInputField(passwordField, placeholder);
        return passwordField;
    }

    private void configureInputField(JTextComponent inputField, String placeholder) {
        inputField.setPreferredSize(new Dimension(INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT));
        inputField.setMaximumSize(new Dimension(INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT));
        inputField.setMinimumSize(new Dimension(INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(INPUT_BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        inputField.setFont(getFontWithFallback("Segoe UI", Font.PLAIN, INPUT_LABEL_FONT_SIZE));
        inputField.setForeground(Color.GRAY);
        inputField.setBackground(INPUT_BG);
        inputField.setText(placeholder);

        inputField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (inputField.getText().equals(placeholder)) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
                inputField.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(INPUT_BORDER_FOCUS, 2, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (inputField.getText().isEmpty()) {
                    inputField.setForeground(Color.GRAY);
                    inputField.setText(placeholder);
                }
                inputField.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(INPUT_BORDER, 1, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
    }

    private JButton createStyledButton(String text, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                // Background
                g2.setColor(getModel().isRollover() ? BUTTON_BG_HOVER : BUTTON_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                // Border
                g2.setColor(BUTTON_BORDER);
                g2.setStroke(new java.awt.BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 18, 18);
                // Text/icon
                super.paintComponent(g);
                g2.dispose();
            }
        };
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 22, 8, 22));
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setFont(getFontWithFallback("Inter", Font.BOLD, BUTTON_FONT_SIZE));
        button.setForeground(BUTTON_TEXT);
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.addActionListener(actionListener);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.repaint(); }
            public void mouseExited(java.awt.event.MouseEvent evt) { button.repaint(); }
        });
        return button;
    }

    private JPanel createLabelTextPanel(String labelText, JTextComponent inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // Set fixed width for the panel to match the input field
        Dimension panelSize = new Dimension(INPUT_FIELD_WIDTH, INPUT_FIELD_HEIGHT + 30);
        panel.setPreferredSize(panelSize);
        panel.setMaximumSize(panelSize);
        panel.setMinimumSize(panelSize);

        JLabel label = new JLabel(labelText);
        label.setFont(getFontWithFallback("Segoe UI", Font.BOLD, INPUT_LABEL_FONT_SIZE));
        label.setForeground(INPUT_LABEL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(VERTICAL_SPACING_HIGH));
        panel.add(inputField);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the whole panel in the form

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, VERTICAL_SPACING_MEDIUM, 0));
        buttonPanel.setOpaque(false);

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        return buttonPanel;
    }

    private void addDocumentListener(JTextComponent inputField, Runnable updateAction) {
        inputField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateAction.run();
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateAction.run();
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateAction.run();
            }
        });
    }

    private void updateState(String fieldType) {
        RegisterState currentState = registerViewModel.getState();
        if ("username".equals(fieldType)) {
            currentState.setUsername(usernameInputField.getText());
        }
        else if ("password".equals(fieldType)) {
            currentState.setPassword(new String(passwordInputField.getPassword()));
        }
        else if ("email".equals(fieldType)) {
            currentState.setEmail(emailInputField.getText());
        }
        registerViewModel.setState(currentState);
    }

    private void handleSignupAction() {
        RegisterState currentState = registerViewModel.getState();
        registerController.execute(currentState.getUsername(), currentState.getEmail(), currentState.getPassword());
    }

    private void handleLoginAction() {
        registerController.switchToLoginView();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        RegisterState state = (RegisterState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setRegisterController(RegisterController controller) {
        this.registerController = controller;
    }

    // Font fallback utility
    private Font getFontWithFallback(String preferred, int style, int size) {
        Font font = new Font(preferred, style, size);
        if (!font.getFamily().equals(preferred)) {
            font = new Font("SansSerif", style, size);
        }
        return font;
    }
}