package restaurant.GUI;

import restaurant.Employee;
import restaurant.Reservation;
import restaurant.Restaurant;
import restaurant.RestaurantStream;
import restaurant.enums_interfaces.ReservationStatus;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Calendar;

public class ReservationPanel extends JPanel {
    private JTable reservationTable;
    private Employee employee;
    private Restaurant restaurant;

    public ReservationPanel(Employee employee, Restaurant restaurant) {
        this.restaurant = restaurant;
        this.employee = employee;

        this.setLayout(null);

        JScrollPane reservationScrollPanel = new JScrollPane();
        reservationScrollPanel.setBounds(31, 37, 664, 444);
        this.add(reservationScrollPanel);

        reservationTable = new JTable();
        reservationTable.setBounds(26, 23, 676, 470);
        this.add(reservationTable);
        prepareReservationTable();
        reservationScrollPanel.setViewportView(reservationTable);

        JButton newReservationButton = new JButton("Yeni Rezervasyon");
        newReservationButton.setBounds(370, 10, 140, 21);
        this.add(newReservationButton);
        newReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newReservationAction();
            }
        });


        JButton editBtn = new JButton("Rezervasyon düzenle");
        editBtn.setBounds(520, 10, 180, 21);
        editBtn.setEnabled(false);
        this.add(editBtn);
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBtnAction();
            }
        });

        ListSelectionModel selectionModel = reservationTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                editBtn.setEnabled(true);
            }
        });
    }

    private void prepareReservationTable() {
        String[] headers = {"Rezervasyon ID", "Durum", "Alınma Tarihi", "İnsan sayısı", "Müşteri", "Müşteri Numarası", "Masalar", "Not"};
        DefaultTableModel model = new DefaultTableModel(headers,1)   {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.reservationTable.setModel(model);
        model.setDataVector(getRezervationTables(), headers);
        RestaurantStream.save(restaurant);
    }

    private Object[][] getRezervationTables() {
        Object[][] rowArray = new Object[restaurant.getReservations().size()][8];
        for(int i = 0; i < restaurant.getReservations().size(); i++) {
            Reservation reservation = restaurant.getReservations().get(i);
            rowArray[i][0] = reservation.getReservationID();
            rowArray[i][1] = reservation.getStatus();
            rowArray[i][2] = reservation.getTimeOfReservation();
            rowArray[i][3] = reservation.getPeopleCount();
            rowArray[i][4] = reservation.getCustomer().getName();
            rowArray[i][5] = reservation.getCustomer().getPhone();
            rowArray[i][6] = reservation.getTablesAsString();
            rowArray[i][7] = reservation.getNotes();
        }
        return rowArray;
    }

    private void newReservationAction() {
        JTextField peopleCountField  = new JTextField();
        JTextField dayField  = new JTextField(2);
        JTextField monthField  = new JTextField(2);
        JTextField yearField  = new JTextField(4);
        JTextField hourField  = new JTextField(2);
        JTextField minuteField  = new JTextField(2);
        JTextField customerNameField  = new JTextField();
        JTextField customerPhoneField  = new JTextField();
        JTextField noteField = new JTextField();

        Calendar cal = Calendar.getInstance();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("İnsan Sayısı:"));
        panel.add(peopleCountField);

        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.add(new JLabel("Gün:"));
        dayField.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        datePanel.add(dayField);

        datePanel.add(new JLabel("Ay:"));
        monthField.setText(String.valueOf(cal.get(Calendar.MONTH) + 1));
        datePanel.add(monthField);

        datePanel.add(new JLabel("Yıl:"));
        yearField.setText(String.valueOf(cal.get(Calendar.YEAR)));
        datePanel.add(yearField);

        datePanel.add(new JLabel("Saat:"));
        hourField.setText(String.valueOf(cal.get(Calendar.HOUR)));
        datePanel.add(hourField);

        datePanel.add(new JLabel(":"));
        minuteField.setText(String.valueOf(cal.get(Calendar.MINUTE)));
        datePanel.add(minuteField);
        panel.add(datePanel);

        panel.add(new JLabel("Müşteri İsmi:"));
        panel.add(customerNameField);
        panel.add(new JLabel("Müşteri Numarası:"));
        panel.add(customerPhoneField);
        panel.add(new JLabel("Not:"));
        panel.add(noteField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Yeni Rezervasyon",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            JPanel resultPanel = new JPanel(new GridLayout(0, 1));

            JList list = new JList(restaurant.getTableLocationAsList()); //data has type Object[]
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            list.setLayoutOrientation(JList.VERTICAL);
            list.setVisibleRowCount(-1);

            JScrollPane listScroller = new JScrollPane(list);
            listScroller.setPreferredSize(new Dimension(250, 80));
            resultPanel.add(list);

            int result1 = JOptionPane.showConfirmDialog(null, resultPanel, "Masaları Seç",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result1 == JOptionPane.OK_OPTION) {
                try {
                    LocalDateTime reservationTime = LocalDateTime.of(Integer.parseInt(yearField.getText()),
                            Integer.parseInt(monthField.getText()),
                            Integer.parseInt(dayField.getText()),
                            Integer.parseInt(hourField.getText()),
                            Integer.parseInt(minuteField.getText())
                    );
                    employee.addReservation(Integer.parseInt(peopleCountField.getText()), reservationTime, customerNameField.getText(), customerPhoneField.getText(), noteField.getText(), restaurant.findTableFromLocationList(list.getSelectedValuesList()));
                    prepareReservationTable();

                    JPanel resultPanel1 = new JPanel(new GridLayout(0, 1));
                    resultPanel1.add(new JLabel("Rezervasyon oluşturuldu."));
                    JOptionPane.showMessageDialog(null, resultPanel1, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
                } catch (Exception e) {
                    JPanel resultPanel1 = new JPanel(new GridLayout(0, 1));
                    resultPanel1.add(new JLabel("Gecersiz değerler girildi. Lütfen düzgün değerler ile tekrar deneyin."));
                    JOptionPane.showMessageDialog(null, resultPanel1, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
                }

            }
        }
    }

    private void editBtnAction() {
        ReservationStatus[] values = ReservationStatus.values();
        String[] valuesAsString = new String[values.length];
        for(int i = 0; i < values.length; i++) {
            valuesAsString[i] = values[i].name();
        }

        int selectedIndex = reservationTable.getSelectedRow();
        Reservation selectedReservation = restaurant.getReservations().get(selectedIndex);

        JComboBox<String> combo = new JComboBox<>(valuesAsString);
        JTextField peopleCount = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Reservazyon durumu:"));
        panel.add(combo);
        panel.add(new JLabel("Kişi sayısı:"));
        peopleCount.setText(String.valueOf(selectedReservation.getPeopleCount()));
        panel.add(peopleCount);

        int result = JOptionPane.showConfirmDialog(null, panel, "Reservazyonu düzenle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            selectedReservation.setStatus(ReservationStatus.valueOf((String) combo.getSelectedItem()));
            selectedReservation.setPeopleCount(Integer.parseInt(peopleCount.getText()));

            employee.updateReservation(selectedReservation, selectedIndex);
            RestaurantStream.save(restaurant);

            JPanel resultPanel = new JPanel(new GridLayout(0, 1));
            resultPanel.add(new JLabel("Rezervasyon güncellendi."));

            JOptionPane.showMessageDialog(null, resultPanel, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
            prepareReservationTable();
        }
    }
}
