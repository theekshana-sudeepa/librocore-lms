/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.lms.lmsystem.component;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPasswordField;

/**
 *
 * @author Theekshana Sudeepa
 */
public class RoundPasswordField extends JPasswordField {

    public RoundPasswordField() {
        init();
    }

    private void init() {
        this.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + " margin:0,10,0,10;"
                + "showRevealButton:true;"
                + "showCapsLock:true"); //TOP, LEFT, BOTTOM, RIGHT
    }

}
