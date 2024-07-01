package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SkillsForm extends JFrame {
    private JTextField skillField;
    private JButton saveButton;
    private JPanel skillsPanel;
    private JButton addMoreButton;

    public SkillsForm() {
        setTitle("Skills");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        skillsPanel = new JPanel();
        skillsPanel.setLayout(new BoxLayout(skillsPanel, BoxLayout.Y_AXIS));

        addSkillField();

        JScrollPane scrollPane = new JScrollPane(skillsPanel);
        add(scrollPane, BorderLayout.CENTER);

        addMoreButton = new JButton("Add More");
        addMoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSkillField();
                revalidate();
                repaint();
            }
        });

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSkills();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addMoreButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addSkillField() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel skillLabel = new JLabel("Skill:");
        skillField = new JTextField(20);
        panel.add(skillLabel);
        panel.add(skillField);
        skillsPanel.add(panel);
    }

    private void saveSkills() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root", "root@123");
            String sql = "INSERT INTO skills (skill_name) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (Component component : skillsPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel panel = (JPanel) component;
                    JTextField skillField = (JTextField) panel.getComponent(1);
                    String skillName = skillField.getText();
                    pstmt.setString(1, skillName);
                    pstmt.addBatch();
                }
            }

            pstmt.executeBatch();
            conn.close();

            JOptionPane.showMessageDialog(this, "Skills saved successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save skills.");
        }
    }
}

