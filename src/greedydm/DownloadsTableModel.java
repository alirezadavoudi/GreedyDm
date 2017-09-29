/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package greedydm;

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

// This class manages the download table's data.
public class DownloadsTableModel extends AbstractTableModel implements Observer {
    
    // These are the names for the table's columns.
    private static final String[] columnNames = {"URL", "Size","Progress","Status","Speed"};
    
    // These are the classes for each column's values.
    private static final Class[] columnClasses = {String.class,
    String.class, JProgressBar.class, String.class , String.class};
    
    // The table's list of downloads.
    private ArrayList<DownloadPack> downloadList = new ArrayList<DownloadPack>();
    
    // Add a new download to the table.
    public void addDownload(DownloadPack downloadPack) {
        
        // Register to be notified when the download changes.
        downloadPack.addObserver(this);
        
        downloadList.add(downloadPack);
        
        // Fire table row insertion notification to table.
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }
    
    // Get a download for the specified row.
    public DownloadPack getDownload(int row) {
        return downloadList.get(row);
    }
    
    // Remove a download from the list.
    public void clearDownload(int row) {
        downloadList.remove(row);
        
        // Fire table row deletion notification to table.
        fireTableRowsDeleted(row, row);
    }
    
    // Get table's column count.
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    // Get a column's name.
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    // Get a column's class.
    @Override
    public Class getColumnClass(int col) {
        return columnClasses[col];
    }
    
    // Get table's row count.
    @Override
    public int getRowCount() {
        return downloadList.size();
    }
    
    // Get value for a specific row and column combination.
    @Override
    public Object getValueAt(int row, int col) {
        
        DownloadPack downloadPack = downloadList.get(row);
        switch (col) {
            case 0: // URL
                return downloadPack.getUrl();
            case 1: // Size
                int size = downloadPack.getSize();
                return (size == -1) ? "" : Integer.toString(size);
            case 2: // Progress
                return new Float(downloadPack.getProgress());
            case 3: // Status
                return downloadPack.getStatus();
            case 4: // Speed
                return downloadPack.getSpeed();
        }
        return "";
    }
    
  /* Update is called when a Download notifies its
     observers of any changes */
    @Override
    public void update(Observable o, Object arg) {
        int index = downloadList.indexOf(o);
        
        // Fire table row update notification to table.
        fireTableRowsUpdated(index, index);
        
    }
}