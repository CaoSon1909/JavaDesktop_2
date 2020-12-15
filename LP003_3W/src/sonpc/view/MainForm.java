/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.view;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import sonpc.daos.TblEmployeeDAO;
import sonpc.dtos.TblEmployeeDTO;
import sonpc.formatters.DateFormatter;
import sonpc.tablemodel.EmployeeTableModel;
import sonpc.validators.EmployeeValidator;

/**
 *
 * @author ACER
 */
public class MainForm extends javax.swing.JFrame {
    
    private String[] HEADERS = {"EmpID", "Fullname", "Phone", "Email"};
    private int[] INDEXES = {1, 2, 3, 4};
    private List<String> exceptionList = new ArrayList<>();
    private EmployeeTableModel model = new EmployeeTableModel();

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        updateUITable();
        setUnable();
    }
    
    private void updateUITable() {
        try {
            TblEmployeeDAO empDAO = new TblEmployeeDAO();
            List<TblEmployeeDTO> list = empDAO.getAvailEmps();
            if (list != null) {
                model = new EmployeeTableModel(HEADERS, INDEXES);
                model.loadData(list);
                tblEmp.setModel(model);
                tblEmp.updateUI();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("Main Form - updateUITable()" + ex);
            logger.writeFile(exceptionList);
            return;
            
        }
    }
    
    private void setUnable() {
        txtEmpID.setEnabled(false);
        txtFullname.setEnabled(false);
        txtPhone.setEnabled(false);
        txtEmail.setEnabled(false);
        txtAddress.setEnabled(false);
        txtDOB.setEnabled(false);
    }
    
    private void setEnable() {
        txtEmpID.setEnabled(true);
        txtFullname.setEnabled(true);
        txtPhone.setEnabled(true);
        txtEmail.setEnabled(true);
        txtAddress.setEnabled(true);
        txtDOB.setEnabled(true);
    }
    
    private boolean isSelectingRow() {
        int rowSelect = tblEmp.getSelectedRow();
        if (rowSelect < 0) {
            return false;
        }
        return true;
    }
    
    private TblEmployeeDTO getTextEmployee() {
        try {
            String empID = txtEmpID.getText().trim();
            String fullName = txtFullname.getText().trim();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();
            String address = txtAddress.getText().trim();
            String DOB = txtDOB.getText().trim();

            //format
            DateFormatter formatter = new DateFormatter();
            java.util.Date date = formatter.stringToDateFormatter(DOB);
            //
            TblEmployeeDTO empDTO = new TblEmployeeDTO(empID, fullName, phone, email, address, date);
            
            return empDTO;
        } catch (ParseException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("Main Form - getTextEmployee():" + ex);
            logger.writeFile(exceptionList);
        }
        return null;
    }
    
    private void setTextEmployee(TblEmployeeDTO empDTO) {
        if (empDTO != null) {
            txtEmpID.setText(empDTO.getEmpID());
            txtFullname.setText(empDTO.getFullName());
            txtPhone.setText(empDTO.getPhone());
            txtEmail.setText(empDTO.getEmail());
            txtAddress.setText(empDTO.getAddress());
            //
            java.util.Date date = empDTO.getDOB();
            DateFormatter formatter = new DateFormatter();
            String dateString = formatter.dateToStringFormatter(date);
            //
            txtDOB.setText(dateString);
        }
    }
    
    private void resetAllFields() {
        txtEmpID.setText("");
        txtFullname.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtDOB.setText("");
    }
    
    private List<TblEmployeeDTO> getAllEmps() {
        try {
            TblEmployeeDAO empDAO = new TblEmployeeDAO();
            List<TblEmployeeDTO> list = empDAO.getAllEmps();
            if (list != null) {
                return list;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("MainForm - getAllEmps():" + ex);
            logger.writeFile(exceptionList);
        }
        return null;
    }
    
    private List<TblEmployeeDTO> getAvailEmps() {
        try {
            TblEmployeeDAO empDAO = new TblEmployeeDAO();
            List<TblEmployeeDTO> list = empDAO.getAvailEmps();
            if (list != null) {
                return list;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("MainForm - getAvailEmps():" + ex);
            logger.writeFile(exceptionList);
        }
        return null;
    }
    
    private boolean checkDuplicateEmpID(String empID) {
        try {
            EmployeeValidator validator = new EmployeeValidator();
            boolean result = validator.checkDuplicateEmpID(empID);
            if (result) {
                return true;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("MainForm - checkDuplicateEmpID:" + ex);
            logger.writeFile(exceptionList);
        }
        return false;
    }
    
    private boolean checkRegexEmpID(String empID) {
        EmployeeValidator validator = new EmployeeValidator();
        boolean result = validator.checkRegexEmpID(empID);
        if (result) {
            return true;
        }
        return false;
    }
    
    private boolean checkLengthEmpID(String empID){
        EmployeeValidator validator = new EmployeeValidator();
        boolean result = validator.checkLengthEmpID(empID);
        return result;
    }
    
    private boolean checkRegexFullName(String fullName) {
        EmployeeValidator validator = new EmployeeValidator();
        boolean result = validator.checkRegexFullName(fullName);
        if (result) {
            return true;
        }
        return false;
    }
    
    private boolean checkRegexPhone(String phone) {
        EmployeeValidator validator = new EmployeeValidator();
        boolean result = validator.checkRegexPhone(phone);
        if (result) {
            return true;
        }
        return false;
    }
    
    private boolean checkRegexEmail(String email) {
        EmployeeValidator validator = new EmployeeValidator();
        boolean result = validator.checkRegexEmail(email);
        if (result) {
            return true;
        }
        return false;
    }
    
    private boolean checkRegexAddress(String address) {
        EmployeeValidator validator = new EmployeeValidator();
        boolean result = validator.checkRegexAddress(address);
        if (result) {
            return true;
        }
        return false;
    }
    
    private boolean checkRegexDOB(String DOB) {
        EmployeeValidator validator = new EmployeeValidator();
        boolean result = validator.checkRegexDOB(DOB);
        if (result) {
            return true;
        }
        return false;
    }
    
    private boolean checkGreaterDate(String DOB) {
        try {
            EmployeeValidator validator = new EmployeeValidator();
            boolean result = validator.checkGreaterDate(DOB);
            if (result) {
                return true;
            }
        } catch (ParseException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("MainForm - checkGreaterDate:" + ex);
            logger.writeFile(exceptionList);
        }
        return false;
    }
    
    private boolean saveEmployee() {
        TblEmployeeDTO empDTO = getTextEmployee();

        //validate
        if (checkDuplicateEmpID(empDTO.getEmpID())) {
            JOptionPane.showMessageDialog(this, "Duplicated empID!");
            return false;
        }
        if (!checkRegexEmpID(empDTO.getEmpID())) {
            JOptionPane.showMessageDialog(this, " empID do not contain special characters!");
            return false;
        }
        if (!checkLengthEmpID(empDTO.getEmpID())){
            JOptionPane.showMessageDialog(this, "empID have max length is 10");
            return false;
        }
        if (!checkRegexFullName(empDTO.getFullName())) {
            JOptionPane.showMessageDialog(this, "Fullname have length from range [1-30] characters");
            return false;
        }
        if (!checkRegexPhone(empDTO.getPhone())) {
            JOptionPane.showMessageDialog(this, "Phone have length in range [10-15] and contain number only!");
            return false;
        }
        if (!checkRegexEmail(empDTO.getEmail())) {
            JOptionPane.showMessageDialog(this, "Email have max length is 30, contain only one '@' character and do not contain special characters!");
            return false;
        }
        if (!checkRegexAddress(empDTO.getAddress())) {
            JOptionPane.showMessageDialog(this, "Address have length from [1-300] characters");
            return false; 
        }
        String dateString = txtDOB.getText().trim();
        if (!checkRegexDOB(dateString)) {
            JOptionPane.showMessageDialog(this, "Date of Birth is not in correct format");
            return false;
        }
        if (!checkGreaterDate(dateString)) {
            JOptionPane.showMessageDialog(this, "Cannot input a date greater than current date!");
            return false;
        }
        //
        if (empDTO != null) {
            try {
                TblEmployeeDAO empDAO = new TblEmployeeDAO();
                boolean result = empDAO.saveEmp(empDTO);
                if (result) {
                    return true;
                }
            } catch (SQLException | ClassNotFoundException ex) {
                sonpc.errors.Logger logger = new sonpc.errors.Logger();
                exceptionList.add("MainForm - saveEmployee():" + ex);
                logger.writeFile(exceptionList);
            }
        }
        return false;
    }
    
    private boolean updateEmployee() {
        TblEmployeeDTO empDTO = getTextEmployee();
        //validate
        if (!checkRegexFullName(empDTO.getFullName())) {
            JOptionPane.showMessageDialog(this, "Fullname have max length is 30!");
            return false;
        }
        if (!checkRegexPhone(empDTO.getPhone())) {
            JOptionPane.showMessageDialog(this, "Phone have length in range [10-15] and contain number only!");
            return false;
        }
        if (!checkRegexEmail(empDTO.getEmail())) {
            JOptionPane.showMessageDialog(this, "Email have max length is 30, contain only one '@' character and do not contain special characters!");
            return false;
        }
        if (!checkRegexAddress(empDTO.getAddress())) {
            JOptionPane.showMessageDialog(this, "Address have max length is 300!");
            return false;
        }
        String dateString = txtDOB.getText().trim();
        if (!checkRegexDOB(dateString)) {
            JOptionPane.showMessageDialog(this, "Date of Birth is not in correct format");
            return false;
        }
        if (!checkGreaterDate(dateString)) {
            JOptionPane.showMessageDialog(this, "Cannot input a date greater than current date!");
            return false;
        }
        //
        if (empDTO != null) {
            try {
                TblEmployeeDAO empDAO = new TblEmployeeDAO();
                boolean result = empDAO.updateEmp(empDTO);
                if (result) {
                    return true;
                }
            } catch (ClassNotFoundException | SQLException ex) {
                sonpc.errors.Logger logger = new sonpc.errors.Logger();
                exceptionList.add("MainForm - updateEmployee:" + ex);
                logger.writeFile(exceptionList);
            }
        }
        return false;
    }
    
    private boolean removeEmployee() {
        TblEmployeeDTO empDTO = getTextEmployee();
        if (empDTO != null) {
            try {
                TblEmployeeDAO empDAO = new TblEmployeeDAO();
                boolean result = empDAO.removeEmp(empDTO);
                if (result) {
                    return true;
                }
            } catch (ClassNotFoundException | SQLException ex) {
                sonpc.errors.Logger logger = new sonpc.errors.Logger();
                exceptionList.add("MainForm - removeEmployee()" + ex);
                logger.writeFile(exceptionList);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmp = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtEmpID = new javax.swing.JTextField();
        btnFindByID = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtFullname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDOB = new javax.swing.JTextField();
        btnCreate = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnGetAvailEmps = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Emp Management");

        tblEmp.setModel(new javax.swing.table.DefaultTableModel(
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
        tblEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmpMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEmp);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Emp's Detail"));

        jLabel2.setText("Emp ID:");

        btnFindByID.setText("Find By EmpID");
        btnFindByID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindByIDActionPerformed(evt);
            }
        });

        jLabel3.setText("FullName:");

        jLabel4.setText("Phone:");

        jLabel5.setText("Email:");

        jLabel6.setText("Address:");

        jLabel7.setText("DOB:");

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
                        .addGap(59, 59, 59)
                        .addComponent(btnSave))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(txtEmpID, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtFullname)
                                .addComponent(txtPhone)
                                .addComponent(txtEmail)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtAddress)
                                .addComponent(txtDOB)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFindByID)
                    .addComponent(btnRemove))
                .addGap(32, 32, 32))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtEmpID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFindByID))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnSave)
                    .addComponent(btnRemove))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        btnGetAvailEmps.setText("Get Available Emps");
        btnGetAvailEmps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetAvailEmpsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(btnGetAvailEmps)))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(257, 257, 257)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnGetAvailEmps)
                        .addContainerGap())
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblEmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpMouseClicked
        txtEmpID.setEditable(false);
        setEnable();
        int rowSelect = tblEmp.getSelectedRow();
        if (rowSelect < 0) {
            return;
        } else {
            TblEmployeeDTO empDTO = model.getList().get(rowSelect);
            if (empDTO != null) {
                setTextEmployee(empDTO);
            }
        }
    }//GEN-LAST:event_tblEmpMouseClicked

    private void btnFindByIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindByIDActionPerformed
        txtEmpID.setEditable(true);
        try {
            String empID = txtEmpID.getText().trim();
            if (empID.length() == 0) {
                JOptionPane.showMessageDialog(this, "empID must not be null");
                return;
            }
            TblEmployeeDAO empDAO = new TblEmployeeDAO();
            TblEmployeeDTO empDTO = empDAO.findByEmpID(empID);
            
            if (empDTO != null) {
                setTextEmployee(empDTO);
                model = new EmployeeTableModel(HEADERS, INDEXES);
                List<TblEmployeeDTO> list = new ArrayList<>();
                list.add(empDTO);
                model.loadData(list);
                tblEmp.setModel(model);
                tblEmp.updateUI();
            } else {
                JOptionPane.showMessageDialog(this, "Not Found The empID");
                return;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("MainForm - Find by ID button:" + ex);
            logger.writeFile(exceptionList);
        }
    }//GEN-LAST:event_btnFindByIDActionPerformed

    private void btnGetAvailEmpsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetAvailEmpsActionPerformed
        List<TblEmployeeDTO> list = getAvailEmps();
        if (list != null) {
            model = new EmployeeTableModel(HEADERS, INDEXES);
            model.loadData(list);
            tblEmp.setModel(model);
            tblEmp.updateUI();
        }
    }//GEN-LAST:event_btnGetAvailEmpsActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        resetAllFields();
        txtEmpID.setEditable(true);
        updateUITable();
        setEnable();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        boolean isSelecting = isSelectingRow();
        if (!isSelecting) {
            //save
            boolean result = saveEmployee();
            if (result) {
                JOptionPane.showMessageDialog(this, "Save Successfully");
                updateUITable();
                return;
            } else {
                JOptionPane.showMessageDialog(this, "Save Failed");
                return;
            }
        } else {
            //update
            boolean result = updateEmployee();
            if (result) {
                JOptionPane.showMessageDialog(this, "Update Successfully");
                updateUITable();
                return;
            } else {
                JOptionPane.showMessageDialog(this, "Update Failed");
                return;
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        boolean isSelecting = isSelectingRow();
        if (!isSelecting) {
            return;
        } else {
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to delete this Employee?");
            if (choice == 0) {
                boolean result = removeEmployee();
                if (result){
                    JOptionPane.showMessageDialog(this, "Remove Successfully");
                    updateUITable();
                    return;
                }
                else{
                    JOptionPane.showMessageDialog(this, "Remove Failed");
                    return;
                }
            } else {
                return;
            }
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
    private javax.swing.JButton btnGetAvailEmps;
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
    private javax.swing.JTable tblEmp;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtDOB;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmpID;
    private javax.swing.JTextField txtFullname;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
