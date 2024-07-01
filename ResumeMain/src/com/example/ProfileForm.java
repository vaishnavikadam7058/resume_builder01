package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import org.jdatepicker.impl.*;

public class ProfileForm extends JFrame {
    private JTextField nameField, emailField, addressField, birthdateField;
    private JComboBox<String> purposeComboBox;
    private JButton editButton, saveButton, cancelButton;
    private JDatePickerImpl datePicker;
    private Connection connection;

    public ProfileForm() {
        setTitle("Profile Section");
        setSize(500, 500); // Increased the frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize database
        initializeDatabase();

        // Show profile section directly
        showProfileSection();
    }

    private void initializeDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/resume_main";
            String user = "root";
            String password = "root@123";

            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showProfileSection() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(10, 10, 10, 10); // Increased padding

        nameField = new JTextField(20); // Increased text field size
        emailField = new JTextField(20); // Increased text field size
        addressField = new JTextField(20); // Increased text field size
        birthdateField = new JTextField(20); // Field to display selected date
        birthdateField.setEditable(false); // Make it non-editable
        String[] purposes = {"Student", "Small business", "Teacher", "Large company", "Non-profit or charity", "Personal"};
        purposeComboBox = new JComboBox<>(purposes);

        // Initialize the date picker
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // Add action listener to the date picker
        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When a date is selected, display it in the birthdateField
                birthdateField.setText(datePicker.getJFormattedTextField().getText());
            }
        });

        editButton = new JButton("Edit");
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Name:"), c);
        c.gridx = 1;
        panel.add(nameField, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Email address:"), c);
        c.gridx = 1;
        panel.add(emailField, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Address:"), c);
        c.gridx = 1;
        panel.add(addressField, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(new JLabel("What will you be using Canva for?"), c);
        c.gridx = 1;
        panel.add(purposeComboBox, c);

        c.gridx = 0;
        c.gridy = 4;
        panel.add(new JLabel("Birthdate:"), c);
        c.gridx = 1;
        panel.add(birthdateField, c); // Add the birthdateField to display selected date
        c.gridy = 5;
        panel.add(datePicker, c); // Add the date picker below the birthdateField

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, c);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Enable editing of name, email, address, and birthdate fields
                nameField.setEditable(true);
                emailField.setEditable(true);
                addressField.setEditable(true);
                datePicker.getComponent(1).setEnabled(true);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the changes to the profile
                saveProfile();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cancel the changes and reset the fields
                resetFields();
            }
        });

        getContentPane().removeAll();
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void saveProfile() {
        String name = nameField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String purpose = (String) purposeComboBox.getSelectedItem();
        String birthdateStr = birthdateField.getText();
        java.sql.Date birthdate = null;

        try {
            birthdate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(birthdateStr).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            String query = "INSERT INTO profile (name, email, address, purpose, birthdate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, purpose);
            preparedStatement.setDate(5, birthdate);
            preparedStatement.executeUpdate();

            nameField.setEditable(false);
            emailField.setEditable(false);
            addressField.setEditable(false);
            datePicker.getComponent(1).setEnabled(false);

            JOptionPane.showMessageDialog(this, "Profile saved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resetFields() {
        nameField.setText("");
        emailField.setText("");
        addressField.setText("");
        birthdateField.setText(""); // Clear the birthdateField
        purposeComboBox.setSelectedIndex(0);
        datePicker.getModel().setValue(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProfileForm();
            }
        });
    }
}

class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private final String datePattern = "yyyy-MM-dd";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}
