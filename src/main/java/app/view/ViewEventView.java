package app.view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import app.entity.Event.Event;
import app.interface_adapter.rsvp_event.RSVPController;
import app.interface_adapter.view_event.ViewEventController;
import app.interface_adapter.view_event.ViewEventState;
import app.interface_adapter.view_event.ViewEventViewModel;

public class ViewEventView extends JPanel implements PropertyChangeListener {
    private static final String VIEW_NAME = "viewEvent";

    // Unified color palette
    private static final Color BG_COLOR = new Color(250, 250, 252);                // Very light warm gray
    private static final Color CARD_BG = Color.WHITE;                              // Card background
    private static final Color BUTTON_BG = Color.WHITE;                            // Button background
    private static final Color BUTTON_BG_HOVER = new Color(2, 36, 56, 30);         // Vibrant blue, very light
    private static final Color BUTTON_BORDER = new Color(2, 36, 56);               // Vibrant blue
    private static final Color TITLE_COLOR = new Color(2, 36, 56);                 // Vibrant blue

    // Sizing
    private static final int PANEL_PADDING = 20;
    private static final int CARD_PADDING = 30;
    private static final int VERTICAL_SPACING = 18;
    private static final int TITLE_FONT_SIZE = 32;
    private static final int LABEL_FONT_SIZE = 20;
    private static final int BUTTON_FONT_SIZE = 16;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 40;

    private ViewEventViewModel viewEventViewModel;
    private final JButton rsvpButton;
    private final JButton homeButton;
    private ViewEventController viewEventController;
    private RSVPController rsvpController;

    public ViewEventView(ViewEventViewModel viewEventViewModel) {
        this.viewEventViewModel = viewEventViewModel;
        this.viewEventViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(new EmptyBorder(PANEL_PADDING, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));

        rsvpButton = createStyledButton("RSVP", e -> rsvpEvent());
        homeButton = createStyledButton("Go Home", e -> goBackHome());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(rsvpButton);
        buttonPanel.add(homeButton);

        add(buttonPanel, BorderLayout.SOUTH);
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
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setFont(getFontWithFallback("Inter", Font.BOLD, BUTTON_FONT_SIZE));
        button.setForeground(BUTTON_BORDER);
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

    private void rsvpEvent() {
        ViewEventState currentState = viewEventViewModel.getState();
        rsvpController.execute(currentState.getUsernameState(), currentState.getViewEvent().getEventId());
        JOptionPane.showMessageDialog(
                null,
                "You have RSVPed to: " + currentState.getViewEvent().getEventId(),
                "RSVP Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ViewEventState state = (ViewEventState) evt.getNewValue();
        Event event = state.getViewEvent();

        this.removeAll();
        if (event != null) {
            JPanel cardPanel = new JPanel();
            cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
            cardPanel.setBackground(CARD_BG);
            cardPanel.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(BUTTON_BORDER, 2, 24),
                    new EmptyBorder(CARD_PADDING, CARD_PADDING, CARD_PADDING, CARD_PADDING)
            ));

            JLabel titleLabel = new JLabel(event.getTitle());
            titleLabel.setFont(getFontWithFallback("Inter", Font.BOLD, TITLE_FONT_SIZE));
            titleLabel.setForeground(TITLE_COLOR);
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel organizerLabel = createInfoLabel("Organizer: " + event.getOrganizer());
            JLabel descriptionLabel = createInfoLabel("<html>Description: " + event.getDescription() + "</html>");
            JLabel dateTimeLabel = createInfoLabel("Date & Time: " + event.getDateTime());
            JLabel capacityLabel = createInfoLabel("Capacity: " + event.getCapacity());
            JLabel tagsLabel = createInfoLabel("Tags: " + String.join(", ", event.getTags()));

            organizerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            dateTimeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            capacityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            tagsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            cardPanel.add(titleLabel);
            cardPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
            cardPanel.add(organizerLabel);
            cardPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
            cardPanel.add(descriptionLabel);
            cardPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
            cardPanel.add(dateTimeLabel);
            cardPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
            cardPanel.add(capacityLabel);
            cardPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
            cardPanel.add(tagsLabel);

            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setOpaque(false);
            centerPanel.add(cardPanel);

            this.add(centerPanel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            buttonPanel.setOpaque(false);
            buttonPanel.add(rsvpButton);
            buttonPanel.add(homeButton);

            this.add(buttonPanel, BorderLayout.SOUTH);

            this.revalidate();
            this.repaint();
        }
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(getFontWithFallback("Inter", Font.PLAIN, LABEL_FONT_SIZE));
        label.setForeground(Color.DARK_GRAY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    public void goBackHome() {
        viewEventController.switchToHomeView();
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setViewEventController(ViewEventController controller) {
        this.viewEventController = controller;
    }

    public void setRSVPEventController(RSVPController controller) {
        this.rsvpController = controller;
    }
}

// --- RoundedBorder class ---
class RoundedBorder extends LineBorder {
    private final int arc;
    public RoundedBorder(Color color, int thickness, int arc) {
        super(color, thickness, true);
        this.arc = arc;
    }
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(lineColor);
        g2.setStroke(new BasicStroke(thickness));
        g2.drawRoundRect(x, y, width - 1, height - 1, arc, arc);
    }
}