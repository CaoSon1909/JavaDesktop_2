/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class ArmorDTO implements Serializable{
    
    private String armorID;
    private String classfication;
    private java.util.Date timeOfCreate;
    private int defense;
    private String description;
    private String status;

    public ArmorDTO() {
    }

    public ArmorDTO(String armorID, String classfication, Date timeOfCreate, int defense, String description, String status) {
        this.armorID = armorID;
        this.classfication = classfication;
        this.timeOfCreate = timeOfCreate;
        this.defense = defense;
        this.description = description;
        this.status = status;
    }

    public String getArmorID() {
        return armorID;
    }

    public void setArmorID(String armorID) {
        this.armorID = armorID;
    }

    public String getClassfication() {
        return classfication;
    }

    public void setClassfication(String classfication) {
        this.classfication = classfication;
    }

    public Date getTimeOfCreate() {
        return timeOfCreate;
    }

    public void setTimeOfCreate(Date timeOfCreate) {
        this.timeOfCreate = timeOfCreate;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
