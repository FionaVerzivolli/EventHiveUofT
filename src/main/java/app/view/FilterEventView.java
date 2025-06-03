package app.view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import app.entity.Event.Event;
import app.interface_adapter.filter_event.FilterEventController;
import app.interface_adapter.filter_event.FilterEventState;
import app.interface_adapter.filter_event.FilterEventViewModel;
import app.interface_adapter.view_event.ViewEventController;

public class FilterEventView extends JPanel implements PropertyChangeListener {
    private static final String VIEW_NAME = "filterEvent";

    // Unified color palette
    private static final Color BG_COLOR = new Color(250, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color BUTTON_BG = Color.WHITE;
    private static final Color BUTTON_BG_HOVER = new Color(2, 36, 56, 30);
    private static final Color BUTTON_BORDER = new Color(2, 36, 56);
    private static final Color BUTTON_TEXT = BUTTON_BORDER;
    private static final Color TITLE_COLOR = BUTTON_BORDER;
    private static final Color INPUT_LABEL = new Color(90, 100, 120);

    private final FilterEventViewModel filterEventViewModel;
    private final JCheckBox musicCheckbox;
    private final JCheckBox sportsCheckbox;
    private final JCheckBox artCultureCheckbox;
    private final JCheckBox foodDrinkCheckbox;
    private final JCheckBox educationCheckbox;
    private final JCheckBox travelCheckbox;
    private final JCheckBox gamingCheckbox;
    private final JCheckBox festivalCheckbox;

    private final JButton submitFilterButton;
    private final JButton backButton;
    private final JPanel eventsPanel;
    private final int border = 16;

    private FilterEventController filterEventController;
    private ViewEventController viewEventController;

    public FilterEventView(FilterEventViewModel filterEventViewModel) {
        this.filterEventViewModel = filterEventViewModel;
        this.filterEventViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(new EmptyBorder(border, border, border, border));

        // Checkbox panel
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        checkboxPanel.setBackground(BG_COLOR);
        checkboxPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BUTTON_BORDER, 1, true), "Categories",
                0, 0, getFontWithFallback("Inter", Font.BOLD, 16), TITLE_COLOR));

        musicCheckbox = createStyledCheckbox("Music");
        sportsCheckbox = createStyledCheckbox("Sports");
        artCultureCheckbox = createStyledCheckbox("Art and Culture");
        foodDrinkCheckbox = createStyledCheckbox("Food & Drink");
        educationCheckbox = createStyledCheckbox("Education");
        travelCheckbox = createStyledCheckbox("Travel");
        gamingCheckbox = createStyledCheckbox("Gaming");
        festivalCheckbox = createStyledCheckbox("Festival");

        checkboxPanel.add(musicCheckbox);
        checkboxPanel.add(sportsCheckbox);
        checkboxPanel.add(artCultureCheckbox);
        checkboxPanel.add(foodDrinkCheckbox);
        checkboxPanel.add(educationCheckbox);
        checkboxPanel.add(travelCheckbox);
        checkboxPanel.add(gamingCheckbox);
        checkboxPanel.add(festivalCheckbox);

        add(checkboxPanel, BorderLayout.WEST);

        // Events panel
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        eventsPanel.setBackground(BG_COLOR);
        JScrollPane eventsScrollPane = new JScrollPane(eventsPanel);
        eventsScrollPane.setBorder(new LineBorder(BUTTON_BORDER, 1, true));
        add(eventsScrollPane, BorderLayout.CENTER);

        // Buttons
        submitFilterButton = createStyledButton("Filter Events", e -> applyFilters());
        backButton = createStyledButton("Back to Home", e -> goToHome());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(submitFilterButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JCheckBox createStyledCheckbox(String text) {
        JCheckBox box = new JCheckBox(text);
        box.setFont(getFontWithFallback("Inter", Font.PLAIN, 15));
        box.setForeground(INPUT_LABEL);
        box.setOpaque(false);
        box.setFocusPainted(false);
        box.setBorder(new EmptyBorder(6, 8, 6, 8));
        return box;
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
        button.setFont(getFontWithFallback("Inter", Font.BOLD, 15));
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

    private Font getFontWithFallback(String preferred, int style, int size) {
        Font font = new Font(preferred, style, size);
        if (!font.getFamily().equals(preferred)) {
            font = new Font("SansSerif", style, size);
        }
        return font;
    }

    private void applyFilters() {
        List<String> selectedCategories = new ArrayList<>();
        if (musicCheckbox.isSelected()) selectedCategories.add("Music");
        if (sportsCheckbox.isSelected()) selectedCategories.add("Sports");
        if (artCultureCheckbox.isSelected()) selectedCategories.add("Art and Culture");
        if (foodDrinkCheckbox.isSelected()) selectedCategories.add("Food & Drink");
        if (educationCheckbox.isSelected()) selectedCategories.add("Education");
        if (travelCheckbox.isSelected()) selectedCategories.add("Travel");
        if (gamingCheckbox.isSelected()) selectedCategories.add("Gaming");
        if (festivalCheckbox.isSelected()) selectedCategories.add("Festival");

        if (filterEventController != null) {
            filterEventController.execute(selectedCategories);
        } else {
            JOptionPane.showMessageDialog(this,
                    "FilterEventController is not set.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        FilterEventState state = (FilterEventState) evt.getNewValue();
        List<Event> events = state.getFilteredEvents();

        eventsPanel.removeAll();
        if (events != null) {
            for (Event event : events) {
                JPanel eventCard = new JPanel();
                eventCard.setLayout(new BoxLayout(eventCard, BoxLayout.Y_AXIS));
                eventCard.setBackground(CARD_BG);
                eventCard.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(BUTTON_BORDER, 1, true),
                        new EmptyBorder(16, 18, 16, 18)
                ));
                eventCard.setAlignmentX(Component.LEFT_ALIGNMENT);
                eventCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

                JLabel titleLabel = new JLabel(event.getTitle());
                titleLabel.setFont(getFontWithFallback("Inter", Font.BOLD, 18));
                titleLabel.setForeground(TITLE_COLOR);

                JLabel organizerLabel = new JLabel("Organizer: " + event.getOrganizer());
                organizerLabel.setFont(getFontWithFallback("Inter", Font.PLAIN, 15));
                organizerLabel.setForeground(INPUT_LABEL);

                JLabel descriptionLabel = new JLabel("<html><body style='width:300px'>" + event.getDescription() + "</body></html>");
                descriptionLabel.setFont(getFontWithFallback("Inter", Font.PLAIN, 14));
                descriptionLabel.setForeground(Color.DARK_GRAY);

                JLabel dateTimeLabel = new JLabel("Date & Time: " + event.getDateTime());
                dateTimeLabel.setFont(getFontWithFallback("Inter", Font.PLAIN, 14));
                dateTimeLabel.setForeground(INPUT_LABEL);

                JLabel capacityLabel = new JLabel("Capacity: " + event.getCapacity());
                capacityLabel.setFont(getFontWithFallback("Inter", Font.PLAIN, 14));
                capacityLabel.setForeground(INPUT_LABEL);

                JLabel tagsLabel = new JLabel("Tags: " + String.join(", ", event.getTags()));
                tagsLabel.setFont(getFontWithFallback("Inter", Font.ITALIC, 13));
                tagsLabel.setForeground(new Color(120, 130, 150));

                JButton viewEventButton = createStyledButton("View Event", e -> viewEvent(event));
                viewEventButton.setAlignmentX(Component.LEFT_ALIGNMENT);

                eventCard.add(titleLabel);
                eventCard.add(Box.createVerticalStrut(4));
                eventCard.add(organizerLabel);
                eventCard.add(descriptionLabel);
                eventCard.add(dateTimeLabel);
                eventCard.add(capacityLabel);
                eventCard.add(tagsLabel);
                eventCard.add(Box.createVerticalStrut(8));
                eventCard.add(viewEventButton);

                eventsPanel.add(Box.createVerticalStrut(10));
                eventsPanel.add(eventCard);
            }
            eventsPanel.revalidate();
            eventsPanel.repaint();
        }
    }

    private void viewEvent(Event event) {
        if (viewEventController != null) {
            viewEventController.execute(event.getTitle());
        }
    }

    private void goToHome() {
        if (filterEventController != null) {
            filterEventController.switchToHomeView();
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setFilterEventsController(FilterEventController controller) {
        this.filterEventController = controller;
    }

    public void setViewEventController(ViewEventController controller) {
        this.viewEventController = controller;
    }
}