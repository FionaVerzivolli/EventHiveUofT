package app.view;

import app.interface_adapter.modify_event.ModifyEventController;
import app.interface_adapter.modify_event.ModifyEventState;
import app.interface_adapter.modify_event.ModifyEventViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class ModifyEventView extends JPanel implements java.awt.event.ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "modifyEvent";

    // Unified color palette
    private static final Color BG_COLOR = new Color(250, 250, 252);
    private static final Color BUTTON_BG = Color.WHITE;
    private static final Color BUTTON_BG_HOVER = new Color(2, 36, 56, 30);
    private static final Color BUTTON_BORDER = new Color(2, 36, 56);
    private static final Color BUTTON_TEXT = BUTTON_BORDER;
    private static final Color TITLE_COLOR = BUTTON_BORDER;
    private static final Color INPUT_BG = Color.WHITE;
    private static final Color INPUT_BORDER = new Color(220, 225, 235);
    private static final Color INPUT_BORDER_FOCUS = BUTTON_BORDER;
    private static final Color INPUT_LABEL = new Color(90, 100, 120);

    private final ModifyEventViewModel modifyEventViewModel;
    private final JTextField oldTitleInputField, updatedOrgInputField, updatedTitleInputField, updatedDescriptionInputField, updatedTimeInputField,
            updatedCapacityInputField, updatedTagsInputField, updatedLocationInputField;
    private final JButton modifyEventButton;
    private final JButton homeButton;
    private final JCheckBox deleteEventCheckbox;
    private ModifyEventController modifyEventController;
    private JPanel parentPanel;

    public ModifyEventView(ModifyEventViewModel modifyEventViewModel) {
        this.modifyEventViewModel = modifyEventViewModel;
        this.modifyEventViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setBackground(BG_COLOR);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(BG_COLOR);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BUTTON_BORDER, 2, true), "Modify Event",
                TitledBorder.CENTER, TitledBorder.TOP, getFontWithFallback("Inter", Font.BOLD, 20), TITLE_COLOR));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        oldTitleInputField = createInputField("Enter the old title...");
        updatedOrgInputField = createInputField("Enter the new organizer(s)...");
        updatedTitleInputField = createInputField("Enter your new title...");
        updatedDescriptionInputField = createInputField("Enter your new description... (150 characters max)");
        updatedTimeInputField = createInputField("Please enter the new time and date...");
        updatedCapacityInputField = createInputField("Enter the new capacity...");
        updatedLocationInputField = createInputField("Enter the new location...");
        updatedTagsInputField = createInputField("Enter new event tags (comma-separated)...");

        modifyEventButton = createStyledButton("Modify Event", this::actionPerformed);
        homeButton = createStyledButton("Home", this::actionPerformed);

        addDocumentListener(oldTitleInputField, () -> updateState("Old title"));
        addDocumentListener(updatedTitleInputField, () -> updateState("Title"));
        addDocumentListener(updatedDescriptionInputField, () -> updateState("Description"));
        addDocumentListener(updatedCapacityInputField, () -> updateState("Capacity"));
        addDocumentListener(updatedTagsInputField, () -> updateState("Tags"));
        addDocumentListener(updatedTimeInputField, () -> updateState("Date and time"));
        addDocumentListener(updatedOrgInputField, () -> updateState("Organizers"));
        addDocumentListener(updatedLocationInputField, () -> updateState("Location"));

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(createLabel("Old title:"), gbc);
        gbc.gridx = 1;
        formPanel.add(oldTitleInputField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(createLabel("Organizers:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedOrgInputField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(createLabel("Title:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedTitleInputField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(createLabel("Description:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedDescriptionInputField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(createLabel("Datetime:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedTimeInputField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(createLabel("Capacity:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedCapacityInputField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(createLabel("Location:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedLocationInputField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        formPanel.add(createLabel("Tags:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedTagsInputField, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(modifyEventButton, gbc);

        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        formPanel.add(homeButton, gbc);

        deleteEventCheckbox = new JCheckBox("Delete Event?");
        deleteEventCheckbox.setFont(getFontWithFallback("Inter", Font.BOLD, 20));
        deleteEventCheckbox.setForeground(Color.RED);
        deleteEventCheckbox.setFocusPainted(false);
        deleteEventCheckbox.setOpaque(false);
        deleteEventCheckbox.addActionListener(this::actionPerformed);
        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST; // Align left
        formPanel.add(deleteEventCheckbox, gbc);


        this.add(formPanel, BorderLayout.CENTER);
    }

    private JTextField createInputField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(getFontWithFallback("Segoe UI", Font.PLAIN, 15));
        field.setPreferredSize(new Dimension(320, 32));
        field.setMinimumSize(new Dimension(320, 32));
        field.setMaximumSize(new Dimension(320, 32));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(INPUT_BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(INPUT_BG);
        field.setForeground(Color.GRAY);
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
        field.setText(placeholder);
        return field;
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
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFont(getFontWithFallback("Inter", Font.BOLD, 16));
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

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(getFontWithFallback("Inter", Font.BOLD, 15));
        label.setForeground(INPUT_LABEL);
        return label;
    }

    private Font getFontWithFallback(String preferred, int style, int size) {
        Font font = new Font(preferred, style, size);
        if (!font.getFamily().equals(preferred)) {
            font = new Font("SansSerif", style, size);
        }
        return font;
    }

    private void updateState(String fieldName) {
        ModifyEventState currentState = modifyEventViewModel.getState();
        currentState.setEventId("123");
        if ("Old title".equals(fieldName)) {
            currentState.setOldTitle(oldTitleInputField.getText());
        } else if ("Title".equals(fieldName)) {
            currentState.setNewTitle(updatedTitleInputField.getText());
        } else if ("Description".equals(fieldName)) {
            currentState.setDescription(updatedDescriptionInputField.getText());
        } else if ("Capacity".equals(fieldName)) {
            currentState.setCapacity(updatedCapacityInputField.getText());
        } else if ("Tags".equals(fieldName)) {
            currentState.setTags(updatedTagsInputField.getText());
        } else if ("Date and time".equals(fieldName)) {
            currentState.setDateTime(updatedTimeInputField.getText());
        } else if ("Organizers".equals(fieldName)) {
            currentState.setOrganizer(updatedOrgInputField.getText());
        } else if ("Location".equals(fieldName)) {
            // Not operational, just store as a string if needed
            // currentState.setLocation(updatedLocationInputField.getText());
        } else if ("DeleteEvent".equals(fieldName)) {
            currentState.setDeleteEvent(deleteEventCheckbox.isSelected());
        }
        modifyEventViewModel.setState(currentState);
    }

    private void addDocumentListener(JTextField textField, Runnable callback) {
        textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { callback.run(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { callback.run(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { callback.run(); }
        });
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource() == modifyEventButton) {
            try {
                ModifyEventState currentState = modifyEventViewModel.getState();
                // Location is not operational, so pass a dummy value or ignore
                modifyEventController.execute(
                        currentState.getOldTitle(),
                        currentState.getNewTitle(),
                        currentState.getDescription(),
                        currentState.getDateTime(),
                        parseInt(currentState.getCapacity()),
                        0f, // latitude dummy
                        0f, // longitude dummy
                        currentState.getDeleteEvent(),
                        currentState.getTags(),
                        "TMP",
                        currentState.getOrganizer()
                );
                JOptionPane.showMessageDialog(
                        null,
                        "You have modified the event: " + currentState.getOldTitle(),
                        "Modify Event Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception error) {
                JOptionPane.showMessageDialog(
                        null,
                        "Failure: " + error.getMessage(),
                        "Failure",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } else if (e.getSource() == homeButton) {
            goToHome();
        } else if (e.getSource() == deleteEventCheckbox) {
            updateState("DeleteEvent");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // No-op for now
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setParentPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    public void setModifyEventController(ModifyEventController controller) {
        this.modifyEventController = controller;
    }

    private void goToHome() {
        modifyEventController.switchToHomeView();
    }
}