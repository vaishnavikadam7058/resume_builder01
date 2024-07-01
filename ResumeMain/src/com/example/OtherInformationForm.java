package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class OtherInformationForm extends JFrame {
    private JTextField infoTitleField;
    private JTextArea descriptionArea;
    private JButton saveButton;

    public OtherInformationForm() {
        setTitle("Other Information");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        JLabel infoTitleLabel = new JLabel("Title:");
        infoTitleField = new JTextField();

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionArea = new JTextArea(5, 20);

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveOtherInformation();
            }
        });

        add(infoTitleLabel);
        add(infoTitleField);
        add(descriptionLabel);
        add(new JScrollPane(descriptionArea));
        add(new JLabel()); // empty cell
        add(saveButton);
    }

    private void saveOtherInformation() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root", "root@123");
            String sql = "INSERT INTO other_information (info_title, description) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, infoTitleField.getText());
            pstmt.setString(2, descriptionArea.getText());

            pstmt.executeUpdate();
            conn.close();

            JOptionPane.showMessageDialog(this, "Other information saved successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save other information.");
        }
    }
}

