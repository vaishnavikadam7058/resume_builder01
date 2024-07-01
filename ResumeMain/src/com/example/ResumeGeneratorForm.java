package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ResumeGeneratorForm extends JFrame {
    private JLabel photoLabel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField educationField;
    private JTextField experienceField;
    private JTextField skillsField;
    private JTextField projectsField;
    private JTextField hobbiesField;
    private JButton uploadPhotoButton;
    private JButton saveButton;
    private JButton backButton;
    private String templateType;

    public ResumeGeneratorForm(String templateType) {
        this.templateType = templateType;

        setTitle("Resume Generator - " + templateType + " Template");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(12, 2));

        photoLabel = new JLabel();
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        educationField = new JTextField();
        experienceField = new JTextField();
        skillsField = new JTextField();
        projectsField = new JTextField();
        hobbiesField = new JTextField();
        uploadPhotoButton = new JButton("Upload Photo");
        saveButton = new JButton("Save");
        backButton = new JButton("Back");

        uploadPhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadPhoto();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveResume();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TemplateForm().setVisible(true);
                dispose();
            }
        });

        add(new JLabel("Photo:"));
        add(photoLabel);
        add(uploadPhotoButton);
        add(new JLabel());
        add(new JLabel("First Name:"));
        add(firstNameField);
        add(new JLabel("Last Name:"));
        add(lastNameField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Phone:"));
        add(phoneField);
        add(new JLabel("Address:"));
        add(addressField);
        add(new JLabel("Education:"));
        add(educationField);
        add(new JLabel("Experience:"));
        add(experienceField);
        add(new JLabel("Skills:"));
        add(skillsField);
        add(new JLabel("Projects:"));
        add(projectsField);
        add(new JLabel("Hobbies:"));
        add(hobbiesField);
        add(saveButton);
        add(backButton);

        loadResumeDetails();
    }

    private void uploadPhoto() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(file.getAbsolutePath()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
                photoLabel.setIcon(imageIcon);
                // Store the photo path or file as needed for saving to the database
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to upload photo.");
            }
        }
    }

    private void saveResume() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root", "root@123");
            String sql = "INSERT INTO resume (first_name, last_name, email, phone, address, education, experience, skills, projects, hobbies, photo, template_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, firstNameField.getText());
            pstmt.setString(2, lastNameField.getText());
            pstmt.setString(3, emailField.getText());
            pstmt.setString(4, phoneField.getText());
            pstmt.setString(5, addressField.getText());
            pstmt.setString(6, educationField.getText());
            pstmt.setString(7, experienceField.getText());
            pstmt.setString(8, skillsField.getText());
            pstmt.setString(9, projectsField.getText());
            pstmt.setString(10, hobbiesField.getText());
            // Convert the photo to bytes and set it
            // pstmt.setBytes(11, photoBytes);
            pstmt.setString(12, templateType);

            pstmt.executeUpdate();
            conn.close();

            JOptionPane.showMessageDialog(this, "Resume saved successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save resume.");
        }
    }

    private void loadResumeDetails() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root", "root@123");
            String sql = "SELECT * FROM personal_details pd " +
                         "LEFT JOIN education e ON pd.id = e.personal_id " +
                         "LEFT JOIN experience ex ON pd.id = ex.personal_id " +
                         "LEFT JOIN skills s ON pd.id = s.personal_id " +
                         "LEFT JOIN projects p ON pd.id = p.personal_id " +
                         "LEFT JOIN hobbies h ON pd.id = h.personal_id " +
                         "WHERE pd.id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1); // Replace with the actual user ID or identifier
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                firstNameField.setText(rs.getString("first_name"));
                lastNameField.setText(rs.getString("last_name"));
                emailField.setText(rs.getString("email"));
                phoneField.setText(rs.getString("phone"));
                addressField.setText(rs.getString("address"));
                educationField.setText(rs.getString("degree") + " from " + rs.getString("college") + " (" + rs.getString("from_date") + " - " + rs.getString("to_date") + ")");
                experienceField.setText(rs.getString("company_name") + " (" + rs.getString("role") + " - " + rs.getString("duration") + ")");
                skillsField.setText(rs.getString("skill"));
                projectsField.setText(rs.getString("project_name") + ": " + rs.getString("project_description"));
                hobbiesField.setText(rs.getString("hobby"));

                // Load the photo from the database and set it to the label
                // byte[] photoBytes = rs.getBytes("photo");
                // if (photoBytes != null) {
                //     ImageIcon imageIcon = new ImageIcon(new ImageIcon(photoBytes).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
                //     photoLabel.setIcon(imageIcon);
                // }
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load resume details.");
        }
    }
}
