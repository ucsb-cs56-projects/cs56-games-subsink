package edu.ucsb.cs56.projects.games.subsink;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;



public class Screen extends JFrame {
    private World w;
    private List<String> menuItems;

    public void startGame() {
        {
            setTitle("Subsink");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(620, 480);

            w = new World(getWidth(), getHeight());
            add(w);

            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
        }
    }


    public Screen(List<String> list) {
        menuItems = list;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Pane());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public class Pane extends JPanel {

        private String selectedMenuItem;
        private String focusedItem;
        private MenuItemPainter painter;
        private Map<String, Rectangle> menuBounds;

        public Pane() {
            setBackground(Color.CYAN);
            painter = new SimpleMenuItemPainter();
            selectedMenuItem = "None";

            MouseAdapter ma = new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    String newItem = null;
                    for (String text : menuItems) {
                        Rectangle bounds = menuBounds.get(text);
                        if (bounds.contains(e.getPoint())) {
                            newItem = text;
                            break;
                        }
                    }
                    if (newItem != null && !newItem.equals(selectedMenuItem)) {
                        selectedMenuItem = newItem;
                        repaint();
                    }
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    focusedItem = null;
                    for (String text : menuItems) {
                        Rectangle bounds = menuBounds.get(text);
                        if (bounds.contains(e.getPoint())) {
                            focusedItem = text;
                            repaint();
                            break;
                        }
                    }
                }

            };

            addMouseListener(ma);
            addMouseMotionListener(ma);


        }

        @Override
        public void invalidate() {
            menuBounds = null;
            super.invalidate();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(620, 480);
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            if (menuBounds == null) {
                menuBounds = new HashMap<>(menuItems.size());
                int width = 0;
                int height = 0;
                for (String text : menuItems) {
                    Dimension dim = painter.getPreferredSize(g2d, text);
                    width = Math.max(width, dim.width);
                    height = Math.max(height, dim.height);
                }

                int x = (getWidth() - (width + 10)) / 2;

                int totalHeight = (height + 10) * menuItems.size();
                totalHeight += 5 * (menuItems.size() - 1);

                int y = (getHeight() - totalHeight) / 2;

                for (String text : menuItems) {
                    menuBounds.put(text, new Rectangle(x, y, width + 10, height + 10));
                    y += height + 10 + 5;
                }

            }
            for (String text : menuItems) {
                Rectangle bounds = menuBounds.get(text);
                boolean isSelected = text.equals(selectedMenuItem);
                boolean isFocused = text.equals(focusedItem);
                if (selectedMenuItem.equals("Start Game") || selectedMenuItem.equals("Try Again")) {
                    startGame();
                    w.run();
                    setVisible(false);
                    transferFocus();
                    return;

                }
                if (selectedMenuItem.equals("Exit")) {
                    System.exit(1);
                }
                if (selectedMenuItem.equals("High Scores")) {
                    Leaderboard lb = new Leaderboard();
                    lb.paintLeaderboard();
                    dispose();
                    return;
                }
                if (selectedMenuItem.equals("How To Play")) {
                    HowToPlay htp = new HowToPlay();
                    dispose();
                    return;
                }
                painter.paint(g2d, text, bounds, isSelected, isFocused);
            }
            g2d.dispose();
        }


        public abstract class MenuItemPainter {

            public abstract void paint(Graphics2D g2d, String text, Rectangle bounds, boolean isSelected, boolean isFocused);

            public abstract Dimension getPreferredSize(Graphics2D g2d, String text);

        }

        public class SimpleMenuItemPainter extends MenuItemPainter {

            public Dimension getPreferredSize(Graphics2D g2d, String text) {
                return g2d.getFontMetrics().getStringBounds(text, g2d).getBounds().getSize();
            }

            @Override
            public void paint(Graphics2D g2d, String text, Rectangle bounds, boolean isSelected, boolean isFocused) {
                FontMetrics fm = g2d.getFontMetrics();

                if (isSelected) {
                    paintBackground(g2d, bounds, Color.BLUE, Color.WHITE);
                } else if (isFocused) {
                    paintBackground(g2d, bounds, Color.MAGENTA, Color.BLACK);
                } else {
                    paintBackground(g2d, bounds, Color.DARK_GRAY, Color.LIGHT_GRAY);
                }
                int x = bounds.x + ((bounds.width - fm.stringWidth(text)) / 2);
                int y = bounds.y + ((bounds.height - fm.getHeight()) / 2) + fm.getAscent();
                g2d.setColor(isSelected ? Color.WHITE : Color.LIGHT_GRAY);
                g2d.drawString(text, x, y);
            }

            protected void paintBackground(Graphics2D g2d, Rectangle bounds, Color background, Color foreground) {
                g2d.setColor(background);
                g2d.fill(bounds);
                g2d.setColor(foreground);
                g2d.draw(bounds);

            }


        }

    }
}