package test;

import lk.lms.lmsystem.gui.Signin;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class CaptureUI {
    public static void main(String[] args) {
        try {
            // Apply Look & Feel if available
            try {
                com.formdev.flatlaf.FlatLightLaf.setup();
            } catch (Exception e) {}

            SwingUtilities.invokeAndWait(() -> {
                try {
                    Signin signin = new Signin();
                    signin.setSize(950, 550);
                    signin.doLayout();
                    signin.validate();

                    BufferedImage img = new BufferedImage(950, 550, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2 = img.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    signin.paint(g2);
                    g2.dispose();

                    File out = new File("test_signin.png");
                    ImageIO.write(img, "png", out);
                    System.out.println("Captured: " + out.getAbsolutePath() + " size: " + out.length());
                    signin.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
