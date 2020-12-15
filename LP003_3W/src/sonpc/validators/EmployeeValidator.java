/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.validators;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sonpc.daos.TblEmployeeDAO;
import sonpc.dtos.TblEmployeeDTO;
import sonpc.formatters.DateFormatter;

/**
 *
 * @author ACER
 */
public class EmployeeValidator implements Serializable {

    public boolean checkDuplicateEmpID(String empID) throws ClassNotFoundException, SQLException {
        TblEmployeeDAO empDAO = new TblEmployeeDAO();
        List<TblEmployeeDTO> list = empDAO.getAllEmps();
        if (list != null) {
            for (TblEmployeeDTO dto : list) {
                if (dto.getEmpID().equals(empID)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkRegexEmpID(String empID) {
        String pattern = "[a-zA-Z0-9]+";
        boolean result = empID.matches(pattern);
        return result;
    }
    
    public boolean checkLengthEmpID(String empID){
        if (empID.isEmpty() || empID.length() > 10){
            return false;
        }
        return true;
    }

    public boolean checkRegexFullName(String fullName) {
        if (fullName.isEmpty() || fullName.length() > 30) {
            return false;
        }
        return true;
    }

    public boolean checkRegexPhone(String phone) {
        String pattern = "[0-9]+{10,15}";
        boolean result = phone.matches(pattern);
        if (phone.length() < 10 || phone.length() > 15){
            return false;
        }
        return result;
    }
    
    public boolean checkRegexEmail(String email) {
        String pattern = "[a-zA-Z0-9]+@[a-zA-Z0-9]+[.][a-zA-Z0-9]+";
        if (email.isEmpty() || email.length() > 30){
            return false;
        }
        boolean result = email.matches(pattern);
        return result;
    }

    public boolean checkRegexAddress(String address) {
        if (address.isEmpty() || address.length() > 300) {
            return false;
        }
        return true;
    }

    public boolean checkRegexDOB(String DOB){
        String pattern = "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)";
        if (!DOB.matches(pattern)) {
            return false;
        }
        return true;
    }

    public boolean checkGreaterDate(String DOB) throws ParseException {
        /*new Date() phải bỏ đi phần giờ thì mới so sánh ngày đc. Convert today sang String rồi chuyển ngược về date*/
        DateFormatter formatter = new DateFormatter();
        java.util.Date date = formatter.stringToDateFormatter(DOB);
        
        //today
        java.util.Date today = new Date();
        String todayString = formatter.dateToStringFormatter(today);
        java.util.Date todayWithoutTime = formatter.stringToDateFormatter(todayString);
        
        if (date.getTime() >= todayWithoutTime.getTime()){
            return false;
        }
        return true;
    }
    
//    public static void main(String[] args) {
//        try {
//            EmployeeValidator validator = new EmployeeValidator();
//            String DOB = "14/12/2020";
//            boolean result = validator.checkGreaterDate(DOB);
//            System.out.println(result);
//        } catch (ParseException ex) {
//            Logger.getLogger(EmployeeValidator.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

}
