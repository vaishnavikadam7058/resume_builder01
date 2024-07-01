package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class HobbiesForm extends JFrame {
    private JTextField hobbiesField;
    private JButton saveButton;
    private JButton nextButton;
    private JButton backButton;

    public HobbiesForm() {
        setTitle("Hobbies Section");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        JLabel hobbiesLabel = new JLabel("Hobbies:");
        hobbiesField = new JTextField();

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveHobbiesDetails();
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

        add(hobbiesLabel);
        add(hobbiesField);
        add(saveButton);
        add(nextButton);
        add(backButton);
    }

    private void saveHobbiesDetails() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root", "root@123");
            String sql = "INSERT INTO hobbies (hobby) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, hobbiesField.getText());

            pstmt.executeUpdate();
            conn.close();

            JOptionPane.showMessageDialog(this, "Hobbies details saved successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save hobbies details.");
        }
    }

    private void openNextForm() {
        new TemplateForm().setVisible(true);
        this.dispose();
    }

    private void openPreviousForm() {
        new ProjectForm().setVisible(true);
        this.dispose();
    }
}
