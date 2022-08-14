package restaurant.GUI;

import restaurant.*;
import restaurant.enums_interfaces.AccountStatus;
import restaurant.enums_interfaces.EmployeeType;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeePanel extends JPanel {
    private JTable employeesTable;
    private Employee employee;
    private Restaurant restaurant;

    public EmployeePanel(Employee employee, Restaurant restaurant) {
        this.employee = employee;
        this.restaurant = restaurant;

        this.setLayout(null);
        JScrollPane employeeScrollPane = new JScrollPane();
        employeeScrollPane.setBounds(31, 37, 664, 444);
        this.add(employeeScrollPane);

        employeesTable = new JTable();
        this.add(employeesTable);
        prepareEmployeeTable();
        employeeScrollPane.setViewportView(employeesTable);

        JButton newEmployeeButton = new JButton("Yeni Çalışan ekle");
        newEmployeeButton.setBounds(390, 10, 140, 21);
        this.add(newEmployeeButton);

        newEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newEmployeeAddAction();
            }
        });

        JButton editBtn = new JButton("Calışanı düzenle");
        editBtn.setBounds(540, 10, 140, 21);
        editBtn.setEnabled(false);
        this.add(editBtn);

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBtnAction();
            }
        });

        ListSelectionModel selectionModel = employeesTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                editBtn.setEnabled(true);
            }
        });
    }

    private void newEmployeeAddAction() {
        String[] items = {"Şef", "Garson"};
        JComboBox<String> combo = new JComboBox<>(items);
        JTextField nameField = new JTextField();
        JTextField addressField  = new JTextField();
        JTextField emailField  = new JTextField();
        JTextField phoneField  = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(combo);
        panel.add(new JLabel("Tam İsim:"));
        panel.add(nameField);
        panel.add(new JLabel("Adres:"));
        panel.add(addressField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Telefon Numarası:"));
        panel.add(phoneField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Yeni Çalışan Ekle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {

            Employee newEmployee;
            if(combo.getSelectedItem().equals("Şef")) {
                newEmployee = ((Manager) employee).addEmployee(EmployeeType.Chef, nameField.getText(), addressField.getText(),
                        emailField.getText(), phoneField.getText());
            } else {
                newEmployee = ((Manager) employee).addEmployee(EmployeeType.Waiter, nameField.getText(), addressField.getText(),
                        emailField.getText(), phoneField.getText());
            }

            JPanel resultPanel = new JPanel(new GridLayout(0, 1));
            resultPanel.add(new JLabel(newEmployee.getName() + " sisteme eklendi."));
            resultPanel.add(new JLabel("Kullanıcı adı: " + newEmployee.getAccount().getUsername()));
            resultPanel.add(new JLabel("Şifre: " + newEmployee.getAccount().getPassword()));

            JOptionPane.showMessageDialog(null, resultPanel, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
            prepareEmployeeTable();
        }
    }

    private Object[][] getEmployeeData() {
        Object[][] rowArray = new Object[restaurant.getEmployees().size()][7];
        for(int i = 0; i < restaurant.getEmployees().size(); i++) {
            Employee employee1 = restaurant.getEmployees().get(i);
            rowArray[i][0] = employee1.getEmployeeID();
            rowArray[i][1] = employee1.getAccount().getStatus();
            rowArray[i][2] = employee1.getClass().getSimpleName();
            rowArray[i][3] = employee1.getName();
            rowArray[i][4] = employee1.getDateJoined();
            rowArray[i][5] = employee1.getEmail();
            rowArray[i][6] = employee1.getPhone();
        }
        return rowArray;
    }

    private void prepareEmployeeTable() {
        String[] headers = {"Çalışan ID", "Durum", "Görevi", "Tam İsim" , "İşi giriş tarihi", "email", "telefon"};
        DefaultTableModel model = new DefaultTableModel(headers,0)  {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.employeesTable.setModel(model);
        model.setDataVector(getEmployeeData(), headers);
        RestaurantStream.save(restaurant);
    }

    private void editBtnAction() {
        AccountStatus[] values = AccountStatus.values();
        String[] valuesAsString = new String[values.length];
        for(int i = 0; i < values.length; i++) {
            valuesAsString[i] = values[i].name();
        }

        int selectedIndex = employeesTable.getSelectedRow();
        Employee selectedEmployee = restaurant.getEmployees().get(selectedIndex);

        JComboBox<String> combo = new JComboBox<>(valuesAsString);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Calışan durumu:"));
        panel.add(combo);

        int result = JOptionPane.showConfirmDialog(null, panel, "Siparişi düzenle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            selectedEmployee.getAccount().setStatus(AccountStatus.valueOf((String) combo.getSelectedItem()));
            ((Manager)employee).updateEmployee(selectedEmployee, selectedIndex);
            RestaurantStream.save(restaurant);

            JPanel resultPanel = new JPanel(new GridLayout(0, 1));
            resultPanel.add(new JLabel("Sipariş güncellendi."));

            JOptionPane.showMessageDialog(null, resultPanel, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
            prepareEmployeeTable();
        }
    }
}
