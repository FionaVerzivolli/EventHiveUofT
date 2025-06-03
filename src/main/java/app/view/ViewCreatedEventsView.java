package app.view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import app.interface_adapter.notify_users.NotifyUserController;
import app.interface_adapter.view_created_events.ViewCreatedEventsController;
import app.interface_adapter.view_created_events.ViewCreatedEventsState;
import app.interface_adapter.view_created_events.ViewCreatedEventsViewModel;

public class ViewCreatedEventsView extends JPanel implements PropertyChangeListener {
    // Unified color palette
    private static final Color BG_COLOR = new Color(250, 250, 252);                // Very light warm gray
    private static final Color CARD_BG = Color.WHITE;
    private static final Color BUTTON_BG = Color.WHITE;
    private static final Color BUTTON_BG_HOVER = new Color(2, 36, 56, 30);         // Vibrant blue, very light
    private static final Color BUTTON_BORDER = new Color(2, 36, 56);               // Vibrant blue
    private static final Color BUTTON_TEXT = BUTTON_BORDER;
    private static final Color TITLE_COLOR = BUTTON_BORDER;
    private static final Color LABEL_COLOR = new Color(90, 100, 120);

    // Sizing
    private static final int PANEL_PADDING = 16;
    private static final int EVENT_PADDING = 8;
    private static final int TITLE_FONT_SIZE = 24;
    private static final int EVENT_LABEL_FONT_SIZE = 18;
    private static final int BUTTON_FONT_SIZE = 15;
    private static final int BUTTON_ARC = 18;

    private static final String VIEW_NAME = "viewCreated";
    private final ViewCreatedEventsViewModel viewCreatedEventsViewModel;
    private final JPanel eventsPanel;
    private final JButton backButton;
    private final JButton viewCreatedEventsButton;
    private ViewCreatedEventsController viewCreatedEventsController;
    private NotifyUserController notifyUserController;

    public ViewCreatedEventsView(ViewCreatedEventsViewModel viewCreatedEventsViewModel) {
        this.viewCreatedEventsViewModel = viewCreatedEventsViewModel;
        this.viewCreatedEventsViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(new EmptyBorder(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));

        // Panel for events
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        eventsPanel.setBackground(BG_COLOR);
        JScrollPane eventsScrollPane = new JScrollPane(eventsPanel);
        eventsScrollPane.setBorder(new LineBorder(BUTTON_BORDER, 1, true));
        this.add(eventsScrollPane, BorderLayout.CENTER);

        // Buttons to navigate or trigger actions
        viewCreatedEventsButton = createStyledButton("View Created Events", e -> viewCreatedEvents());
        backButton = createStyledButton("Back to Home", e -> goToHome());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(viewCreatedEventsButton);
        buttonPanel.add(backButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ViewCreatedEventsState state = (ViewCreatedEventsState) evt.getNewValue();
        List<String> createdEvents = state.getCreatedEvents();

        eventsPanel.removeAll();

        // Add a title label at the top
        JLabel titleLabel = new JLabel("Your Created Events:");
        titleLabel.setFont(getFontWithFallback("Inter", Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setForeground(TITLE_COLOR);
        titleLabel.setBorder(new EmptyBorder(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));
        eventsPanel.add(titleLabel);

        if (createdEvents != null && !createdEvents.isEmpty()) {
            for (String eventName : createdEvents) {
                JPanel eventPanel = new JPanel(new BorderLayout());
                eventPanel.setBackground(CARD_BG);
                eventPanel.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(BUTTON_BORDER, 1, true),
                        new EmptyBorder(EVENT_PADDING, EVENT_PADDING, EVENT_PADDING, EVENT_PADDING)
                ));

                JLabel eventLabel = new JLabel(eventName);
                eventLabel.setFont(getFontWithFallback("Inter", Font.PLAIN, EVENT_LABEL_FONT_SIZE));
                eventLabel.setForeground(LABEL_COLOR);
                eventPanel.add(eventLabel, BorderLayout.WEST);

                JButton sendNotificationButton = createStyledButton(
                        "Send Notification Email to RSVPED users",
                        e -> sendNotificationEmail(eventName)
                );
                eventPanel.add(sendNotificationButton, BorderLayout.EAST);

                eventsPanel.add(eventPanel);

                JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
                separator.setForeground(BUTTON_BORDER);
                eventsPanel.add(separator);
            }
        } else {
            JLabel noEventsLabel = new JLabel("No created events found.");
            noEventsLabel.setFont(getFontWithFallback("Inter", Font.ITALIC, EVENT_LABEL_FONT_SIZE));
            noEventsLabel.setForeground(LABEL_COLOR);
            noEventsLabel.setBorder(new EmptyBorder(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));
            eventsPanel.add(noEventsLabel);
        }

        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    private JButton createStyledButton(String text, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? BUTTON_BG_HOVER : BUTTON_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), BUTTON_ARC, BUTTON_ARC);
                g2.setColor(BUTTON_BORDER);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, BUTTON_ARC, BUTTON_ARC);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        button.setFont(getFontWithFallback("Inter", Font.BOLD, BUTTON_FONT_SIZE));
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

    private void sendNotificationEmail(String eventName) {
        notifyUserController.execute(eventName);
        JOptionPane.showMessageDialog(
                null,
                "You have notified all the users who have rsvped to " + eventName,
                "Notification success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void viewCreatedEvents() {
        ViewCreatedEventsState currentState = viewCreatedEventsViewModel.getState();
        viewCreatedEventsController.execute(currentState.getUsernameState());
    }

    private void goToHome() {
        viewCreatedEventsController.switchToHomeView();
    }

    public void setViewCreatedEventsController(ViewCreatedEventsController controller) {
        this.viewCreatedEventsController = controller;
    }

    public void setNotificationController(NotifyUserController controller) {
        this.notifyUserController = controller;
    }

    public String getViewName() {
        return VIEW_NAME;
    }
}