package restaurant.GUI;

import restaurant.Employee;
import restaurant.Manager;
import restaurant.Restaurant;
import restaurant.RestaurantStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private OrderPanel orderPanel;
    private JPanel contentPane;
    private ReservationPanel reservationPanel;
    private TablePanel tablePanel;
    private EmployeePanel employeesPanel;
    private MenuPanel menuPanel;
    private Restaurant restaurant;

    public MainFrame(Employee employee, Restaurant restaurant) {
        this.restaurant = restaurant;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 753, 593);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 10, 729, 505);
        contentPane.add(tabbedPane);

        orderPanel = new OrderPanel(employee, restaurant);
        tabbedPane.addTab("Siparişler", null, orderPanel, null);

        tablePanel = new TablePanel(employee, restaurant);
        tabbedPane.addTab("Masalar", null, tablePanel, null);

        this.reservationPanel = new ReservationPanel(employee, restaurant);
        tabbedPane.addTab("Rezervasyonlar", null, reservationPanel, null);

        menuPanel = new MenuPanel(employee, restaurant);
        tabbedPane.addTab("Menu", null, menuPanel, null);

        if(employee instanceof Manager) {
            employeesPanel = new EmployeePanel(employee, restaurant);
            tabbedPane.addTab("Çalışanlar", null, employeesPanel, null);
        }
        JButton changePwBtn = new JButton("Şifreyi Yenile");
        changePwBtn.setBounds(580, 525, 120, 21);
        contentPane.add(changePwBtn);

        changePwBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassAction(employee);
            }
        });

        this.setVisible(true);
    }

    private void changePassAction(Employee employee) {
        JTextField oldPWField  = new JTextField();
        JTextField newPWField  = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Eski Şifre:"));
        panel.add(oldPWField);
        panel.add(new JLabel("Yeni Şifre:"));
        panel.add(newPWField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Şifre Değiştir",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            boolean success = employee.changePassword(oldPWField.getText(), newPWField.getText());

            JPanel resultPanel = new JPanel(new GridLayout(0, 1));
            if(success) {
                resultPanel.add(new JLabel("Şifre değiştirildi."));
                RestaurantStream.save(restaurant);
            } else {
                resultPanel.add(new JLabel("Eski şifre yanlış!"));
            }

            JOptionPane.showMessageDialog(null, resultPanel, "Sonuç Paneli", JOptionPane.CLOSED_OPTION);
        }
    }
}
