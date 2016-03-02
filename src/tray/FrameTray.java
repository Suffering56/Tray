package tray;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.Timer;

public class FrameTray extends JFrame {

    TrayIcon trayIcon;
    SystemTray tray;

    public FrameTray() {
        setTitle("Tray");
        setIconImage(Toolkit.getDefaultToolkit().getImage("icon_x64.png"));
        setVisible(true);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
//        try {
//            System.out.println("setting look and feel");
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            System.out.println("Unable to set LookAndFeel");
//        }

        if (SystemTray.isSupported()) {
            System.out.println("system tray supported");
            tray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().getImage("icon_x64.png");
            ActionListener exitListener = (ActionEvent e) -> {
                System.out.println("Exiting....");
                System.exit(0);
            };
            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);

            defaultItem = new MenuItem("Open");
            defaultItem.addActionListener((ActionEvent e) -> {
                setVisible(true);
                setExtendedState(JFrame.NORMAL);
            });
            popup.add(defaultItem);

            trayIcon = new TrayIcon(image, "SystemTray Demo", popup);
            trayIcon.setImageAutoSize(true);

            Image image2 = Toolkit.getDefaultToolkit().getImage("duke256.png");

            timer = new Timer(2000, (ActionEvent e) -> {
                counter++;
                if (counter % 2 == 0) {
                    trayIcon.setImage(image2);
                } else {
                    trayIcon.setImage(image);
                }
            });
            timer.start();

        } else {
            System.out.println("system tray not supported");
        }
        addWindowStateListener((WindowEvent e) -> {
            if (e.getNewState() == ICONIFIED) {
                try {
                    tray.add(trayIcon);
                    setVisible(false);
                    System.out.println("added to SystemTray");
                } catch (AWTException ex) {
                    System.out.println("unable to add to tray");
                }
            }
            if (e.getNewState() == 7) {
                try {
                    tray.add(trayIcon);
                    setVisible(false);
                    System.out.println("added to SystemTray");
                } catch (AWTException ex) {
                    System.out.println("unable to add to system tray");
                }
            }
            if (e.getNewState() == MAXIMIZED_BOTH) {
                tray.remove(trayIcon);
                setVisible(true);
                System.out.println("Tray icon removed");
            }
            if (e.getNewState() == NORMAL) {
                tray.remove(trayIcon);
                setVisible(true);
                System.out.println("Tray icon removed");
            }
        });
    }

    private int counter = 0;
    private Timer timer;
}
