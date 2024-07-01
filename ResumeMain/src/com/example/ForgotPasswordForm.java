package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgotPasswordForm extends JFrame {
    private JTextField textFieldUsername;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton resetPasswordButton;
    private JPanel forgotPasswordPanel;

    public ForgotPasswordForm() {
        // Initialize the components
        textFieldUsername = new JTextField(20);
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        resetPasswordButton = new JButton("Reset Password");
        forgotPasswordPanel = new JPanel();

        // Set the layout and add components
        forgotPasswordPanel.setLayout(new BoxLayout(forgotPasswordPanel, BoxLayout.Y_AXIS));
        forgotPasswordPanel.add(new JLabel("Username:"));
        forgotPasswordPanel.add(textFieldUsername);
        forgotPasswordPanel.add(new JLabel("New Password:"));
        forgotPasswordPanel.add(newPasswordField);
        forgotPasswordPanel.add(new JLabel("Confirm Password:"));
        forgotPasswordPanel.add(confirmPasswordField);
        forgotPasswordPanel.add(resetPasswordButton);

        // Set frame properties
        setTitle("Forgot Password Form");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(forgotPasswordPanel);
        setLocationRelativeTo(null);

        // Add action listener to the button
        resetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match");
                    return;
                }

                if (Database.resetPassword(username, newPassword)) {
                    JOptionPane.showMessageDialog(null, "Password reset successful");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Password reset failed");
                }
            }
        });
    }
}
