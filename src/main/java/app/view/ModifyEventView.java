package app.view;


import app.interface_adapter.modify_event.ModifyEventController;
import app.interface_adapter.modify_event.ModifyEventState;
import app.interface_adapter.modify_event.ModifyEventViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class ModifyEventView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "modifyEvent";

    private final ModifyEventViewModel modifyEventViewModel;
    private final JTextField oldTitleInputField, updatedOrgInputField, updatedTitleInputField, updatedDescriptionInputField, updatedTimeInputField,
            updatedCapacityInputField, updatedTagsInputField, updatedLatitudeInputField, updatedLongitudeInputField;
    private final JButton modifyEventButton;
    private final JButton homeButton;
    private final JCheckBox deleteEventCheckbox;
    private ModifyEventController modifyEventController;
    private JPanel parentPanel;

    public ModifyEventView(ModifyEventViewModel modifyEventViewModel, ModifyEventController controller) {
        this.modifyEventViewModel = modifyEventViewModel;
        this.modifyEventViewModel.addPropertyChangeListener(this);
        this.modifyEventController = controller;

        // Configure the layout
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Main panel for form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Modify Event",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.BLUE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;



        // Build components and generate placeholders
        oldTitleInputField = createInputField("Enter the old title...");
        updatedOrgInputField = createInputField("Enter the new organizer(s)...");
        updatedTitleInputField = createInputField("Enter your new title...");
        updatedDescriptionInputField = createInputField("Enter your new description... (150 characters max)");
        updatedTimeInputField = createInputField("Please enter the new time and date...");
        updatedCapacityInputField = createInputField("Enter the new capacity...");
        updatedLatitudeInputField = createInputField("Please enter the new latitude of the address");
        updatedLongitudeInputField = createInputField("Please enter the new longitude of the address");
        updatedTagsInputField = createInputField("Enter new event tags (comma-separated)...");
        // Create an event button
        modifyEventButton = modifyEventButton();
        // Create a home button
        homeButton = createHomeButton();
        // Add document listeners for input validation
        addDocumentListener(oldTitleInputField, () -> updateState("Old title"));
        addDocumentListener(updatedTitleInputField, () -> updateState("Title"));
        addDocumentListener(updatedDescriptionInputField, () -> updateState("Description"));
        addDocumentListener(updatedCapacityInputField, () -> updateState("Capacity"));
        addDocumentListener(updatedTagsInputField, () -> updateState("Tags"));
        addDocumentListener(updatedTimeInputField, () -> updateState("Date and time"));
        addDocumentListener(updatedOrgInputField, () -> updateState("Organizers"));
        addDocumentListener(updatedLatitudeInputField, () -> updateState("Latitude"));
        addDocumentListener(updatedLongitudeInputField, () -> updateState("Longitude"));
        modifyEventButton.addActionListener(this::actionPerformed);
        homeButton.addActionListener(this::actionPerformed);

        // Add labels and fields to the panel, starting with the old Title
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Old title:"), gbc);
        gbc.gridx = 1;
        formPanel.add(oldTitleInputField, gbc);

        // Add organizers input
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel("Organizers:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedOrgInputField, gbc);

        // Add description input
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabel("Title:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedTitleInputField, gbc);

        // Add capacity input
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createLabel("Description:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedDescriptionInputField, gbc);

        // Add tags input
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(createLabel("Datetime:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedTimeInputField, gbc);

        // Add building input
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(createLabel("Capacity:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedCapacityInputField, gbc);

        // Add floor number input
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(createLabel("Latitude:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedLatitudeInputField, gbc);

        // Add room number input
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(createLabel("Longitude:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedLongitudeInputField, gbc);

        // Add room number input
        gbc.gridx = 0; gbc.gridy = 8;
        formPanel.add(createLabel("Tags:"), gbc);
        gbc.gridx = 1;
        formPanel.add(updatedTagsInputField, gbc);

        // Add the create button
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(modifyEventButton, gbc);

        // Add the home button
        gbc.gridx = 0; gbc.gridy = 10; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(homeButton, gbc);

        // delete event box
        deleteEventCheckbox = new JCheckBox("Delete Event?");
        deleteEventCheckbox.setFont(new Font("Arial", Font.BOLD, 30));
        deleteEventCheckbox.setForeground(Color.RED);
        deleteEventCheckbox.setFocusPainted(false);
        deleteEventCheckbox.addActionListener(this::actionPerformed);
        // Add the checkbox to the formPanel layout
        gbc.gridx = 0; gbc.gridy = 11; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(deleteEventCheckbox, gbc);

        // Add formPanel to the main panel
        this.add(formPanel, BorderLayout.CENTER);
    }

    private JTextField createInputField(String placeholder) {
        JTextField field = new JTextField(); // No fixed width
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        field.setToolTipText(placeholder);
        return field;
    }


    private JButton modifyEventButton() {
        JButton button = new JButton("Modify Event");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.DARK_GRAY);
        return label;
    }

    // Updates state based on user input
    private void updateState(String fieldName) {
        // Implementation of state update (placeholder)
        ModifyEventState currentState = modifyEventViewModel.getState();
        currentState.setEventId("123");
        if ("Old title".equals(fieldName)) {
            currentState.setOldTitle(oldTitleInputField.getText());
        }else if ("Title".equals(fieldName)) {
            currentState.setNewTitle(updatedTitleInputField.getText());
        } else if ("Description".equals(fieldName)) {
            currentState.setDescription(updatedDescriptionInputField.getText());
        } else if ("Capacity".equals(fieldName)) {
            currentState.setCapacity(parseInt(updatedCapacityInputField.getText()));
        } else if ("Tags".equals(fieldName)) {
            currentState.setTags(updatedTagsInputField.getText());
        } else if ("Date and time".equals(fieldName)) {
            currentState.setDateTime(updatedTimeInputField.getText());
        } else if ("Organizers".equals(fieldName)) {
            currentState.setOrganizer(updatedOrgInputField.getText());
        } else if ("Latitude".equals(fieldName)) {
            currentState.setLatitude(parseFloat(updatedLatitudeInputField.getText()));
        } else if ("Longitude".equals(fieldName)) {
            currentState.setLongitude(parseFloat(updatedLongitudeInputField.getText()));
        } else if ("DeleteEvent".equals(fieldName)) {
            currentState.setDeleteEvent(deleteEventCheckbox.isSelected());
        }
        modifyEventViewModel.setState(currentState);
    }

    // Add a document listener to a JTextField
    private void addDocumentListener(JTextField textField, Runnable callback) {
        textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                callback.run();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                callback.run();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                callback.run();
            }
        });
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource() == modifyEventButton) {
            ModifyEventState currentState = modifyEventViewModel.getState();
            modifyEventController.execute(currentState.getOldTitle(), currentState.getNewTitle(), currentState.getDescription(),
                    currentState.getDateTime(), currentState.getCapacity(), currentState.getLatitude(), currentState.getLongitude(),
                    currentState.getDeleteEvent(), currentState.getTags(), "TMP", currentState.getOrganizer());
            System.out.println("Event created!");
        } else if (e.getSource() == homeButton) {
            navigateTo("Home");
            System.out.println("Went home!");
        } else if (e.getSource() == deleteEventCheckbox) {
            updateState("DeleteEvent"); // Ensure the state is updated when the checkbox is toggled
            System.out.println("DeleteEvent checkbox state changed: " + deleteEventCheckbox.isSelected());
        }
    }


    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ModifyEventState state = (ModifyEventState) evt.getNewValue();
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

    /**
     * Function to navigate from this screen to a different screen
     * using the name of the new screen.
     * @param viewName the name of the view you want to navigate to
     * */
    public void navigateTo(String viewName) {
        // Check if the parentPanel is valid for debugging purposes
        if (parentPanel != null && parentPanel.getLayout() instanceof CardLayout) {
            // Debug statement in console:
            System.out.println("Navigating to: " + viewName);

            CardLayout layout = (CardLayout) parentPanel.getLayout();
            layout.show(parentPanel, viewName);

            // Revalidate and redraw the parent panel after you navigate to it
            parentPanel.revalidate();
            parentPanel.repaint();
        } else {
            // Console error statement if the navigation doesn't work
            System.out.println("Navigation failed: parentPanel or layout is not set up correctly.");
        }
    }
    private JButton createHomeButton() {
        JButton button = new JButton("Home");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 180, 0));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
