/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.validators;

import java.io.Serializable;

/**
 *
 * @author ACER
 */
public class UserInfoValidator implements Serializable{
    
    public boolean checkRegexUsername(String username){
        String pattern = "[a-zA-Z0-9]+";
        if (username.matches(pattern) == false){
            return false;
        }
        return true;
    }
    
    public boolean checkLengthUsername(String username){
        if (username.isEmpty() || username.length() > 10){
            return false;
        }
        return true;
    }
    
    
    public boolean checkRegexPassword(String password){
        if (password.isEmpty() || password.length() > 50){
            return false;
        }
        return true;
    }
}
