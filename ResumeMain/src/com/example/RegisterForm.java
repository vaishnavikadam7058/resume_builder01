package com.example;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterForm extends JFrame {
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JTextField textFieldEmail;
    private JButton registerButton;
    private JPanel registerPanel;

    public RegisterForm() {
       
        registerPanel = new JPanel();
        textFieldUsername = new JTextField(20);
        passwordField = new JPasswordField(20);
        textFieldEmail = new JTextField(20);
        registerButton = new JButton("Register");

        // Set layout manager for registerPanel
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));

        // Add components to registerPanel
        registerPanel.add(new JLabel("Username:"));
        registerPanel.add(textFieldUsername);
        registerPanel.add(new JLabel("Password:"));
        registerPanel.add(passwordField);
        registerPanel.add(new JLabel("Email:"));
        registerPanel.add(textFieldEmail);
        registerPanel.add(registerButton);

        
        setContentPane(registerPanel);

        
        setTitle("Register Form");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String password = new String(passwordField.getPassword());
                String email = textFieldEmail.getText();
                if (Database.register(username, password, email)) {
                    JOptionPane.showMessageDialog(null, "Registration successful");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Registration failed");
                }
            }
        });
    }
}
