package app.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import javax.swing.*;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.*;

import app.interface_adapter.display_event.DisplayEventController;
import app.interface_adapter.home.HomeController;
import app.interface_adapter.home.HomeViewModel;
import app.interface_adapter.view_event.ViewEventController;

public class HomeView extends JPanel implements PropertyChangeListener {

    private static final int MIN_ZOOM_LEVEL = 1;
    private static final int MAX_ZOOM_LEVEL = 17;
    private static final double EARTH_RADIUS = 6371000.0;
    private static final double ZOOM_THRESHOLD_LOW = 0.000025;
    private static final double ZOOM_THRESHOLD_MEDIUM = 0.00025;
    private static final double ZOOM_THRESHOLD_HIGH = 0.001;
    private static final double ZOOM_MULTIPLIER = 0.005;
    private static final double GEO_LATITUTE = 43.6629;
    private static final double GEO_LONGITUDE = -79.3957;
    private static final int LOW_ZOOM_THRESHOLD = 5;
    private static final int MEDIUM_ZOOM_THRESHOLD = 7;
    private static final int HIGH_ZOOM_THRESHOLD = 9;
    private static final int TILE_SIZE = 256;
    private static final int BUTTON_FONT_SIZE = 15;
    private static final int WAYPOINT_RADIUS = 6;
    private static final int SIDEBAR_PADDING = 10;
    private static final int SIDEBAR_SPACING = 12;
    private static final int TITLE_FONT_SIZE = 24;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 40;

    // Color palette
    private static final Color SIDEBAR_BG = new Color(250, 250, 252);           // Very light warm gray
    private static final Color BUTTON_BG = Color.WHITE;                         // White
    private static final Color BUTTON_BG_HOVER = new Color(2, 36, 56, 30);   // Vibrant blue, very light
    private static final Color BUTTON_BORDER = new Color(2, 36, 56);         // Vibrant blue
    private static final Color TITLE_COLOR = new Color(2, 36, 56);           // Vibrant blue

    private static final String VIEW_NAME = "Home";
    private final HomeViewModel homeViewModel;
    private final DisplayEventController displayEventController;
    private ViewEventController viewEventController;
    private JPanel parentPanel;
    private HomeController homeController;
    private ArrayList<ArrayList<Object>> events;

    private final JButton zoomInButton;
    private final JButton zoomOutButton;
    private final JButton logOutButton;
    private final JButton filterButton;
    private final JButton createEventButton;
    private final JButton viewRSVPButton;
    private final JButton viewCreatedButton;
    private final JButton modifyEventButton;

    private double ZOOM_LEVEL;
    private JXMapViewer mapViewer;

    public HomeView(HomeViewModel homeViewModel, DisplayEventController displayEventController) {
        this.homeViewModel = homeViewModel;
        this.displayEventController = displayEventController;
        this.events = displayEventController.execute();
        this.homeViewModel.addPropertyChangeListener(this);

        // Styled buttons
        zoomInButton = createStyledButton("+", evt -> handleZoomInAction());
        zoomOutButton = createStyledButton("-", evt -> handleZoomOutAction());
        logOutButton = createStyledButton("Log out", evt -> handleLogoutAction());
        filterButton = createStyledButton("Filter", evt -> handleFilterAction());
        createEventButton = createStyledButton("Create event", evt -> handleEventAction());
        viewRSVPButton = createStyledButton("View RSVP", evt -> handleViewRSVPAction());
        viewCreatedButton = createStyledButton("View Created", evt -> handleViewCreatedEvents());
        modifyEventButton = createStyledButton("Modify Event", evt -> handleViewModifyEvent());

        setLayout(new BorderLayout());
        setBackground(SIDEBAR_BG);

        setupView();
    }

    public void setParentPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    private void setupView() {
        mapViewer = setupMapViewer();

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setBorder(BorderFactory.createEmptyBorder(SIDEBAR_PADDING, SIDEBAR_PADDING, SIDEBAR_PADDING, SIDEBAR_PADDING));
        sidebar.setPreferredSize(new Dimension(180, 0));

        // Title label
        JLabel titleLabel = new JLabel("EventHive", JLabel.CENTER);
        titleLabel.setFont(getFontWithFallback("Inter", Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setForeground(TITLE_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setMaximumSize(new Dimension(180, 40));

        sidebar.add(titleLabel);
        sidebar.add(Box.createVerticalStrut(SIDEBAR_SPACING));

        // Add buttons with spacing
        addSidebarButton(sidebar, logOutButton);
        addSidebarButton(sidebar, createEventButton);
        addSidebarButton(sidebar, filterButton);
        addSidebarButton(sidebar, viewRSVPButton);
        addSidebarButton(sidebar, viewCreatedButton);
        addSidebarButton(sidebar, modifyEventButton);

        add(sidebar, BorderLayout.WEST);

        // Overlay zoom buttons at top-right of map
        int zoomButtonSize = 64; // Slightly larger for better visibility
        int overlayPanelWidth = zoomButtonSize;
        int overlayPanelHeight = zoomButtonSize * 2 + 16; // 16px spacing

        // Style zoomInButton
        zoomInButton.setPreferredSize(new Dimension(zoomButtonSize, zoomButtonSize));
        zoomInButton.setMaximumSize(new Dimension(zoomButtonSize, zoomButtonSize));
        zoomInButton.setMinimumSize(new Dimension(zoomButtonSize, zoomButtonSize));
        zoomInButton.setFont(getFontWithFallback("Inter", Font.BOLD, 32));
        zoomInButton.setBackground(BUTTON_BG);
        zoomInButton.setForeground(BUTTON_BORDER);

        // Style zoomOutButton
        zoomOutButton.setPreferredSize(new Dimension(zoomButtonSize, zoomButtonSize));
        zoomOutButton.setMaximumSize(new Dimension(zoomButtonSize, zoomButtonSize));
        zoomOutButton.setMinimumSize(new Dimension(zoomButtonSize, zoomButtonSize));
        zoomOutButton.setFont(getFontWithFallback("Inter", Font.BOLD, 32));
        zoomOutButton.setBackground(BUTTON_BG);
        zoomOutButton.setForeground(BUTTON_BORDER);

        JPanel overlayPanel = new JPanel();
        overlayPanel.setLayout(new BoxLayout(overlayPanel, BoxLayout.Y_AXIS));
        overlayPanel.setOpaque(false);
        overlayPanel.setSize(overlayPanelWidth, overlayPanelHeight);

        overlayPanel.add(zoomInButton);
        overlayPanel.add(Box.createVerticalStrut(16));
        overlayPanel.add(zoomOutButton);

        // Use a JLayeredPane to overlay the zoom buttons on the map
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        mapViewer.setBounds(0, 0, 1000, 700);
        int rightMargin = 20;

        overlayPanel.setBounds(mapViewer.getWidth() - overlayPanelWidth - rightMargin, 16, overlayPanelWidth, overlayPanelHeight);

        layeredPane.add(mapViewer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);

        // Listen for resize to reposition overlay
        layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                mapViewer.setSize(layeredPane.getSize());
                int x = layeredPane.getWidth() - overlayPanel.getWidth() - 16;
                int y = 16;
                overlayPanel.setLocation(Math.max(x, 0), y);
            }
        });

        sidebar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 2, BUTTON_BORDER), // no top border
                BorderFactory.createEmptyBorder(SIDEBAR_PADDING, SIDEBAR_PADDING, SIDEBAR_PADDING, SIDEBAR_PADDING)
        ));
        add(layeredPane, BorderLayout.CENTER);
    }

    private void addSidebarButton(JPanel sidebar, JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(SIDEBAR_SPACING));
    }

    private JXMapViewer setupMapViewer() {
        JXMapViewer mapViewer = MapViewerSingleton.getInstance();

        TileFactoryInfo info = new TileFactoryInfo(
                MIN_ZOOM_LEVEL, MAX_ZOOM_LEVEL, MAX_ZOOM_LEVEL, TILE_SIZE, true, true,
                "https://tile.openstreetmap.org/", "x", "y", "z") {
            @Override
            public String getTileUrl(int x, int y, int zoom) {
                return this.baseURL + (MAX_ZOOM_LEVEL - zoom) + "/" + x + "/" + y + ".png";
            }
        };

        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        GeoPosition uoftCampus = new GeoPosition(GEO_LATITUTE, GEO_LONGITUDE);
        mapViewer.setZoom(1);
        mapViewer.setAddressLocation(uoftCampus);

        Set<DefaultWaypoint> waypoints = new HashSet<>();
        Map<DefaultWaypoint, Color> waypointColors = new HashMap<>();
        Map<DefaultWaypoint, String> waypointTitles = new HashMap<>();

        for (ArrayList<Object> event : events) {
            Color colour = Color.CYAN;
            float latitude = (float) event.get(2);
            float longitude = (float) event.get(3);
            String title = (String) event.get(1);
            ArrayList<String> tags = (ArrayList<String>) event.get(4);

            for (String tag : tags) {
                switch (tag) {
                    case "Gaming":
                        colour = Color.GREEN;
                        break;
                    case "Music":
                        colour = Color.BLACK;
                        break;
                    case "Sports":
                        colour = Color.CYAN;
                        break;
                    case "Art and Culture":
                        colour = Color.ORANGE;
                        break;
                    case "Education":
                        colour = Color.PINK;
                        break;
                    case "Travel":
                        colour = Color.GRAY;
                        break;
                    case "Festival":
                        colour = Color.YELLOW;
                        break;
                }
            }
            DefaultWaypoint waypoint = new DefaultWaypoint(new GeoPosition(latitude, longitude));
            waypoints.add(waypoint);
            waypointColors.put(waypoint, colour);
            waypointTitles.put(waypoint, title);
        }

        WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setRenderer((g, map, waypoint) -> {
            Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());
            Color color = waypointColors.getOrDefault(waypoint, Color.BLACK);
            String title = waypointTitles.getOrDefault(waypoint, "");

            // Draw waypoint marker (filled oval with black border)
            g.setColor(color);
            g.fillOval((int) point.getX() - WAYPOINT_RADIUS, (int) point.getY() - WAYPOINT_RADIUS,
                    2 * WAYPOINT_RADIUS, 2 * WAYPOINT_RADIUS);
            g.setColor(Color.BLACK);
            g.drawOval((int) point.getX() - WAYPOINT_RADIUS, (int) point.getY() - WAYPOINT_RADIUS,
                    2 * WAYPOINT_RADIUS, 2 * WAYPOINT_RADIUS);

            // Draw label with rounded border and background
            if (!title.isEmpty()) {
                Color bg_colour = new Color(241, 248, 250); // Use your desired background color
                FontMetrics fm = g.getFontMetrics();
                int textWidth = fm.stringWidth(title);
                int textHeight = fm.getHeight();
                int arc = 12;
                int paddingX = 8;
                int paddingY = 4;
                int labelX = (int) point.getX() + WAYPOINT_RADIUS + 6;
                int labelY = (int) point.getY() - textHeight / 2 - paddingY;

                // Background
                g.setColor(bg_colour);
                g.fillRoundRect(labelX, labelY, textWidth + 2 * paddingX, textHeight + 2 * paddingY, arc, arc);

                // Border
                g.setColor(Color.GRAY);
                g.drawRoundRect(labelX, labelY, textWidth + 2 * paddingX, textHeight + 2 * paddingY, arc, arc);

                // Text
                g.setColor(Color.BLACK);
                g.drawString(title, labelX + paddingX, labelY + fm.getAscent() + paddingY);
            }
        });

        waypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(waypointPainter);

        mapViewer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                GeoPosition clickedGeoPosition = mapViewer.convertPointToGeoPosition(e.getPoint());
                for (DefaultWaypoint waypoint : waypoints) {
                    GeoPosition wpPosition = waypoint.getPosition();
                    double distance = calculateDistance(clickedGeoPosition, wpPosition);
                    if (distance < (20 * mapViewer.getZoom())) {
                        String title = waypointTitles.get(waypoint);
                        viewEvent(title);
                    }
                }
            }
        });

        setupDragFunctionality(mapViewer);
        return mapViewer;
    }

    private double calculateDistance(GeoPosition pos1, GeoPosition pos2) {
        double lat1 = Math.toRadians(pos1.getLatitude());
        double lon1 = Math.toRadians(pos1.getLongitude());
        double lat2 = Math.toRadians(pos2.getLatitude());
        double lon2 = Math.toRadians(pos2.getLongitude());
        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    private void setupDragFunctionality(JXMapViewer mapViewer) {
        final Point[] lastMousePosition = {null};
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMousePosition[0] = e.getPoint();
            }
        });
        mapViewer.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mapViewer.getZoom() <= LOW_ZOOM_THRESHOLD) {
                    ZOOM_LEVEL = ZOOM_THRESHOLD_LOW;
                } else if (mapViewer.getZoom() <= MEDIUM_ZOOM_THRESHOLD) {
                    ZOOM_LEVEL = ZOOM_THRESHOLD_MEDIUM;
                } else if (mapViewer.getZoom() <= HIGH_ZOOM_THRESHOLD) {
                    ZOOM_LEVEL = ZOOM_THRESHOLD_HIGH;
                } else {
                    ZOOM_LEVEL = ZOOM_MULTIPLIER * mapViewer.getZoom();
                }
                if (lastMousePosition[0] != null) {
                    Point currentMousePosition = e.getPoint();
                    int deltaX = currentMousePosition.x - lastMousePosition[0].x;
                    int deltaY = currentMousePosition.y - lastMousePosition[0].y;
                    GeoPosition currentPosition = mapViewer.getAddressLocation();
                    double longitudeDelta = -deltaX * ZOOM_LEVEL;
                    double latitudeDelta = deltaY * ZOOM_LEVEL;
                    GeoPosition newPosition = new GeoPosition(
                            currentPosition.getLatitude() + latitudeDelta,
                            currentPosition.getLongitude() + longitudeDelta
                    );
                    mapViewer.setAddressLocation(newPosition);
                    lastMousePosition[0] = currentMousePosition;
                }
            }
        });
    }

    private void viewEvent(String title) {
        viewEventController.execute(title);
    }

    private void handleZoomInAction() {
        int currentZoom = mapViewer.getZoom();
        if (currentZoom > 1) {
            mapViewer.setZoom(currentZoom - 1);
        }
    }

    private void handleZoomOutAction() {
        int currentZoom = mapViewer.getZoom();
        if (currentZoom < MAX_ZOOM_LEVEL) {
            mapViewer.setZoom(currentZoom + 1);
        }
    }

    private void handleEventAction() {
        homeController.switchToCreateEventView();
    }

    private void handleLogoutAction() {
        homeController.switchToLoginView();
    }

    private void handleFilterAction() {
        homeController.switchToFilterEventView();
    }

    private void handleViewRSVPAction() {
        homeController.switchToViewRSVPView();
    }

    private void handleViewCreatedEvents() {
        homeController.switchToViewCreatedEventsView();
    }

    private void handleViewModifyEvent() {
        homeController.switchToModifyEventView();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // No property change logic needed
    }

    private JButton createStyledButton(String text, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Draw background
                g2.setColor(getModel().isRollover() ? BUTTON_BG_HOVER : BUTTON_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                // Draw lighter border
                g2.setColor(BUTTON_BORDER);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 18, 18);
                // Draw text/icon
                super.paintComponent(g);
                g2.dispose();
            }
        };
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setFont(getFontWithFallback("Inter", Font.BOLD, BUTTON_FONT_SIZE));
        button.setForeground(BUTTON_BORDER); // lighter text color
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(actionListener);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { button.repaint(); }
            public void mouseExited(MouseEvent evt) { button.repaint(); }
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

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setHomeController(HomeController controller) {
        this.homeController = controller;
    }

    public void setViewEventController(ViewEventController controller) {
        this.viewEventController = controller;
    }
}