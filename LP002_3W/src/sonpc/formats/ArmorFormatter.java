/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.formats;

import java.io.Serializable;
import java.text.ParseException;
import sonpc.dtos.ArmorDTO;

/**
 *
 * @author ACER
 */
public class ArmorFormatter implements Serializable{
    
    private final String DELIMETER = "-,-";
    
    public ArmorDTO StringToDTOFormat(String armorID, String classification, String description, String status,
            String dateString, String defenseString) throws NumberFormatException, ParseException{
        //timeOfCreate
        DateFormatter dateFormat = new DateFormatter();
        java.util.Date timeOfCreate = dateFormat.StringToDateFormat(dateString);
        //defense
        int defense = Integer.parseInt(defenseString);
        
        ArmorDTO dto = new ArmorDTO(armorID, classification, timeOfCreate, defense, description, status);
        return dto;
    }
    
    public String DTOToStringFormat(ArmorDTO dto){
        String armorID = dto.getArmorID();
        String classification = dto.getClassfication();
        String description = dto.getDescription();
        String status = dto.getStatus();
        java.util.Date timeOfCreate = dto.getTimeOfCreate();
        int defense = dto.getDefense();
        
        //dateString
        DateFormatter dateFormat = new DateFormatter();
        String dateString = dateFormat.DateToStringFormat(timeOfCreate);
        //
        //defenseString
        String defenseString = defense+"";
        //
        
        //nối chuỗi lại
        StringBuilder sb = new StringBuilder();
        sb.append(armorID);
        sb.append(DELIMETER);
        sb.append(classification);
        sb.append(DELIMETER);
        sb.append(description);
        sb.append(DELIMETER);
        sb.append(status);
        sb.append(DELIMETER);
        sb.append(dateString);
        sb.append(DELIMETER);
        sb.append(defenseString);
        //
        return sb.toString();
    }
}
