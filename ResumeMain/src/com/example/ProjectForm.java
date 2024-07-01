package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ProjectForm extends JFrame {
    private JTextField projectNameField;
    private JTextField projectDescriptionField;
    private JButton saveButton;
    private JButton nextButton;
    private JButton backButton;

    public ProjectForm() {
        setTitle("Projects Section");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        JLabel projectNameLabel = new JLabel("Project Name:");
        projectNameField = new JTextField();

        JLabel projectDescriptionLabel = new JLabel("Project Description:");
        projectDescriptionField = new JTextField();

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProjectDetails();
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

        add(projectNameLabel);
        add(projectNameField);
        add(projectDescriptionLabel);
        add(projectDescriptionField);
        add(saveButton);
        add(nextButton);
        add(backButton);
    }

    private void saveProjectDetails() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root", "root@123");
            String sql = "INSERT INTO projects (project_name, project_description) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, projectNameField.getText());
            pstmt.setString(2, projectDescriptionField.getText());

            pstmt.executeUpdate();
            conn.close();

            JOptionPane.showMessageDialog(this, "Project details saved successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save project details.");
        }
    }

    private void openNextForm() {
        new HobbiesForm().setVisible(true);
        this.dispose();
    }

    private void openPreviousForm() {
        new ExperienceForm().setVisible(true);
        this.dispose();
    }
}
