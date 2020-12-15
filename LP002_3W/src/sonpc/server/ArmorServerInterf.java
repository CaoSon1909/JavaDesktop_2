/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import sonpc.dtos.ArmorDTO;

/**
 *
 * @author ACER
 */
public interface ArmorServerInterf  extends Remote{
    
    boolean createArmor(ArmorDTO dto) throws RemoteException;
    ArmorDTO findByArmorID(String id) throws RemoteException;
    List<ArmorDTO> findAllArmors() throws RemoteException;
    boolean updateArmor(ArmorDTO dto) throws RemoteException;
    boolean removeArmor(String id) throws RemoteException;
    
}
