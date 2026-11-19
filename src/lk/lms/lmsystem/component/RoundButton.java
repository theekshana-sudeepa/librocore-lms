package lk.lms.lmsystem.component;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;


public class RoundButton extends JButton {

    public RoundButton() {
        init();
//        paint(g);
    }

    private void init() {
        this.putClientProperty(FlatClientProperties.STYLE, "arc:999;");
    }
    
//    @Override
//    public void paint(Graphics g){
//         Graphics2D g2 = (Graphics2D) g.create();
//    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0));
//    super.paint(g2);
//    g2.dispose();
//    }
    
}
