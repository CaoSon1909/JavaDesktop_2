/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import sonpc.dtos.ArmorDTO;
import sonpc.formats.DateFormatter;

/**
 *
 * @author ACER
 */
public class ArmorTableModel extends AbstractTableModel{

    String headers[];
    int indexes[];
    List<ArmorDTO> list = new ArrayList<>();

    public ArmorTableModel() {
    }

    public ArmorTableModel(String[] headers, int[] indexes) {
        this.headers = headers;
        this.indexes = indexes;
    }

    public List<ArmorDTO> getList() {
        return list;
    }
    
    public void loadData(List<ArmorDTO> armorList){
        if (armorList != null){
            for (ArmorDTO dto : armorList){
                this.list.add(dto);
            }
        }
    }
    
    @Override
    public int getRowCount() {
        return this.list.size();
    }

    @Override
    public int getColumnCount() {
        return indexes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArmorDTO armorDTO = this.list.get(rowIndex);
        switch (indexes[columnIndex]){
            case 1: return armorDTO.getArmorID();
            case 2: return armorDTO.getClassfication();
            case 3: 
                DateFormatter formatter = new DateFormatter();
                String dateString = formatter.DateToStringFormat(armorDTO.getTimeOfCreate());
                
                return dateString;
            case 4: return armorDTO.getDefense();
            case 5: return armorDTO.getDescription();
            case 6: return armorDTO.getStatus();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }
    
    
    
}
