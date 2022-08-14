package restaurant.GUI;

import restaurant.MenuItem;
import restaurant.*;
import restaurant.enums_interfaces.OrderStatus;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OrderPanel extends JPanel {

    private JTable orderTable;

    private Employee employee;

    private Restaurant restaurant;

    public OrderPanel(Employee employee, Restaurant restaurant) {
        this.employee = employee;
        this.restaurant = restaurant;

        this.setLayout(null);

        JScrollPane orderScrollPanel = new JScrollPane();
        orderScrollPanel.setBounds(31, 37, 664, 444);
        this.add(orderScrollPanel);

        orderTable = new JTable();
        orderTable.setBounds(26, 23, 676, 470);
        this.add(orderTable);

        prepareOrderTable();
        orderScrollPanel.setViewportView(orderTable);

        JButton newOrderBtn = new JButton("Yeni Sipariş ekle");
        newOrderBtn.setBounds(390, 10, 140, 21);
        this.add(newOrderBtn);
        newOrderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newOrderAction();
            }
        });

        JButton editBtn = new JButton("Siparişi düzenle");
        editBtn.setBounds(540, 10, 140, 21);
        editBtn.setEnabled(false);
        this.add(editBtn);
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBtnAction();
            }
        });

        ListSelectionModel selectionModel = orderTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                editBtn.setEnabled(true);
            }
        });

    }
    private void prepareOrderTable() {
        String[] headers = {"Sipariş ID", "Durum", "Masa" , "Yaratılma Zamanı", "Garson", "Şef", "Yemekler", "Toplam Fiyat"};
        DefaultTableModel model = new DefaultTableModel(headers,0)  {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.orderTable.setModel(model);
        model.setDataVector(getOrderData(), headers);
        RestaurantStream.save(restaurant);
    }

    private void newOrderAction() {
        String[] tables = restaurant.getTableLocationAsList();
        String[] waiters = restaurant.getWaitersAsList();
        String[] chefs = restaurant.getChefAsList();

        JComboBox<String> tableField = new JComboBox<>(tables);
        JComboBox<String> waiterNameField = new JComboBox<>(waiters);
        JComboBox<String> chefNameField = new JComboBox<>(chefs);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Masa:"));
        panel.add(tableField);
        panel.add(new JLabel("Garson:"));
        panel.add(waiterNameField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Yeni Sipariş Oluştur",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {

            JPanel resultPanel = new JPanel(new GridLayout(0, 1));

            JList list = new JList(restaurant.getMenuItemAsList());
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            list.setLayoutOrientation(JList.VERTICAL);
            list.setVisibleRowCount(-1);

            JScrollPane listScroller = new JScrollPane(list);
            listScroller.setPreferredSize(new Dimension(250, 80));
            resultPanel.add(list);

            Waiter waiter = (Waiter) restaurant.findEmployeeByName((String) waiterNameField.getSelectedItem());
            Table table = restaurant.findTableFromLocation((String) tableField.getSelectedItem());

            int result1 = JOptionPane.showConfirmDialog(null, resultPanel, "Yemekleri Seç",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result1 == JOptionPane.OK_OPTION) {
                List<MenuItem> menuItems = restaurant.getMenuItemFromList(list.getSelectedValuesList());
                employee.addOrder(new Meal(menuItems), table, waiter);

                JPanel resultPanel1 = new JPanel(new GridLayout(0, 1));
                resultPanel1.add(new JLabel("Sipariş oluşturuldu."));

                JOptionPane.showMessageDialog(null, resultPanel1, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
                prepareOrderTable();
            }
        }
    }

    private Object[][] getOrderData() {
        Object[][] rowArray = new Object[restaurant.getOrders().size()][7];
        for(int i = 0; i < restaurant.getOrders().size(); i++) {
            Order order = restaurant.getOrders().get(i);
            rowArray[i][0] = order.getOrderID();
            rowArray[i][1] = order.getStatus();
            rowArray[i][2] = order.getTable().getLocationIdentifier();
            rowArray[i][3] = order.getCreationTime();
            rowArray[i][4] = order.getWaiter().getName();
            rowArray[i][5] = order.getMealsAsString();
            rowArray[i][6] = order.getMeal().getQuantity();
        }
        return rowArray;
    }

    private void editBtnAction() {
        OrderStatus[] values = OrderStatus.values();
        String[] valuesAsString = new String[values.length];
        for(int i = 0; i < values.length; i++) {
            valuesAsString[i] = values[i].name();
        }

        int selectedIndex = orderTable.getSelectedRow();
        Order selectedOrder = restaurant.getOrders().get(selectedIndex);

        JComboBox<String> combo = new JComboBox<>(valuesAsString);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Sipariş durumu:"));
        panel.add(combo);

        int result = JOptionPane.showConfirmDialog(null, panel, "Siparişi düzenle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            selectedOrder.setStatus(OrderStatus.valueOf((String) combo.getSelectedItem()));
            employee.updateOrder(selectedOrder, selectedIndex);
            RestaurantStream.save(restaurant);

            JPanel resultPanel = new JPanel(new GridLayout(0, 1));
            resultPanel.add(new JLabel("Sipariş güncellendi."));

            JOptionPane.showMessageDialog(null, resultPanel, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
            prepareOrderTable();
        }
    }
}
