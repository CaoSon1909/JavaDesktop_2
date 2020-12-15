/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import sonpc.dtos.ArmorDTO;
import sonpc.formats.ArmorFormatter;

/**
 *
 * @author ACER
 */
public class ArmorServer extends UnicastRemoteObject implements ArmorServerInterf {

    private final String FILENAME = "ArmorData.txt";
    private final String DELIMETER = "-,-";
    private List<String> exceptionList = new ArrayList<>();

    public ArmorServer() throws RemoteException {
        super();
        readFile();
    }

    public List<ArmorDTO> readFile() {
        File file = null;
        FileReader fr = null;
        BufferedReader br = null;
        List<ArmorDTO> list = null;
        try {

            file = new File(FILENAME);
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line = "";

            while ((line = br.readLine()) != null) {
                String tmp[] = line.split(DELIMETER);
                String armorID = tmp[0];
                String classification = tmp[1];
                String description = tmp[2];
                String status = tmp[3];
                String dateString = tmp[4];
                String defenseString = tmp[5];
                // create DTO
                ArmorFormatter armorFormat = new ArmorFormatter();
                ArmorDTO dto = armorFormat.StringToDTOFormat(armorID, classification, description, status, dateString, defenseString);
                if (list == null) {
                    list = new Vector<>();
                }
                list.add(dto);
            }

            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("ArmorServer - readFile():" + ex);
            logger.writeFile(exceptionList);

        } catch (IOException | ParseException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("ArmorServer - readFile():" + ex);
            logger.writeFile(exceptionList);
        }

        return list;
    }

    public void writeFille(List<ArmorDTO> list) {
        File file = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {

            file = new File(FILENAME);
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);

            if (list != null) {
                for (ArmorDTO dto : list) {
                    ArmorFormatter formatter = new ArmorFormatter();
                    String lineDTO = formatter.DTOToStringFormat(dto);
                    bw.write(lineDTO);
                    bw.write("\n");
                }
            }
            bw.close();
            fw.close();

        } catch (IOException ex) {
            sonpc.errors.Logger logger = new sonpc.errors.Logger();
            exceptionList.add("ArmorServer - writeFile():"+ex);
            logger.writeFile(exceptionList);
        }
    }

    @Override
    public boolean createArmor(ArmorDTO dto) throws RemoteException {
        List<ArmorDTO> list = readFile();
        if (list != null) {
            list.add(dto);
            writeFille(list);
            return true;
        }
        return false;
    }

    @Override
    public ArmorDTO findByArmorID(String id) throws RemoteException {
        List<ArmorDTO> list = readFile();
        if (list != null) {
            for (ArmorDTO dto : list) {
                if (dto.getArmorID().equals(id)) {
                    return dto;
                }
            }
        }
        return null;
    }

    @Override
    public List<ArmorDTO> findAllArmors() throws RemoteException {
        List<ArmorDTO> list = readFile();
        if (list != null) {
            return list;
        }
        return null;
    }

    @Override
    public boolean updateArmor(ArmorDTO newDTO) throws RemoteException {
        List<ArmorDTO> list = readFile();
        if (list != null && newDTO != null){
            for (ArmorDTO dto : list){
                if (dto.getArmorID().equals(newDTO.getArmorID())){
                    dto.setArmorID(newDTO.getArmorID());
                    dto.setClassfication(newDTO.getClassfication());
                    dto.setDefense(newDTO.getDefense());
                    dto.setDescription(newDTO.getDescription());
                    dto.setStatus(newDTO.getStatus());
                    dto.setTimeOfCreate(newDTO.getTimeOfCreate());
                    writeFille(list);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean removeArmor(String id) throws RemoteException {
        List<ArmorDTO> list = readFile();
        for (ArmorDTO dto : list) {
            if (dto.getArmorID().equals(id)) {
                list.remove(dto);
                writeFille(list);
                return true;
            }
        }
        return false;
    }

//    public static void main(String[] args) throws RemoteException {
//        ArmorServer obj = new ArmorServer();
//        ArmorDTO dto = new ArmorDTO("id1", "kssksksks", new Date(), 0, "213", "123");
//        obj.removeArmor(dto.getArmorID());
//    }
}
