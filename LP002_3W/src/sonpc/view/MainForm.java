/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.view;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import sonpc.daos.ArmorDAO;
import sonpc.dtos.ArmorDTO;
import sonpc.formats.DateFormatter;
import sonpc.tablemodel.ArmorTableModel;
import sonpc.validators.ArmorValidator;

/**
 *
 * @author ACER
 */
public class MainForm extends javax.swing.JFrame {

    private String headers[] = {"ID", "Classification", "TimeOfCreate", "Defense"};
    private int indexes[] = {1, 2, 3, 4};
    ArmorTableModel armorModel = new ArmorTableModel(headers, indexes);
    List<String> exceptionList = new ArrayList<>();

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        updateUIArmor();
        setUnalble();
    }

    private void setUnalble(){
        txtArmorID.setEnabled(false);
        txtClassification.setEnabled(false);
        txtTimeOfCreate.setEnabled(false);
        txtDefense.setEnabled(false);
        textAreaDescription.setEnabled(false);
        txtStatus.setEnabled(false);
    }
    
    private void setEnable(){
        txtArmorID.setEnabled(true);
        txtClassification.setEnabled(true);
        txtTimeOfCreate.setEnabled(true);
        txtDefense.setEnabled(true);
        textAreaDescription.setEnabled(true);
        txtStatus.setEnabled(true);
    }
    
    //validate function
    private boolean checkDuplicateArmorID(String id) throws RemoteException, NotBoundException, MalformedURLException {
        ArmorValidator validator = new ArmorValidator();
        boolean result = validator.checkDuplicateArmorID(id);
        return result;
    }

    private boolean checkRegexArmorID(String id) {
        ArmorValidator validator = new ArmorValidator();
        boolean result = validator.checkRegexArmorID(id);
        return result;
    }

    private boolean checkRegexClassification(String classification) {
        ArmorValidator validator = new ArmorValidator();
        boolean result = validator.checkRegexClassification(classification);
        return result;
    }

    private boolean checkRegexDefense(String defenseString) {
        try {
            ArmorValidator validator = new ArmorValidator();
            boolean result = validator.checkRegexDefense(defenseString);
            if (result) {
                return true;
            }
        } catch (NumberFormatException ex) {
            //ghi log
            sonpc.errors.Logger log = new sonpc.errors.Logger();
            exceptionList.add("Main Form - checkRegexDefense() :" + ex);
            log.writeFile(exceptionList);
            return false;
        }
        return false;
    }

    private boolean checkRegexDescription(String description) {
        ArmorValidator validator = new ArmorValidator();
        boolean result = validator.checkRegexDescription(description);
        return result;
    }

    private boolean checkRegexStatus(String status) {
        ArmorValidator validator = new ArmorValidator();
        boolean result = validator.checkRegexStatus(status);
        return result;
    }

    //
    //?
    private ArmorDTO getTextArmorForSave() {
        try {
            String armorID = txtArmorID.getText().trim();
            String classification = txtClassification.getText().trim();
            String dateString = txtTimeOfCreate.getText().trim();
            DateFormatter formatter = new DateFormatter();
            java.util.Date date = formatter.StringToDateFormat(dateString);
            String defenseString = txtDefense.getText().trim();
            int defense = 0;
            String description = textAreaDescription.getText().trim();
            String status = txtStatus.getText().trim();

            //validate
            if (checkDuplicateArmorID(armorID)) {
                JOptionPane.showMessageDialog(this, "Duplicated ArmorID!");
                return null;
            }
            if (!checkRegexArmorID(armorID)) {
                JOptionPane.showMessageDialog(this, "ArmorID not contains special characters and have length [1-10] characters");
                return null;
            }
            if (!checkRegexClassification(classification)) {
                JOptionPane.showMessageDialog(this, "Classification have length [1-30] characters");
                return null;
            }
            if (!checkRegexDefense(defenseString)) {
                JOptionPane.showMessageDialog(this, "Wrong number format in Defense Field!");
                return null;
            } else {
                defense = Integer.parseInt(defenseString);
            }
            if (!checkRegexDescription(description)) {
                JOptionPane.showMessageDialog(this, "Description have length [1-300] characters");
                return null;
            }
            if (!checkRegexStatus(status)) {
                JOptionPane.showMessageDialog(this, "Status have length [1-20] characters");
                return null;
            }
            //

            ArmorDTO dto = new ArmorDTO(armorID, classification, date, defense, description, status);
            return dto;
        } catch (RemoteException | NotBoundException | MalformedURLException | ParseException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("Main Form - getTextArmor():" + ex);
            logger.writeFile(exceptionList);
        }
        return null;
    }

    private ArmorDTO getTextArmorForUpdate() {
        try {
            String armorID = txtArmorID.getText().trim();
            String classification = txtClassification.getText().trim();
            String dateString = txtTimeOfCreate.getText().trim();
            DateFormatter formatter = new DateFormatter();
            java.util.Date date = formatter.StringToDateFormat(dateString);
            String defenseString = txtDefense.getText().trim();
            int defense = 0;
            String description = textAreaDescription.getText().trim();
            String status = txtStatus.getText().trim();

            //validate
//            if (checkDuplicateArmorID(armorID)) {
//                JOptionPane.showMessageDialog(this, "Duplicated ArmorID!");
//                return null;
//            }
//            if (!checkRegexArmorID(armorID)) {
//                JOptionPane.showMessageDialog(this, "ArmorID not contains special characters and have length [1-10] characters");
//                return null;
//            }
            if (!checkRegexClassification(classification)) {
                JOptionPane.showMessageDialog(this, "Classification have length [1-30] characters");
                return null;
            }
            if (!checkRegexDefense(defenseString)) {
                JOptionPane.showMessageDialog(this, "Wrong number format in Defense Field!");
                return null;
            } else {
                defense = Integer.parseInt(defenseString);
            }
            if (!checkRegexDescription(description)) {
                JOptionPane.showMessageDialog(this, "Description have length [1-300] characters");
                return null;
            }
            if (!checkRegexStatus(status)) {
                JOptionPane.showMessageDialog(this, "Status have length [1-20] characters");
                return null;
            }
            //

            ArmorDTO dto = new ArmorDTO(armorID, classification, date, defense, description, status);
            return dto;
        } catch (ParseException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("Main Form - getTextArmor():" + ex);
            logger.writeFile(exceptionList);
        }
        return null;
    }

    private void autoFilledTimeOfCreate() {
        java.util.Date date = new Date();
        DateFormatter formatter = new DateFormatter();
        String dateString = formatter.DateToStringFormat(date);
        txtTimeOfCreate.setText(dateString);
        txtTimeOfCreate.setEditable(false);
    }

    private void setTextArmor(ArmorDTO dto) {
        if (dto != null) {
            txtArmorID.setText(dto.getArmorID());
            txtClassification.setText(dto.getClassfication());
//            autoFilledTimeOfCreate();
            DateFormatter formatter = new DateFormatter();
            txtTimeOfCreate.setText(formatter.DateToStringFormat(dto.getTimeOfCreate()));
            txtDefense.setText(dto.getDefense() + "");
            textAreaDescription.setText(dto.getDescription());
            txtStatus.setText(dto.getStatus());
        }
    }

    private boolean isSelectingRow() {
        int row = tblArmor.getSelectedRow();
        if (row < 0) {
            return false;
        }
        return true;
    }

    private void updateUIArmor() {
        try {
            ArmorDAO dao = new ArmorDAO();
            List<ArmorDTO> list = dao.findAllArmors();
            if (list != null) {
                armorModel = new ArmorTableModel(headers, indexes);
                armorModel.loadData(list);
                tblArmor.setModel(armorModel);
                tblArmor.updateUI();
            }
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("Main Form - updateUIArmor():" + ex);
            logger.writeFile(exceptionList);
            JOptionPane.showMessageDialog(this, "Server is not running. App will shutdown");
            System.exit(0);
        }
    }

    private void resetAllFields() {
        txtArmorID.setText("");
        txtClassification.setText("");
        autoFilledTimeOfCreate();
        textAreaDescription.setText("");
        txtStatus.setText("");
        txtDefense.setText("");
        java.util.Date date = new Date();
        DateFormatter formatter = new DateFormatter();
        String dateString = formatter.DateToStringFormat(date);
        txtTimeOfCreate.setText(dateString);
    }

    //Functional Buttons
    private List<ArmorDTO> findAllArmors() {
        try {
            ArmorDAO dao = new ArmorDAO();
            List<ArmorDTO> list = dao.findAllArmors();
            if (list != null) {
                return list;
            }

        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("Main Form - findAllArmors():" + ex);
            logger.writeFile(exceptionList);
        }
        return null;
    }

    private ArmorDTO findByArmorID(String id) {
        try {
            ArmorDAO dao = new ArmorDAO();
            ArmorDTO dto = dao.findByArmorID(id);
            if (dto != null) {
                return dto;
            }

        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("Main Form - findByArmorID:" + ex);
            logger.writeFile(exceptionList);
            return null;
        }
        return null;
    }

    private void createArmor() {
        resetAllFields();
    }

    private boolean saveArmor() {
        ArmorDTO dto = getTextArmorForSave();
        if (dto != null) {
            try {
                ArmorDAO dao = new ArmorDAO();
                boolean result = dao.createArmor(dto);
                if (result) {
                    return true;
                }
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                sonpc.errors.Logger logger = new sonpc.errors.Logger();
                exceptionList.add("Main Form - saveArmor():" + ex);
                logger.writeFile(exceptionList);
                return false;
            }
        }
        return false;
    }

    private boolean updateArmor() {
        ArmorDTO dto = getTextArmorForUpdate();
        if (dto != null) {
            try {
                ArmorDAO dao = new ArmorDAO();
                boolean result = dao.updateArmor(dto);
                if (result) {
                    return true;
                }
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                sonpc.errors.Logger logger = new sonpc.errors.Logger();
                exceptionList.add("Main Form - updateArmor():" + ex);
                logger.writeFile(exceptionList);
                return false;
            }
        }
        return false;
    }

    private boolean removeArmor(ArmorDTO dto) {
        if (dto != null) {
            try {
                ArmorDAO dao = new ArmorDAO();
                boolean result = dao.removeArmor(dto.getArmorID());
                if (result) {
                    return true;
                }
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                sonpc.errors.Logger logger = new sonpc.errors.Logger();
                exceptionList.add("Main Form - removeArmor():" + ex);
                logger.writeFile(exceptionList);
                return false;
            }
        }
        return false;
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblArmor = new javax.swing.JTable();
        btnGetAll = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtArmorID = new javax.swing.JTextField();
        txtClassification = new javax.swing.JTextField();
        txtTimeOfCreate = new javax.swing.JTextField();
        txtDefense = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaDescription = new javax.swing.JTextArea();
        txtStatus = new javax.swing.JTextField();
        btnFindByID = new javax.swing.JButton();
        btnCreate = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Armor Client");

        tblArmor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblArmor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblArmorMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblArmor);

        btnGetAll.setText("Get All");
        btnGetAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(btnGetAll)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGetAll)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(" Armor's Detail"));

        jLabel2.setText("ArmorID");

        jLabel3.setText("Classification:");

        jLabel4.setText("TimeOfCreate:");

        jLabel5.setText("Defense:");

        jLabel6.setText("Description:");

        jLabel7.setText("Status:");

        textAreaDescription.setColumns(20);
        textAreaDescription.setRows(5);
        jScrollPane1.setViewportView(textAreaDescription);

        btnFindByID.setText("Find By ArmorID");
        btnFindByID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindByIDActionPerformed(evt);
            }
        });

        btnCreate.setText("Create");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCreate)
                        .addGap(75, 75, 75)
                        .addComponent(btnSave)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(47, 47, 47)
                            .addComponent(txtArmorID))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addComponent(txtClassification, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                .addComponent(txtTimeOfCreate)
                                .addComponent(txtDefense)
                                .addComponent(txtStatus)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFindByID, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRemove, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(29, 29, 29))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtArmorID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFindByID))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtClassification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTimeOfCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDefense, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnSave)
                    .addComponent(btnRemove)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(271, 271, 271))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFindByIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindByIDActionPerformed

        String id = txtArmorID.getText().trim();
        if (!id.isEmpty()) {
            ArmorDTO dto = findByArmorID(id);
            if (dto != null) {
                armorModel = new ArmorTableModel(headers, indexes);
                List<ArmorDTO> armorList = new ArrayList();
                armorList.add(dto);
                armorModel.loadData(armorList);
                tblArmor.setModel(armorModel);
                tblArmor.updateUI();
            } else {
                JOptionPane.showMessageDialog(this, "ArmorID Not Found!");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter ArmorID first!");
            return;
        }

    }//GEN-LAST:event_btnFindByIDActionPerformed

    private void tblArmorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblArmorMouseClicked
        setEnable();
        txtArmorID.setEditable(false);
        int rowSelect = tblArmor.getSelectedRow();
        if (rowSelect >= 0) {
            ArmorDTO dto = armorModel.getList().get(rowSelect);
            setTextArmor(dto);
        }
    }//GEN-LAST:event_tblArmorMouseClicked

    private void btnGetAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetAllActionPerformed
        List<ArmorDTO> list = findAllArmors();
        if (list != null) {
            armorModel = new ArmorTableModel(headers, indexes);
            armorModel.loadData(list);
            tblArmor.setModel(armorModel);
            tblArmor.updateUI();
        }
    }//GEN-LAST:event_btnGetAllActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        setEnable();
        createArmor();
        txtArmorID.setEditable(true);
        updateUIArmor();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        boolean isSelecting = isSelectingRow();
        if (isSelecting) {
            //update
            boolean result = updateArmor();
            if (result) {
                updateUIArmor();
                JOptionPane.showMessageDialog(this, "Update Successfully!");
                return;
            } else {
                JOptionPane.showMessageDialog(this, "Update Failed");
                return;
            }
        } else {
            //save
            boolean result = saveArmor();
            if (result) {
                updateUIArmor();
                JOptionPane.showMessageDialog(this, "Save Successfully!");
                return;
            } else {
                JOptionPane.showMessageDialog(this, "Save Failed!");
                return;
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        boolean isSelecting = isSelectingRow();
        if (isSelecting) {
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to delete this Armor?");
            if (choice == 0) {
                int row = tblArmor.getSelectedRow();
                ArmorDTO dto = armorModel.getList().get(row);
                boolean result = removeArmor(dto);
                if (result) {
                    updateUIArmor();
                    JOptionPane.showMessageDialog(this, "Remove Successfully");
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, "Remove Failed");
                    return;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row in the table to remove");
            return;
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnFindByID;
    private javax.swing.JButton btnGetAll;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblArmor;
    private javax.swing.JTextArea textAreaDescription;
    private javax.swing.JTextField txtArmorID;
    private javax.swing.JTextField txtClassification;
    private javax.swing.JTextField txtDefense;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTimeOfCreate;
    // End of variables declaration//GEN-END:variables
}
