package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private JButton addProfileImageButton;
    private JPanel homePanel;

    private String username;

    public HomePage(String username) {
        this.username = username;

        // Initialize components
        homePanel = new JPanel();
        addProfileImageButton = new JButton("Add Profile Image");

        // Set layout manager for homePanel
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));

        // Add components to homePanel
        homePanel.add(addProfileImageButton);

        // Set the content pane
        setContentPane(homePanel);

        // Set JFrame properties
        setTitle("Home Page");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add action listeners
        addProfileImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfileForm().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomePage("your_username").setVisible(true);
            }
        });
    }
}


