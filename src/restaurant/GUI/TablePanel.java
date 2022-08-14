package restaurant.GUI;

import restaurant.*;
import restaurant.enums_interfaces.TableStatus;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablePanel extends JPanel {
    private JButton editBtn;
    private JTable tablesTable;
    private Employee employee;
    private Restaurant restaurant;

    public TablePanel(Employee employee, Restaurant restaurant) {
        this.employee = employee;
        this.restaurant = restaurant;

        this.setLayout(null);

        JScrollPane tableScrollPane = new JScrollPane();
        tableScrollPane.setBounds(31, 37, 664, 444);
        this.add(tableScrollPane);

        tablesTable = new JTable();
        tablesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablesTable.setBounds(26, 23, 676, 470);
        this.add(tablesTable);
        prepareTablesTable();
        tableScrollPane.setViewportView(tablesTable);

        JButton newTableButton = new JButton("Yeni Masa Ekle");
        newTableButton.setBounds(390, 10, 140, 21);
        this.add(newTableButton);
        newTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newTableButton();
            }
        });

        editBtn = new JButton("Masayı düzenle");
        editBtn.setBounds(540, 10, 140, 21);
        editBtn.setEnabled(false);
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBtnAction();
            }
        });

        this.add(editBtn);

        newTableButton.setVisible(false);
        if(employee instanceof Manager) {
            newTableButton.setVisible(true);
        }

        ListSelectionModel selectionModel = tablesTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                editBtn.setEnabled(true);
            }
        });
    }

    private void newTableButton() {
        JTextField locationField = new JTextField();
        JTextField capacityField  = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Masa Konumu:"));
        panel.add(locationField);
        panel.add(new JLabel("Maximum Kapasite:"));
        panel.add(capacityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Yeni Masa Ekle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            ((Manager) employee).addTable(Integer.parseInt(capacityField.getText()), locationField.getText());

            JPanel resultPanel = new JPanel(new GridLayout(0, 1));
            resultPanel.add(new JLabel("Masa " + locationField.getText() + " sisteme eklendi."));

            JOptionPane.showMessageDialog(null, resultPanel, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
            prepareTablesTable();
        }
    }

    private Object[][] getTablesData() {
        Object[][] rowArray = new Object[restaurant.getTables().size()][4];
        for(int i = 0; i < restaurant.getTables().size(); i++) {
            Table table = restaurant.getTables().get(i);
            rowArray[i][0] = table.getTableID();
            rowArray[i][1] = table.getLocationIdentifier();
            rowArray[i][2] = table.getMaxCapacity();
            rowArray[i][3] = table.getStatus();
        }
        return rowArray;
    }

    private void prepareTablesTable() {
        String[] headers = {"Masa ID", "Masa konumu", "max kapasite" , "Durum"};
        DefaultTableModel model = new DefaultTableModel(headers,0)   {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tablesTable.setModel(model);
        model.setDataVector(getTablesData(), headers);
        RestaurantStream.save(restaurant);
    }

    private void editBtnAction() {
        TableStatus[] values = TableStatus.values();
        String[] valuesAsString = new String[values.length];
        for(int i = 0; i < values.length; i++) {
            valuesAsString[i] = values[i].name();
        }

        int selectedIndex = tablesTable.getSelectedRow();
        Table selectedTable = restaurant.getTables().get(selectedIndex);

        JComboBox<String> combo = new JComboBox<>(valuesAsString);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Masa durumu:"));
        panel.add(combo);

        int result = JOptionPane.showConfirmDialog(null, panel, "Masayı düzenle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            selectedTable.setStatus(TableStatus.valueOf((String) combo.getSelectedItem()));
            employee.updateTable(selectedTable, selectedIndex);
            RestaurantStream.save(restaurant);

            JPanel resultPanel = new JPanel(new GridLayout(0, 1));
            resultPanel.add(new JLabel("Masa güncellendi."));

            JOptionPane.showMessageDialog(null, resultPanel, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
            prepareTablesTable();
        }
    }
}
