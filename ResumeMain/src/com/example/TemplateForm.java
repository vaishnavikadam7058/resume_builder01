package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemplateForm extends JFrame {
    public TemplateForm() {
        setTitle("Choose Template");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        JButton businessTemplateButton = new JButton("Business Template");
        JButton studentTemplateButton = new JButton("Student Template");
        JButton customTemplateButton = new JButton("Custom Template");

        businessTemplateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResumeGeneratorForm("Business").setVisible(true);
                dispose();
            }
        });

        studentTemplateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResumeGeneratorForm("Student").setVisible(true);
                dispose();
            }
        });

        customTemplateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ResumeGeneratorForm("Custom").setVisible(true);
                dispose();
            }
        });

        add(businessTemplateButton);
        add(studentTemplateButton);
        add(customTemplateButton);
    }
}
