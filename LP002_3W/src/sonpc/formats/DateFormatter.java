/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.formats;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class DateFormatter implements Serializable{
    private final String FORMATTER = "dd/MM/yyyy";
    
    public java.util.Date StringToDateFormat(String dateString) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat(FORMATTER);
        java.util.Date date = format.parse(dateString);
        return date;
    }
    
    public String DateToStringFormat(java.util.Date date){
        SimpleDateFormat format = new SimpleDateFormat(FORMATTER);
        String dateString = format.format(date);
        return dateString;
    }
    
}
