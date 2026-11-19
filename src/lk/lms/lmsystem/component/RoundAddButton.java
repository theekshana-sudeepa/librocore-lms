package lk.lms.lmsystem.component;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Cursor;
import javax.swing.JButton;


public class RoundAddButton extends JButton {

    public RoundAddButton() {
        init();
//        paint(g);
    }

    private void init() {
        this.putClientProperty(FlatClientProperties.STYLE, "arc:16;");
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
//    @Override
//    public void paint(Graphics g){
//         Graphics2D g2 = (Graphics2D) g.create();
//    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0));
//    super.paint(g2);
//    g2.dispose();
//    }
    
}
