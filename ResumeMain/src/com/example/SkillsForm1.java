package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SkillsForm1 extends JFrame {
    private JTextField skillField;
    private JButton saveButton;
    private JButton backButton;
    private JButton nextButton;

    public SkillsForm1() {
        setTitle("Skills");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        JLabel skillLabel = new JLabel("Skill:");
        skillField = new JTextField();

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSkill();
            }
        });

        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PersonalDetailsForm().setVisible(true);
                dispose();
            }
        });

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OtherInformationForm().setVisible(true);
                dispose();
            }
        }); 

        add(skillLabel);
        add(skillField);
        add(backButton);
        add(nextButton);
        add(new JLabel()); 
        add(saveButton);
    }

    private void saveSkill() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root", "root@123");
            String sql = "INSERT INTO skills (skill) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, skillField.getText());

            pstmt.executeUpdate();
            conn.close();

            JOptionPane.showMessageDialog(this, "Skill saved successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save skill.");
        }
    }
}

