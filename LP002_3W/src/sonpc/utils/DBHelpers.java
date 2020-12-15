/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.utils;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import sonpc.server.ArmorServerInterf;

/**
 *
 * @author ACER
 */
public class DBHelpers {

    public static ArmorServerInterf makeConnection() throws NotBoundException, MalformedURLException, RemoteException {
        ArmorServerInterf armorServer = (ArmorServerInterf) Naming.lookup("rmi://localhost:1234/armor");
        return armorServer;
    }

}
