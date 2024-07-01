package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage1 extends JFrame {
    public HomePage1() {
        setTitle("Resume Builder");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        JPanel mainPanel = new JPanel(new BorderLayout());

       
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel headerLabel = new JLabel("Welcome to Resume Builder");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton personalDetailsButton = new JButton("Personal Details");
        JButton skillsButton = new JButton("Skills");
        JButton experienceButton = new JButton("Experience");
        JButton projectsButton = new JButton("Projects");
        JButton hobbiesButton = new JButton("Hobbies");
        JButton educationButton = new JButton("Education");
        JButton resumeGeneratorButton = new JButton("Resume Generator");

        buttonPanel.add(personalDetailsButton);
        buttonPanel.add(skillsButton);
        buttonPanel.add(experienceButton);
        buttonPanel.add(projectsButton);
        buttonPanel.add(hobbiesButton);
        buttonPanel.add(educationButton);
        buttonPanel.add(resumeGeneratorButton);

       
        personalDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPersonalDetailsForm();
            }
        });

        skillsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSkillsForm();
            }
        });

        experienceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openExperienceForm();
            }
        });

        projectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProjectsForm();
            }
        });

        hobbiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openHobbiesForm();
            }
        });

        educationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEducationForm();
            }
        });

        resumeGeneratorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openResumeGeneratorForm();
            }
        });

     
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        
        add(mainPanel);
    }

    private void openPersonalDetailsForm() {
        PersonalDetailsForm personalDetailsForm = new PersonalDetailsForm();
        personalDetailsForm.setVisible(true);
    }

    private void openSkillsForm() {
        SkillsForm skillsForm = new SkillsForm();
        skillsForm.setVisible(true);
    }

    private void openExperienceForm() {
        ExperienceForm experienceForm = new ExperienceForm();
        experienceForm.setVisible(true);
    }

    private void openProjectsForm() {
        ProjectForm projectsForm = new ProjectForm();
        projectsForm.setVisible(true);
    }

    private void openHobbiesForm() {
        HobbiesForm hobbiesForm = new HobbiesForm();
        hobbiesForm.setVisible(true);
    }

    private void openEducationForm() {
        EducationSection educationForm = new EducationSection();
        educationForm.setVisible(true);
    }

    private void openResumeGeneratorForm() {
        ResumeGenerator resumeGeneratorForm = new ResumeGenerator();
        resumeGeneratorForm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomePage1().setVisible(true);
            }
        });
    }
}
