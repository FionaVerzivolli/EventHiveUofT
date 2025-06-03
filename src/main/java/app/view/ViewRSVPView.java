package app.view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import app.interface_adapter.view_rsvp.ViewRSVPController;
import app.interface_adapter.view_rsvp.ViewRSVPState;
import app.interface_adapter.view_rsvp.ViewRSVPViewModel;

public class ViewRSVPView extends JPanel implements PropertyChangeListener {
    // Unified color palette
    private static final Color BG_COLOR = new Color(250, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color BUTTON_BG = Color.WHITE;
    private static final Color BUTTON_BG_HOVER = new Color(2, 36, 56, 30);
    private static final Color BUTTON_BORDER = new Color(2, 36, 56);
    private static final Color BUTTON_TEXT = BUTTON_BORDER;
    private static final Color TITLE_COLOR = BUTTON_BORDER;
    private static final Color INPUT_LABEL = new Color(90, 100, 120);

    private static final int PANEL_PADDING = 16;
    private static final int EVENT_CARD_PADDING = 14;
    private static final int TITLE_FONT_SIZE = 22;
    private static final int EVENT_FONT_SIZE = 16;

    private static final String VIEW_NAME = "viewRSVP";
    private final ViewRSVPViewModel viewRSVPViewModel;
    private final JPanel eventsPanel;
    private final JButton backButton;
    private final JButton viewRSVPButton;
    private ViewRSVPController viewRSVPController;

    public ViewRSVPView(ViewRSVPViewModel viewRSVPViewModel) {
        this.viewRSVPViewModel = viewRSVPViewModel;
        this.viewRSVPViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(new EmptyBorder(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));

        // Events panel
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        eventsPanel.setBackground(BG_COLOR);
        JScrollPane eventsScrollPane = new JScrollPane(eventsPanel);
        eventsScrollPane.setBorder(new LineBorder(BUTTON_BORDER, 1, true));
        add(eventsScrollPane, BorderLayout.CENTER);

        // Buttons
        viewRSVPButton = createStyledButton("View RSVPs", e -> viewRSVP());
        backButton = createStyledButton("Back to Home", e -> goToHome());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(viewRSVPButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ViewRSVPState state = (ViewRSVPState) evt.getNewValue();
        List<String> rsvpEvents = state.getViewRSVP();

        eventsPanel.removeAll();

        // Title
        JLabel titleLabel = new JLabel("Your RSVPed Events:");
        titleLabel.setFont(getFontWithFallback("Inter", Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setForeground(TITLE_COLOR);
        titleLabel.setBorder(new EmptyBorder(0, 0, 12, 0));
        eventsPanel.add(titleLabel);

        if (rsvpEvents != null && !rsvpEvents.isEmpty()) {
            for (String eventName : rsvpEvents) {
                JPanel eventCard = new JPanel();
                eventCard.setLayout(new BoxLayout(eventCard, BoxLayout.Y_AXIS));
                eventCard.setBackground(CARD_BG);
                eventCard.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(BUTTON_BORDER, 1, true),
                        new EmptyBorder(EVENT_CARD_PADDING, EVENT_CARD_PADDING, EVENT_CARD_PADDING, EVENT_CARD_PADDING)
                ));
                eventCard.setAlignmentX(Component.LEFT_ALIGNMENT);
                eventCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

                JLabel eventLabel = new JLabel(eventName);
                eventLabel.setFont(getFontWithFallback("Inter", Font.PLAIN, EVENT_FONT_SIZE));
                eventLabel.setForeground(INPUT_LABEL);

                eventCard.add(eventLabel);
                eventsPanel.add(Box.createVerticalStrut(8));
                eventsPanel.add(eventCard);
            }
        } else {
            JLabel noEventsLabel = new JLabel("No RSVP events found.");
            noEventsLabel.setFont(getFontWithFallback("Inter", Font.ITALIC, EVENT_FONT_SIZE));
            noEventsLabel.setForeground(INPUT_LABEL);
            noEventsLabel.setBorder(new EmptyBorder(16, 0, 0, 0));
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
        button.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
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

    private void viewRSVP() {
        ViewRSVPState currentState = viewRSVPViewModel.getState();
        if (viewRSVPController != null) {
            viewRSVPController.execute(currentState.getUsernameState());
        }
    }

    private void goToHome() {
        if (viewRSVPController != null) {
            viewRSVPController.switchToHomeView();
        }
    }

    public void setViewRSVPController(ViewRSVPController controller) {
        this.viewRSVPController = controller;
    }

    public String getViewName() {
        return VIEW_NAME;
    }
}