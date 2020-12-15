/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.daos;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import sonpc.dtos.TblEmployeeDTO;
import sonpc.formatters.DateFormatter;
import sonpc.utils.DBHelpers;

/**
 *
 * @author ACER
 */
public class TblEmployeeDAO {

    public List<TblEmployeeDTO> getAllEmps() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TblEmployeeDTO> list = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "Select empID, fullName, phone, email, address, dateOfBirth From tblEmployee";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    String empID = rs.getString("empID");
                    String fullName = rs.getString("fullName");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    long DOB = rs.getLong("dateOfBirth");

                    //formatter
                    java.util.Date date = new java.util.Date(DOB);
                    //

                    TblEmployeeDTO empDTO = new TblEmployeeDTO(empID, fullName, phone, email, address, date);
                    list.add(empDTO);
                }
                return list;
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public List<TblEmployeeDTO> getAvailEmps() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TblEmployeeDTO> list = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "Select empID, fullName, phone, email, address, dateOfBirth From tblEmployee Where isDelete = 0";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    String empID = rs.getString("empID");
                    String fullName = rs.getString("fullName");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    long DOB = rs.getLong("dateOfBirth");

                    //formatter
                    java.util.Date date = new java.util.Date(DOB);
                    //

                    TblEmployeeDTO empDTO = new TblEmployeeDTO(empID, fullName, phone, email, address, date);
                    list.add(empDTO);
                }
                return list;
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public TblEmployeeDTO findByEmpID(String id) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "Select fullName, phone, email, address, dateOfBirth From tblEmployee Where isDelete = 0 and empID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    long dateOfBirth = rs.getLong("dateOfBirth");

                    //forrmatter
                    java.util.Date date = new java.util.Date(dateOfBirth);

                    TblEmployeeDTO empDTO = new TblEmployeeDTO(id, fullName, phone, email, address, date);
                    return empDTO;

                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public boolean saveEmp(TblEmployeeDTO dto) throws SQLException, ClassNotFoundException{
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "Insert Into tblEmployee (empID, fullName, phone, email, address, dateOfBirth, isDelete) Values(?,?,?,?,?,?,0)";
                ps = con.prepareStatement(sql);
                ps.setString(1, dto.getEmpID());
                ps.setString(2, dto.getFullName());
                ps.setString(3, dto.getPhone());
                ps.setString(4, dto.getEmail());
                ps.setString(5, dto.getAddress());
                ps.setLong(6, dto.getDOB().getTime());
                int row = ps.executeUpdate();
                if (row > 0){
                    return true;
                }
            }
        }
        finally{
            if (ps != null){
                ps.close();
            }
            if (con != null){
                con.close();
            }
        }
        return false;
    }     
    
    public boolean updateEmp(TblEmployeeDTO dto) throws ClassNotFoundException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "Update tblEmployee Set fullName = ?, phone = ?, email = ?, address = ?, dateOfBirth = ? Where empID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, dto.getFullName());
                ps.setString(2, dto.getPhone());
                ps.setString(3, dto.getEmail());
                ps.setString(4, dto.getAddress());
                ps.setLong(5, dto.getDOB().getTime());
                ps.setString(6, dto.getEmpID());
                
                int row = ps.executeUpdate();
                if (row > 0){
                    return true;
                }
            }
        }
        finally{
            if (ps != null){
                ps.close();
            }
            if (con != null){
                con.close();
            }
        }
        return false;
    }
    
    public boolean removeEmp(TblEmployeeDTO dto) throws ClassNotFoundException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = DBHelpers.makeConnection();
            if (con != null){
                String sql = "Update tblEmployee Set isDelete = 1 Where empID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, dto.getEmpID());
                int row = ps.executeUpdate();
                if (row > 0){
                    return true;
                }
            }
        }
        finally{
            if (ps != null){
                ps.close();
            }
            if (con != null){
                con.close();
            }
        }
        return false;
    }

}
