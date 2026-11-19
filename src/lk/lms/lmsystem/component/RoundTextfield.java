package lk.lms.lmsystem.component;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JTextField;

public class RoundTextfield extends JTextField {

    public RoundTextfield() {
        init();
    }

    private void init() {
        this.putClientProperty(FlatClientProperties.STYLE, ""
                +"arc:20;"
                + " margin:0,10,0,10"); //TOP, LEFT, BOTTOM, RIGHT
    }
}
