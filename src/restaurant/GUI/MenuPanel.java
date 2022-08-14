package restaurant.GUI;

import restaurant.*;
import restaurant.MenuItem;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel{
    private JTable menuTable;

    private JTable menuSectionTable;

    private Employee employee;

    private Restaurant restaurant;

    public MenuPanel(Employee employee, Restaurant restaurant) {
        this.employee = employee;
        this.restaurant = restaurant;

        this.setLayout(null);

        JPanel tablePanel = new JPanel(new GridLayout(0 ,2));
        tablePanel.setBounds(31, 37, 664, 444);
        this.add(tablePanel);

        JScrollPane menuSectionScrollPanel = new JScrollPane();
        menuSectionScrollPanel.setBounds(31, 37, 664, 444);
        tablePanel.add(menuSectionScrollPanel);

        menuSectionTable = new JTable();
        menuSectionTable.setBounds(26, 23, 676, 470);
        menuSectionScrollPanel.setViewportView(menuSectionTable);

        JScrollPane menuScrollpanel = new JScrollPane();
        menuScrollpanel.setBounds(31, 37, 664, 444);
        tablePanel.add(menuScrollpanel);

        menuTable = new JTable();
        menuTable.setBounds(26, 23, 676, 470);

        prepareMenuSectionTable();
        prepareMenuTable();
        menuScrollpanel.setViewportView(menuTable);

        JButton newMenuSectionBtn = new JButton("Yeni Menü Başlığı ekle");
        newMenuSectionBtn.setBounds(170, 10, 195, 21);
        this.add(newMenuSectionBtn);
        newMenuSectionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newMenuSectionAction();
            }
        });

        JButton newMenuItemBtn = new JButton("Yeni Menu Item ekle");
        newMenuItemBtn.setBounds(370, 10, 170, 21);
        this.add(newMenuItemBtn);
        newMenuItemBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newMenuItem();
            }
        });

        JButton editBtn = new JButton("Menu Item düzenle");
        editBtn.setBounds(540, 10, 151, 21);
        editBtn.setEnabled(false);
        this.add(editBtn);

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBtnAction();
            }
        });

        ListSelectionModel selectionModel = menuTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                editBtn.setEnabled(true);
            }
        });
    }

    private void prepareMenuTable() {
        String[] headers = {"Menu Item ID", "Isim", "Fiyat", "Tür"};
        DefaultTableModel model = new DefaultTableModel(headers,0)  {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.menuTable.setModel(model);
        model.setDataVector(getMenuData(), headers);
        RestaurantStream.save(restaurant);
    }

    private void prepareMenuSectionTable() {
        String[] headers = {"Menu bölüm ID", "Başlık"};
        DefaultTableModel model = new DefaultTableModel(headers,0)  {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.menuSectionTable.setModel(model);
        model.setDataVector(getMenuSectionData(), headers);
        RestaurantStream.save(restaurant);
    }

    private void newMenuItem() {
        String[] menuSection = restaurant.getMenuSectionAsList();

        JTextField menuItemNameField  = new JTextField();
        JTextField menuItemQuantityField  = new JTextField();
        JComboBox<String> menuSectionPart = new JComboBox<>(menuSection);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Menu Item İsmi:"));
        panel.add(menuItemNameField);
        panel.add(new JLabel("Menu Başlığı:"));
        panel.add(menuSectionPart);
        panel.add(new JLabel("Fiyat:"));
        panel.add(menuItemQuantityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Yeni Menu Item Ekle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                ((Manager) employee).addMenuItem(menuItemNameField.getText(), Double.parseDouble(menuItemQuantityField.getText()), (String) menuSectionPart.getSelectedItem());

                JPanel resultPanel = new JPanel(new GridLayout(0, 1));
                resultPanel.add(new JLabel(menuItemNameField.getText() +  " sisteme eklendi."));

                JOptionPane.showMessageDialog(null, resultPanel, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
                prepareMenuTable();
            } catch (Exception e) {
                JPanel resultPanel1 = new JPanel(new GridLayout(0, 1));
                resultPanel1.add(new JLabel("Gecersiz değerler girildi. Lütfen düzgün değerler ile tekrar deneyin."));
                JOptionPane.showMessageDialog(null, resultPanel1, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
            }
        }
    }

    private Object[][] getMenuData() {
        Object[][] rowArray = new Object[restaurant.getMenuItems().size()][4];
        for(int i = 0; i < restaurant.getMenuItems().size(); i++) {
            MenuItem menuItem = restaurant.getMenuItems().get(i);
            rowArray[i][0] = menuItem.getMenuItemID();
            rowArray[i][1] = menuItem.getTitle();
            rowArray[i][2] = menuItem.getPrice();
            rowArray[i][3] = menuItem.getMenuSection().getTitle();
        }
        return rowArray;
    }

    private Object[][] getMenuSectionData() {
        Object[][] rowArray = new Object[restaurant.getMenuSections().size()][2];
        for(int i = 0; i < restaurant.getMenuSections().size(); i++) {
            MenuSection menuSection = restaurant.getMenuSections().get(i);
            rowArray[i][0] = menuSection.getMenuSectionID();
            rowArray[i][1] = menuSection.getTitle();
        }
        return rowArray;
    }
    private void editBtnAction() {
        String[] menuSection = restaurant.getMenuSectionAsList();

        int selectedIndex = menuTable.getSelectedRow();
        MenuItem selectedMenu = restaurant.getMenuItems().get(selectedIndex);

        JComboBox<String> menuSectionPart = new JComboBox<>(menuSection);
        JTextField priceField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Fiyat:"));
        priceField.setText(String.valueOf(selectedMenu.getPrice()));
        panel.add(priceField);
        panel.add(new JLabel("Menu Başlığı:"));
        panel.add(menuSectionPart);

        int result = JOptionPane.showConfirmDialog(null, panel, "Menu item düzenle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {;
                selectedMenu.setPrice(Double.parseDouble(priceField.getText()));
                employee.updateMenuItem(selectedMenu, selectedIndex, (String) menuSectionPart.getSelectedItem());
                RestaurantStream.save(restaurant);

                JPanel resultPanel = new JPanel(new GridLayout(0, 1));
                resultPanel.add(new JLabel("Sipariş güncellendi."));

                JOptionPane.showMessageDialog(null, resultPanel, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
                prepareMenuTable();
            } catch (Exception e) {
                JPanel resultPanel1 = new JPanel(new GridLayout(0, 1));
                resultPanel1.add(new JLabel("Gecersiz değerler girildi. Lütfen düzgün değerler ile tekrar deneyin."));
                JOptionPane.showMessageDialog(null, resultPanel1, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
            }
        }
    }

    private void newMenuSectionAction() {
        JTextField menuSectionNameField  = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Menu Başlığı İsmi:"));
        panel.add(menuSectionNameField);


        int result = JOptionPane.showConfirmDialog(null, panel, "Yeni Menu Başlığı Ekle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            ((Manager) employee).addMenuSection(menuSectionNameField.getText());

            JPanel resultPanel = new JPanel(new GridLayout(0, 1));
            resultPanel.add(new JLabel(menuSectionNameField.getText() +  " sisteme eklendi."));

            JOptionPane.showMessageDialog(null, resultPanel, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
            prepareMenuSectionTable();
        }
    }
}
