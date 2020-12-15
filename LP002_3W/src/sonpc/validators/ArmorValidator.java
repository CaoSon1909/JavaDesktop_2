/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.validators;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import sonpc.daos.ArmorDAO;
import sonpc.dtos.ArmorDTO;

/**
 *
 * @author ACER
 */
public class ArmorValidator implements Serializable {

    public boolean checkDuplicateArmorID(String id) throws RemoteException, NotBoundException, MalformedURLException {
        ArmorDAO dao = new ArmorDAO();
        List<ArmorDTO> list = dao.findAllArmors();
        if (list != null) {
            for (ArmorDTO dto : list) {
                if (dto.getArmorID().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkRegexArmorID(String id) {
        String pattern = "[a-zA-Z0-9]+{3,10}";
        if (id.length() < 3 || id.length() > 10) {
           return false;
        }
        boolean result = id.matches(pattern);
        if (result) {
            return true;
        }
        return false;
    }

    public boolean checkRegexClassification(String classification) {
        if (classification.length() >= 1 && classification.length() <= 30) {
            return true;
        }
        return false;
    }

    public boolean checkRegexDescription(String description) {
        if (description.length() >= 1 && description.length() <= 300) {
            return true;
        }
        return false;
    }

    public boolean checkRegexDefense(String defenseString) throws NumberFormatException {
        int defense = Integer.parseInt(defenseString);

        if (defense <= 0) {
            return false;
        }
        return true;
    }

    public boolean checkRegexStatus(String status) {
        if (status.length() >= 1 && status.length() <= 20) {
            return true;
        }
        return false;
    }

}
