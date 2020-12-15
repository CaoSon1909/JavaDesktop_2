/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.formatters;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class DateFormatter implements Serializable{
    
    private final String FORMATTER = "dd/MM/yyyy";
    private final String FORMATTER1 = "MM/dd/yyyy";
    
    public String dateToStringFormatter(java.util.Date date){
        SimpleDateFormat format = new SimpleDateFormat(FORMATTER);
        String dateString = format.format(date);
        return dateString;
    }
    
    public java.util.Date stringToDateFormatter(String dateString) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat(FORMATTER);
        java.util.Date date = format.parse(dateString);
        return date;
    }
    
    public String convertDateFormat(String dateString) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat(FORMATTER);
        SimpleDateFormat format1 = new SimpleDateFormat(FORMATTER1);
        
        String date = format.format(format1.parse(dateString));
        
        return date;
        
    }
    
}
