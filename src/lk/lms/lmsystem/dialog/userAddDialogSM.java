/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package lk.lms.lmsystem.dialog;

import com.toedter.calendar.JDateChooser;
import java.io.File;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import lk.lms.lmsystem.connection.MySQL;
import lk.lms.lmsystem.validation.Validation;
import raven.toast.Notifications;
import java.sql.Connection;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JComboBox;
import lk.lms.lmsystem.component.RoundButton;
import lk.lms.lmsystem.component.RoundTextfield;
import lk.lms.lmsystem.panel.SuperAdminUserPanel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.engine.JasperExportManager;

/**
 *
 * @author Theekshana Sudeepa
 */
public class userAddDialogSM extends javax.swing.JDialog {

    private final SuperAdminUserPanel superAdminUserPanel;

    /**
     * Creates new form BookAdd
     *
     * @param parent
     * @param modal
     */
    public static HashMap<String, Integer> userGenderMap = new HashMap();
    public static HashMap<String, Integer> userStatusMap = new HashMap();
    public static HashMap<String, Integer> ProvinceMap = new HashMap();
    public static HashMap<String, Integer> districtMap = new HashMap();
    public static HashMap<String, Integer> cityMap = new HashMap();

    private Connection connection;

    public userAddDialogSM(java.awt.Frame parent, boolean modal, SuperAdminUserPanel panel) {
        super(parent, modal);
        this.superAdminUserPanel = panel;
        initComponents();
        loadGender();
        loadStatus();
        loadProvince();
        loadDistrict();
        loadCity();
        Notifications.getInstance().setJFrame((JFrame) parent);

    }

//    private void loadCities() {
//        try {
//            cityComboBox.removeAllItems();
//            String selectedDistrict = (String) districtComboBox.getSelectedItem();
//            PreparedStatement statement = connection.prepareStatement(
//                    "SELECT cities.name FROM cities JOIN districts ON cities.district_id = districts.id WHERE districts.name = ?");
//            statement.setString(1, selectedDistrict);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                cityComboBox.addItem(resultSet.getString("name"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    private void getselecteddistrict() {
//
//        int provinceid = provincecombo.getSelectedIndex();
//        if (provinceid < 1) {
//            try {
//                ResultSet districtfilterdata = MySQL.search("SELECT `id`,`name` FROM `district` WHERE `province_id`='1'");
//                Vector<String> v = new Vector();
//                v.add("Select District");
//                while (districtfilterdata.next()) {
//                    v.add(districtfilterdata.getString("name"));
//                    districtfilteredMap.put(districtfilterdata.getString("name"), districtfilterdata.getInt("id"));
//                }
//                DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(v);
//                districtcombo.setModel(comboBoxModel);
//            } catch (Exception e) {
//                throw new RuntimeException("District loading failed");
//            }
//        }
//
//    }
//    private void disablecombo() {
//        districtcombo.setEnabled(false);
//        citycombo.setEnabled(false);
//    }
    private void loadGender() {
        try {

            ResultSet resultSet = MySQL.search("SELECT * FROM `gender`");

            Vector v = new Vector();
            v.add("Select Gender");
            while (resultSet.next()) {
                v.add(resultSet.getString("name"));
                userGenderMap.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            gendercombo.setModel(model);

        } catch (Exception e) {
            //System.out.println("Connection Problem");
            e.printStackTrace();
        }
    }

    private void loadStatus() {
        try {

            ResultSet resultSet = MySQL.search("SELECT * FROM `status`");

            Vector v = new Vector();
            v.add("Select Status");
            while (resultSet.next()) {
                v.add(resultSet.getString("name"));
                userStatusMap.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            statuscombo.setModel(model);

        } catch (Exception e) {
            //System.out.println("Connection Problem");
            e.printStackTrace();
        }
    }

    private void loadProvince() {
        try {

            ResultSet resultprovince = MySQL.search("SELECT * FROM `province`");

            Vector v = new Vector();
            v.add("Select Province");
            while (resultprovince.next()) {
                v.add(resultprovince.getString("name"));
                ProvinceMap.put(resultprovince.getString("name"), resultprovince.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            provincecombo.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDistrict() {
        try {

            ResultSet resultprovince = MySQL.search("SELECT * FROM `district`");

            Vector v = new Vector();
            v.add("Select District");
            while (resultprovince.next()) {
                v.add(resultprovince.getString("name"));
                districtMap.put(resultprovince.getString("name"), resultprovince.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            districtcombo.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCity() {
        try {

            ResultSet resultprovince = MySQL.search("SELECT * FROM `city`");

            Vector v = new Vector();
            v.add("Select District");
            while (resultprovince.next()) {
                v.add(resultprovince.getString("name"));
                cityMap.put(resultprovince.getString("name"), resultprovince.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(v);
            citycombo.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        AddUserBtn = new lk.lms.lmsystem.component.RoundButton();
        UpdateUserBtn = new lk.lms.lmsystem.component.RoundButton();
        jLabel2 = new javax.swing.JLabel();
        nicnofield = new lk.lms.lmsystem.component.RoundTextfield();
        jLabel3 = new javax.swing.JLabel();
        fnamefield = new lk.lms.lmsystem.component.RoundTextfield();
        jLabel4 = new javax.swing.JLabel();
        lnamefield = new lk.lms.lmsystem.component.RoundTextfield();
        jLabel5 = new javax.swing.JLabel();
        emailfield = new lk.lms.lmsystem.component.RoundTextfield();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        gendercombo = new javax.swing.JComboBox<>();
        statuscombo = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        line1field = new lk.lms.lmsystem.component.RoundTextfield();
        line2field = new lk.lms.lmsystem.component.RoundTextfield();
        jLabel10 = new javax.swing.JLabel();
        pcodefield = new lk.lms.lmsystem.component.RoundTextfield();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        provincecombo = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        districtcombo = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        citycombo = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        mob1field = new lk.lms.lmsystem.component.RoundTextfield();
        mob2field = new lk.lms.lmsystem.component.RoundTextfield();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Montserrat", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Add / Update User");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        AddUserBtn.setBackground(new java.awt.Color(0, 153, 51));
        AddUserBtn.setText("Add User");
        AddUserBtn.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        AddUserBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddUserBtnActionPerformed(evt);
            }
        });

        UpdateUserBtn.setBackground(new java.awt.Color(0, 153, 204));
        UpdateUserBtn.setText("Update User");
        UpdateUserBtn.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        UpdateUserBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateUserBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel2.setText("NIC Number");

        nicnofield.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel3.setText("First Name");

        fnamefield.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel4.setText("Last Name");

        lnamefield.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel5.setText("Email Address");

        emailfield.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel6.setText("Date of Birth");

        jDateChooser1.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel7.setText("Gender");

        jLabel8.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel8.setText("Status");

        gendercombo.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N

        statuscombo.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel9.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel9.setText("Address Lane 01");

        line1field.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N

        line2field.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        line2field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                line2fieldActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel10.setText("Address Lane 02 (Optional)");

        pcodefield.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        pcodefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pcodefieldActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel11.setText("Postal Code");

        jLabel12.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel12.setText("Province");

        provincecombo.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel13.setText("District");

        districtcombo.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel14.setText("City");

        citycombo.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel15.setText("Mobile Number ");

        mob1field.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N

        mob2field.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        jLabel16.setText("Mobile Number 2 (Optional)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(726, 726, 726))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap(9, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(gendercombo, 0, 300, Short.MAX_VALUE)
                                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(statuscombo, 0, 299, Short.MAX_VALUE)
                                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(mob1field, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(mob2field, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(nicnofield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(305, 305, 305))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(emailfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(fnamefield, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lnamefield, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(provincecombo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(districtcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pcodefield, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(line2field, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(citycombo, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(line1field, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(UpdateUserBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AddUserBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)))
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fnamefield, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lnamefield, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nicnofield, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(emailfield, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mob1field, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mob2field, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(gendercombo, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(statuscombo)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(provincecombo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(districtcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(citycombo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(line1field, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(line2field, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pcodefield, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UpdateUserBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddUserBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void AddUserBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddUserBtnActionPerformed

        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String nicno = nicnofield.getText();
        String email = emailfield.getText();
        String fname = fnamefield.getText();
        String lname = lnamefield.getText();
        String mobile1 = mob1field.getText();
        String mobile2 = mob2field.getText();

        int gendercombo = this.gendercombo.getSelectedIndex();
        int statuscombo = this.statuscombo.getSelectedIndex();
        String addline1 = line1field.getText();
        String addline2 = line2field.getText();
        String pcode = pcodefield.getText();

        int provincecombo = this.provincecombo.getSelectedIndex();
        int districtcombo = this.districtcombo.getSelectedIndex();
        int citycombo = this.citycombo.getSelectedIndex();

        if (nicValidate(nicno)) {
            return;
        }
        if (emailValidate(email)) {
            return;
        }
        if (fnameValidation(fname)) {
            return;
        }
        if (lnameValidation(lname)) {
            return;
        }
        if (mobileValidation(mobile1)) {
            return;
        }
        if (!mobile2.isEmpty()) {
            if (mobileValidation(mobile2)) {
                return;
            }
        } else {

        }
        if (jDateChooser1 != null) {
            Date dobirth = jDateChooser1.getDate();
            if (dobirth != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String strdob = format.format(dobirth);

                // rest of your code
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Select Date of Birth");
                jDateChooser1.grabFocus();
                return;
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String strdob = format.format(dobirth);

            if (gendercombo < 1) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Select Gender");
                return;
            }
            if (statuscombo < 1) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Select Status");
                return;
            }

            if (addressValidation(addline1)) {
                return;
            }
            if (!addline2.isEmpty()) {
                if (addressValidation(addline2)) {
                    return;
                }
            } else {

            }
            if (postalcodeValidate(pcode)) {
                return;
            }
            if (provincecombo < 1) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Select Province");
                return;
            }
            if (districtcombo < 1) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Select District");
                return;
            }
            if (citycombo < 1) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Select City");
                return;
            }

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("UserName", fname + " " + lname);
            parameters.put("NICNum", nicno);

            try {
                String path = "/reports/LMSUserBCode.jasper";
                InputStream reportStream = getClass().getResourceAsStream(path);
                if (reportStream == null) {
                    throw new FileNotFoundException("Report file not found: " + path);
                }

                // Data source (if any, here using a bean collection data source)
                JREmptyDataSource dataSource = new JREmptyDataSource();

                //fill report with data
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, dataSource);

                //Export report to pdf
                String outputFolder = "src/userqrcodes/";
                String outputFile = "user" + nicno + ".pdf";
                File outputfile = new File(outputFolder, outputFile);
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputfile.getAbsolutePath());

                Notifications
                        .getInstance()
                        .show(Notifications.Type.SUCCESS,
                                Notifications.Location.TOP_RIGHT, "PDF saved Successfully.");

                //view report
                JFrame frame = new JFrame("Report");
                frame.getContentPane().add(new JRViewer(jasperPrint));
                frame.pack();
                frame.setVisible(true);

                try {
                    synchronized (this) {
                        ResultSet result = MySQL.search("SELECT `nic_no` FROM `user` WHERE `user`.`nic_no`='" + nicno + "'");
                        if (result.next()) {
                            Notifications
                                    .getInstance()
                                    .show(Notifications.Type.WARNING,
                                            Notifications.Location.TOP_RIGHT, "This User already exists!");
                        } else {

                            try {
                                String joinedDate = dformat.format(new Date());
                                MySQL.iud("INSERT INTO `user`(`nic_no`,`fName`,`lName`,`email`,`dob`,`gender_id`,`barcode_path`,`joined_date`,`user_type_id`,`status_id`)"
                                        + " VALUES ('" + nicno + "','" + fname + "','" + lname + "','" + email + "','" + strdob + "','" + gendercombo + "',"
                                        + "'" + outputFile + "','" + joinedDate + "','1','" + statuscombo + "')"
                                );
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                MySQL.iud("INSERT INTO `contact_number`(`pNumber01`,`pNumber02`,`user_nic_no`) VALUES ('" + mobile1 + "','" + mobile2 + "','" + nicno + "')");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                MySQL.iud("INSERT INTO `address`(`lane_1`,`lane_2`,`postal_code`,`city_id`,`district_id`,`province_id`,`user_nic_no`) VALUES ('" + addline1 + "','" + addline2 + "','" + pcode + "','" + citycombo + "','" + districtcombo + "','" + provincecombo + "','" + nicno + "')");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Notifications
                                    .getInstance()
                                    .show(Notifications.Type.SUCCESS,
                                            Notifications.Location.TOP_RIGHT, "Added User Detailed Successfully.");

                        }
                    }
                } catch (SQLException se) {
                    throw new RuntimeException(se);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // handle the null case
        }


    }//GEN-LAST:event_AddUserBtnActionPerformed

    private void UpdateUserBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateUserBtnActionPerformed
        // TODO add your handling code here:
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String unicno = nicnofield.getText();
        String uemail = emailfield.getText();
        String ufname = fnamefield.getText();
        String ulname = lnamefield.getText();
        String umobile1 = mob1field.getText();
        String umobile2 = mob2field.getText();

        int ugendercombo = this.gendercombo.getSelectedIndex();
        int ustatuscombo = this.statuscombo.getSelectedIndex();
        String uaddline1 = line1field.getText();
        String uaddline2 = line2field.getText();
        String upcode = pcodefield.getText();

        int uprovincecombo = this.provincecombo.getSelectedIndex();
        int udistrictcombo = this.districtcombo.getSelectedIndex();
        int ucitycombo = this.citycombo.getSelectedIndex();

        if (nicValidate(unicno)) {
            return;
        }
        if (emailValidate(uemail)) {
            return;
        }
        if (fnameValidation(ufname)) {
            return;
        }
        if (lnameValidation(ulname)) {
            return;
        }
        if (mobileValidation(umobile1)) {
            return;
        }
        if (!umobile2.isEmpty()) {
            if (mobileValidation(umobile2)) {
                return;
            }
        } else {

        }
        if (jDateChooser1 != null) {
            Date dobirth = jDateChooser1.getDate();
            if (dobirth != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String ustrdob = format.format(dobirth);

                // rest of your code
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Select Date of Birth");
                jDateChooser1.grabFocus();
                return;
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String ustrdob = format.format(dobirth);

            if (ugendercombo < 1) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Select Gender");
                return;
            }
            if (ustatuscombo < 1) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Select Status");
                return;
            }

            if (addressValidation(uaddline1)) {
                return;
            }
            if (!uaddline2.isEmpty()) {
                if (addressValidation(uaddline2)) {
                    return;
                }
            } else {

            }
            if (postalcodeValidate(upcode)) {
                return;
            }
            if (uprovincecombo < 1) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Select Province");
                return;
            }
            if (udistrictcombo < 1) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Select District");
                return;
            }
            if (ucitycombo < 1) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Select City");
                return;
            }

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("UserName", ufname + " " + ulname);
            parameters.put("NICNum", unicno);

            try {
                String path = "/reports/LMSUserBCode.jasper";
                InputStream reportStream = getClass().getResourceAsStream(path);
                if (reportStream == null) {
                    throw new FileNotFoundException("Report file not found: " + path);
                }

                // Data source (if any, here using a bean collection data source)
                JREmptyDataSource dataSource = new JREmptyDataSource();

                //fill report with data
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, dataSource);

                //Export report to pdf
                String outputFolder = "src/userqrcodes/";
                String outputFile = "user" + unicno + ".pdf";
                File outputfile = new File(outputFolder, outputFile);
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputfile.getAbsolutePath());

                Notifications
                        .getInstance()
                        .show(Notifications.Type.SUCCESS,
                                Notifications.Location.TOP_RIGHT, "PDF saved Successfully.");

                //view report
                JFrame frame = new JFrame("Report");
                frame.getContentPane().add(new JRViewer(jasperPrint));
                frame.pack();
                frame.setVisible(true);

                try {
                    synchronized (this) {
                        ResultSet result = MySQL.search("SELECT `nic_no` FROM `user` WHERE `user`.`nic_no`='" + unicno + "'");
                        if (result.next()) {
                            Notifications
                                    .getInstance()
                                    .show(Notifications.Type.WARNING,
                                            Notifications.Location.TOP_RIGHT, "This User already exists!");
                        } else {

//                           
                            try {
                                String joinedDate = dformat.format(new Date());
                                MySQL.iud("UPDATE `user` SET `nic_no`= '" + unicno + "' , `fName`='" + ufname + "',`lName`='" + ulname + "',`email`='" + uemail + "',`dob`='" + ustrdob + "',`gender_id`= '" + ugendercombo + "',"
                                        + "`barcode_path`='" + outputFile + "',`joined_date`='" + joinedDate + "',`user_type_id`='1',`status_id`='" + ustatuscombo + "' WHERE `user`.`email`='" + uemail + "'"
                                );
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                MySQL.iud("UPDATE `contact_number` SET `pNumber01` = '" + umobile1 + "' ,`pNumber02` = '" + umobile2 + "' ,`user_nic_no`= '" + unicno + "' WHERE `user`.`email`='" + uemail + "'");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                MySQL.iud("UPDATE `address` SET `lane_1`= '" + uaddline1 + "',`lane_2`='" + uaddline2 + "' ,`postal_code`= '" + upcode + "',`city_id`= '" + ucitycombo + "',`district_id`= '" + udistrictcombo + "',`province_id`= '" + uprovincecombo + "',`user_nic_no`= '" + unicno + "' WHERE `user`.`email`='" + uemail + "' ");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Notifications
                                    .getInstance()
                                    .show(Notifications.Type.SUCCESS,
                                            Notifications.Location.TOP_RIGHT, "Added User Detailed Successfully.");

                        }
                    }
                } catch (SQLException se) {
                    throw new RuntimeException(se);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // handle the null case
        }

    }//GEN-LAST:event_UpdateUserBtnActionPerformed

    private void line2fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_line2fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_line2fieldActionPerformed

    private void pcodefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pcodefieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pcodefieldActionPerformed

    private boolean fnameValidation(String value) {
        if (value.isEmpty()) {
            Notifications
                    .getInstance()
                    .show(Notifications.Type.WARNING,
                            Notifications.Location.TOP_RIGHT, "First name is required");
            return true;
      } else if (!value.matches("^[a-zA-Z\\s]+$")) {
            Notifications
                    .getInstance()
                    .show(Notifications.Type.WARNING,
                            Notifications.Location.TOP_RIGHT, "Only Letters Allowed In First Name.");
            return true;
        } else {
            return false;
        }
    }

    private boolean lnameValidation(String value) {
        if (value.isEmpty()) {
            Notifications
                    .getInstance()
                    .show(Notifications.Type.WARNING,
                            Notifications.Location.TOP_RIGHT, "Last name is required");
            return true;
        } else if (!value.matches("^[a-zA-Z\\s]+$")) {
            Notifications
                    .getInstance()
                    .show(Notifications.Type.WARNING,
                            Notifications.Location.TOP_RIGHT, "Only Letters Allowed In Last Name.");
            return true;
        } else {
            return false;
        }
    }

    private boolean emailValidate(String value) {
        if (value.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT,
                    "Email is required");
            return true;
        } else if (!value.matches(Validation.EMAIL_VALIDATION.validate())) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT,
                    "Provide valid Email!");
            return true;
        }
        return false;
    }

    private boolean nicValidate(String value) {
        if (value.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT,
                    "NIC is required.");
            return true;
        } else if (!value.matches(Validation.NICNUMBER_VALIDATION.validate())) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT,
                    "NIC Number Not Valid!");
            return true;
        }
        return false;
    }

    private boolean postalcodeValidate(String value) {
        if (value.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT,
                    "Postal Code is required.");
            return true;
        } else if (!value.matches(Validation.POSTALCODE_VALIDATION.validate())) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT,
                    "Postal Code Not Valid!");
            return true;
        }
        return false;
    }

    private boolean mobileValidation(String mobile) {
        if (mobile.isEmpty()) {
            Notifications
                    .getInstance()
                    .show(Notifications.Type.WARNING,
                            Notifications.Location.TOP_RIGHT, "Mobile Number is required");
            return true;
        } else if (mobile.length() != 10) {
            Notifications
                    .getInstance()
                    .show(Notifications.Type.WARNING,
                            Notifications.Location.TOP_RIGHT, "Mobile Number should contain 10 numbers");
            return true;
        } else if (!mobile.matches(Validation.MOBILE_VALIDATION.validate())) {
            Notifications
                    .getInstance()
                    .show(Notifications.Type.WARNING,
                            Notifications.Location.TOP_RIGHT,
                            "Provide valid Mobile Number");
            return true;
        }
        return false;
    }

    private boolean dobValidation(String value) {
        if (value.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT,
                    "Date of Birth is required");
            return true;
        }
        return false;
    }

    private boolean addressValidation(String value) {
        if (value.isEmpty()) {
            Notifications
                    .getInstance()
                    .show(Notifications.Type.WARNING,
                            Notifications.Location.TOP_RIGHT, "Address is required");
            return true;
        }
        return false;
    }

    public RoundButton getaddButton() {
        return AddUserBtn;
    }

    public RoundButton getUserButton() {
        return UpdateUserBtn;
    }

    public RoundTextfield getuserfNamefield() {
        return fnamefield;
    }

    public RoundTextfield getuserlNamefield() {
        return lnamefield;
    }

    public RoundTextfield getUserNICfield() {
        return nicnofield;
    }

    public RoundTextfield getUserEmailfield() {
        return emailfield;
    }

    public RoundTextfield getUserMobileOnefield() {
        return mob1field;
    }

    public RoundTextfield getUserMobileTwofield() {
        return mob2field;
    }

    public JDateChooser getUserDOBhooser() {
        return jDateChooser1;
    }

    public JComboBox<String> getUserGendercombo() {
        return gendercombo;
    }

    public JComboBox<String> getUserStatuscombo() {
        return statuscombo;
    }

    public JComboBox<String> getUserProvincecombo() {
        return provincecombo;
    }

    public JComboBox<String> getUserDistrictcombo() {
        return districtcombo;
    }

    public JComboBox<String> getUserCitycombo() {
        return citycombo;
    }
    
       public RoundTextfield getUserLineONefield() {
        return line1field;
    }

    public RoundTextfield getUserLineTwofield() {
        return line2field;
    }

    public RoundTextfield getUserPostCodefield() {
        return pcodefield;
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                userAddDialogSM dialog = new userAddDialogSM(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private lk.lms.lmsystem.component.RoundButton AddUserBtn;
    private lk.lms.lmsystem.component.RoundButton UpdateUserBtn;
    private javax.swing.JComboBox<String> citycombo;
    private javax.swing.JComboBox<String> districtcombo;
    private lk.lms.lmsystem.component.RoundTextfield emailfield;
    private lk.lms.lmsystem.component.RoundTextfield fnamefield;
    private javax.swing.JComboBox<String> gendercombo;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private lk.lms.lmsystem.component.RoundTextfield line1field;
    private lk.lms.lmsystem.component.RoundTextfield line2field;
    private lk.lms.lmsystem.component.RoundTextfield lnamefield;
    private lk.lms.lmsystem.component.RoundTextfield mob1field;
    private lk.lms.lmsystem.component.RoundTextfield mob2field;
    private lk.lms.lmsystem.component.RoundTextfield nicnofield;
    private lk.lms.lmsystem.component.RoundTextfield pcodefield;
    private javax.swing.JComboBox<String> provincecombo;
    private javax.swing.JComboBox<String> statuscombo;
    // End of variables declaration//GEN-END:variables
}
