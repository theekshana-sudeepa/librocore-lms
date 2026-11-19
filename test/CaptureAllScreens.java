package test;

import lk.lms.lmsystem.gui.Signin;
import lk.lms.lmsystem.gui.AdminDashboard;
import lk.lms.lmsystem.gui.SuperAdminDashboard;
import lk.lms.lmsystem.panel.AdminBookPanel;
import lk.lms.lmsystem.panel.AdminUserPanel;
import lk.lms.lmsystem.panel.AssignBookPanel;
import lk.lms.lmsystem.panel.Reports;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;
import javax.imageio.ImageIO;

public class CaptureAllScreens {
    private static final String DIR = "assets/portfolio_screenshots/";

    public static void main(String[] args) {
        new File(DIR).mkdirs();
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
        } catch (Exception e) {}

        try {
            // 1. Signin Screen
            captureFrame("01_Sign_In_Portal.png", () -> {
                Signin signin = new Signin();
                signin.addNotify();
                signin.validate();
                signin.setVisible(true);
                return signin;
            }, 1012, 528);

            // 2. SuperAdmin Dashboard
            captureFrame("02_SuperAdmin_Dashboard.png", () -> {
                SuperAdminDashboard sad = new SuperAdminDashboard();
                sad.setSize(1280, 800);
                sad.validate();
                sad.setVisible(true);
                return sad;
            }, 1280, 800);

            // 3. Admin Book Catalog Panel inside AdminDashboard
            captureFrame("03_Book_Catalog_Management.png", () -> {
                AdminDashboard ad = new AdminDashboard();
                ad.setSize(1280, 800);
                ad.validate();
                ad.setVisible(true);
                try {
                    Method m = AdminDashboard.class.getDeclaredMethod("LoadPanel", JPanel.class);
                    m.setAccessible(true);
                    m.invoke(ad, new AdminBookPanel(ad));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ad;
            }, 1280, 800);

            // 4. Admin User Management Panel inside AdminDashboard
            captureFrame("04_Member_Management.png", () -> {
                AdminDashboard ad = new AdminDashboard();
                ad.setSize(1280, 800);
                ad.validate();
                ad.setVisible(true);
                try {
                    Method m = AdminDashboard.class.getDeclaredMethod("LoadPanel", JPanel.class);
                    m.setAccessible(true);
                    m.invoke(ad, new AdminUserPanel(ad));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ad;
            }, 1280, 800);

            // 5. Circulation Desk (AssignBookPanel) inside AdminDashboard
            captureFrame("05_Circulation_Issue_Desk.png", () -> {
                AdminDashboard ad = new AdminDashboard();
                ad.setSize(1280, 800);
                ad.validate();
                ad.setVisible(true);
                try {
                    Method m = AdminDashboard.class.getDeclaredMethod("LoadPanel", JPanel.class);
                    m.setAccessible(true);
                    m.invoke(ad, new AssignBookPanel());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ad;
            }, 1280, 800);

            // 6. Reports Panel inside SuperAdminDashboard
            captureFrame("06_Reports_Audit_Panel.png", () -> {
                SuperAdminDashboard sad = new SuperAdminDashboard();
                sad.setSize(1280, 800);
                sad.validate();
                sad.setVisible(true);
                try {
                    Method m = SuperAdminDashboard.class.getDeclaredMethod("LoadPanel", JPanel.class);
                    m.setAccessible(true);
                    m.invoke(sad, new Reports(sad));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return sad;
            }, 1280, 800);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("All Swing screens captured successfully!");
        System.exit(0);
    }

    interface WindowSupplier {
        Window get() throws Exception;
    }

    private static void captureFrame(String filename, WindowSupplier supplier, int w, int h) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    Window win = supplier.get();
                    Thread.sleep(700);
                    win.validate();
                    win.repaint();
                    Thread.sleep(300);

                    Dimension d = win.getSize();
                    int width = (d.width > 0) ? d.width : w;
                    int height = (d.height > 0) ? d.height : h;

                    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = img.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    win.printAll(g2);
                    g2.dispose();

                    File out = new File(DIR + filename);
                    ImageIO.write(img, "png", out);
                    System.out.println("Saved " + filename + " (" + out.length() + " bytes)");
                    win.dispose();
                } catch (Exception e) {
                    System.err.println("Error capturing " + filename + ": " + e.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
