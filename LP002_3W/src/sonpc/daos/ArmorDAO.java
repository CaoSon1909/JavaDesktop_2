/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.daos;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import sonpc.dtos.ArmorDTO;
import sonpc.server.ArmorServerInterf;
import sonpc.utils.DBHelpers;

/**
 *
 * @author ACER
 */
public class ArmorDAO implements Serializable {

    public boolean createArmor(ArmorDTO dto) throws NotBoundException, MalformedURLException, RemoteException {
        ArmorServerInterf armorServer = null;
        armorServer = DBHelpers.makeConnection();
        if (armorServer != null) {
            if (dto != null) {
                boolean result = armorServer.createArmor(dto);
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArmorDTO findByArmorID(String id) throws NotBoundException, MalformedURLException, RemoteException {
        ArmorServerInterf armorServer = null;
        armorServer = DBHelpers.makeConnection();
        if (armorServer != null) {
            ArmorDTO dto = armorServer.findByArmorID(id);
            if (dto != null) {
                return dto;
            }
        }
        return null;
    }

    public List<ArmorDTO> findAllArmors() throws NotBoundException, MalformedURLException, RemoteException {
        ArmorServerInterf armorServer = null;
        armorServer = DBHelpers.makeConnection();
        if (armorServer != null) {
            List<ArmorDTO> list = armorServer.findAllArmors();
            if (list != null) {
                return list;
            }
        }
        return null;
    }

    public boolean updateArmor(ArmorDTO newDTO) throws NotBoundException, MalformedURLException, RemoteException{
        ArmorServerInterf armorServer = null;
        armorServer = DBHelpers.makeConnection();
        if (armorServer != null){
            boolean result = armorServer.updateArmor(newDTO);
            if (result){
                return true;
            }
        }
        return false;
    }
    
    public boolean removeArmor(String id) throws NotBoundException, MalformedURLException, RemoteException{
        ArmorServerInterf armorServer = null;
        armorServer = DBHelpers.makeConnection();
        if (armorServer != null){
            boolean result = armorServer.removeArmor(id);
            if (result){
                return true;
            }
        }
        return false;
    }
    
    
    
}
