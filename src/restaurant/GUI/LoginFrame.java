package restaurant.GUI;

import restaurant.Employee;
import restaurant.Restaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginFrame extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField idTextField;
    private JPasswordField passwordPF;
    private JLabel errorMessage;
    private Restaurant restaurant;


    public LoginFrame(Restaurant restaurant) {
        this.restaurant = restaurant;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        setLocationRelativeTo(null);


        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(this);
        loginBtn.setBounds(143, 183, 85, 21);
        contentPane.add(loginBtn);

        JLabel Header = new JLabel("Welcome to " + restaurant.getName());
        Header.setHorizontalAlignment(SwingConstants.CENTER);
        Header.setBounds(102, 18, 198, 31);
        contentPane.add(Header);

        idTextField = new JTextField();
        idTextField.setColumns(10);
        idTextField.setBounds(147, 85, 198, 21);
        contentPane.add(idTextField);

        JLabel employeeIDLabel = new JLabel("Email :");
        employeeIDLabel.setBounds(70, 85, 74, 21);
        contentPane.add(employeeIDLabel);

        JLabel passwordTFLabel = new JLabel("Password :");
        passwordTFLabel.setBounds(70, 132, 74, 21);
        contentPane.add(passwordTFLabel);

        this.getRootPane().setDefaultButton(loginBtn);

        passwordPF = new JPasswordField();
        passwordPF.setBounds(147, 133, 198, 19);
        contentPane.add(passwordPF);

        this.errorMessage = new JLabel("");
        errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        errorMessage.setBounds(2, 223, 200, 13);
        contentPane.add(errorMessage);
        errorMessage.setForeground(Color.red);

        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<Employee> employees = restaurant.getEmployees();
        String employeeID = this.idTextField.getText();
        String password = new String(this.passwordPF.getPassword());
        Employee employee = findEmployee(employees, employeeID, password);

        if(employee == null) {
            errorMessage.setText("Böyle bir çalışan bulunamadı.");
            return;
        }

        switch (employee.getAccount().getStatus()) {
            case KAPATILDI:
                errorMessage.setText("Bu hesap kapatılmıştır.");
                return;
            case IPTAL_EDILDI:
                errorMessage.setText("Bu hesap iptal edilmiştir.");
                return;
            case KARALISTEDE:
                errorMessage.setText("Bu hesap askıya alınmıştır.");
                return;
        }
        this.setVisible(false);
        new MainFrame(employee, restaurant);

    }

    private Employee findEmployee(ArrayList<Employee> employees, String employeeID, String password) {
        for (Employee employee: employees) {
            if(employee.getAccount().getUsername().equals(employeeID) && employee.getAccount().getPassword().equals(password)) {
                return employee;
            }
        }
        return null;
    }
}
