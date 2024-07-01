package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ExperienceForm extends JFrame {
    private JTextField companyNameField;
    private JTextField roleField;
    private JTextField fromDateField;
    private JTextField toDateField;
    private JButton saveButton;
    private JButton nextButton;
    private JButton backButton;

    public ExperienceForm() {
        setTitle("Experience Section");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        JLabel companyNameLabel = new JLabel("Company Name:");
        companyNameField = new JTextField();

        JLabel roleLabel = new JLabel("Role:");
        roleField = new JTextField();

        JLabel fromDateLabel = new JLabel("From Date:");
        fromDateField = new JTextField();

        JLabel toDateLabel = new JLabel("To Date:");
        toDateField = new JTextField();

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveExperienceDetails();
            }
        });

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNextForm();
            }
        });

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPreviousForm();
            }
        });

        add(companyNameLabel);
        add(companyNameField);
        add(roleLabel);
        add(roleField);
        add(fromDateLabel);
        add(fromDateField);
        add(toDateLabel);
        add(toDateField);
        add(saveButton);
        add(nextButton);
        add(backButton);
    }

    private void saveExperienceDetails() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root", "root@123");
            String sql = "INSERT INTO experience (company_name, role, from_date, to_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, companyNameField.getText());
            pstmt.setString(2, roleField.getText());
            pstmt.setString(3, fromDateField.getText());
            pstmt.setString(4, toDateField.getText());

            pstmt.executeUpdate();
            conn.close();

            JOptionPane.showMessageDialog(this, "Experience details saved successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save experience details.");
        }
    }

    private void openNextForm() {
        new ProjectForm().setVisible(true);
        this.dispose();
    }

    private void openPreviousForm() {
        new EducationSection().setVisible(true);
        this.dispose();
    }
}
