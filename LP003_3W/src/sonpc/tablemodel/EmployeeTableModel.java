/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonpc.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import sonpc.dtos.TblEmployeeDTO;

/**
 *
 * @author ACER
 */
public class EmployeeTableModel extends AbstractTableModel{

    String[] headers;
    int[] indexes;
    List<TblEmployeeDTO> list = new ArrayList<>();

    public List<TblEmployeeDTO> getList() {
        return list;
    }

    public EmployeeTableModel() {
    }

    public EmployeeTableModel(String[] headers, int[] indexes) {
        this.headers = headers;
        this.indexes = indexes;
    }
    
    public void loadData(List<TblEmployeeDTO> list){
        if (list != null){
            for (TblEmployeeDTO dto : list){
                this.list.add(dto);
            }
        }
    }
    
    
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return indexes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TblEmployeeDTO dto = this.list.get(rowIndex);
        switch(indexes[columnIndex]){
            case 1: return dto.getEmpID();
            case 2: return dto.getFullName();
            case 3: return dto.getPhone();
            case 4: return dto.getEmail();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }
    
    
    
}
