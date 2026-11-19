package test;

import lk.lms.lmsystem.gui.Signin;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class CaptureUI2 {
    public static void main(String[] args) {
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
            SwingUtilities.invokeAndWait(() -> {
                try {
                    Signin signin = new Signin();
                    signin.addNotify();
                    signin.validate();
                    signin.setVisible(true);
                    
                    // Give it a split second to render
                    Thread.sleep(300);
                    
                    Dimension d = signin.getSize();
                    if (d.width <= 0) d = new Dimension(950, 550);
                    
                    BufferedImage img = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = img.createGraphics();
                    signin.printAll(g2);
                    g2.dispose();

                    File out = new File("test_signin2.png");
                    ImageIO.write(img, "png", out);
                    System.out.println("Captured test_signin2: " + out.length() + " bytes, dim: " + d);
                    signin.dispose();
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
