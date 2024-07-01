package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class EducationSection extends JFrame {
    private JTextField collegeNameField;
    private JTextField degreeField;
    private JTextField fromDateField;
    private JTextField toDateField;
    private JButton saveButton;
    private JButton nextButton;
    private JButton backButton;

    public EducationSection() {
        setTitle("Education Section");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        JLabel collegeNameLabel = new JLabel("College Name:");
        collegeNameField = new JTextField();

        JLabel degreeLabel = new JLabel("Degree:");
        degreeField = new JTextField();

        JLabel fromDateLabel = new JLabel("From Date:");
        fromDateField = new JTextField();

        JLabel toDateLabel = new JLabel("To Date:");
        toDateField = new JTextField();

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEducationDetails();
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

        add(collegeNameLabel);
        add(collegeNameField);
        add(degreeLabel);
        add(degreeField);
        add(fromDateLabel);
        add(fromDateField);
        add(toDateLabel);
        add(toDateField);
        add(saveButton);
        add(nextButton);
        add(backButton);
    }

    private void saveEducationDetails() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root", "root@123");
            String sql = "INSERT INTO education (college_name, degree, from_date, to_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, collegeNameField.getText());
            pstmt.setString(2, degreeField.getText());
            pstmt.setString(3, fromDateField.getText());
            pstmt.setString(4, toDateField.getText());

            pstmt.executeUpdate();
            conn.close();

            JOptionPane.showMessageDialog(this, "Education details saved successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save education details.");
        }
    }

    private void openNextForm() {
        new ExperienceForm().setVisible(true);
        this.dispose();
    }

    private void openPreviousForm() {
        new PersonalDetailsForm().setVisible(true);
        this.dispose();
    }
}
