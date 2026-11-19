package test;

import lk.lms.lmsystem.gui.AdminDashboard;
import lk.lms.lmsystem.gui.SuperAdminDashboard;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class CaptureDashboards {
    public static void main(String[] args) {
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
            
            // 1. Capture SuperAdmin Dashboard
            SwingUtilities.invokeAndWait(() -> {
                try {
                    SuperAdminDashboard sad = new SuperAdminDashboard();
                    sad.setSize(1280, 800);
                    sad.validate();
                    sad.setVisible(true);
                    
                    Thread.sleep(600);
                    
                    Dimension d = sad.getSize();
                    BufferedImage img = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = img.createGraphics();
                    sad.printAll(g2);
                    g2.dispose();

                    File out = new File("test_superadmin_live.png");
                    ImageIO.write(img, "png", out);
                    System.out.println("Captured SuperAdmin Dashboard: " + out.length() + " bytes, dim: " + d);
                    sad.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // 2. Capture Admin Dashboard
            SwingUtilities.invokeAndWait(() -> {
                try {
                    AdminDashboard ad = new AdminDashboard();
                    ad.setSize(1280, 800);
                    ad.validate();
                    ad.setVisible(true);
                    
                    Thread.sleep(600);
                    
                    Dimension d = ad.getSize();
                    BufferedImage img = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = img.createGraphics();
                    ad.printAll(g2);
                    g2.dispose();

                    File out = new File("test_admin_live.png");
                    ImageIO.write(img, "png", out);
                    System.out.println("Captured Admin Dashboard: " + out.length() + " bytes, dim: " + d);
                    ad.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
