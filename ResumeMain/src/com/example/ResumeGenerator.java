package com.example;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ResumeGenerator extends JFrame {
    private JTextArea personalDetailsArea;
    private JTextArea skillsArea;
    private JTextArea experienceArea;
    private JTextArea projectsArea;
    private JTextArea hobbiesArea;
    private JTextArea educationArea;

    public ResumeGenerator() {
        setTitle("Resume Generator");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        personalDetailsArea = new JTextArea(5, 50);
        skillsArea = new JTextArea(5, 50);
        experienceArea = new JTextArea(5, 50);
        projectsArea = new JTextArea(5, 50);
        hobbiesArea = new JTextArea(5, 50);
        educationArea = new JTextArea(5, 50);

        inputPanel.add(new JLabel("Personal Details:"));
        inputPanel.add(new JScrollPane(personalDetailsArea));
        inputPanel.add(new JLabel("Skills:"));
        inputPanel.add(new JScrollPane(skillsArea));
        inputPanel.add(new JLabel("Experience:"));
        inputPanel.add(new JScrollPane(experienceArea));
        inputPanel.add(new JLabel("Projects:"));
        inputPanel.add(new JScrollPane(projectsArea));
        inputPanel.add(new JLabel("Hobbies:"));
        inputPanel.add(new JScrollPane(hobbiesArea));
        inputPanel.add(new JLabel("Education:"));
        inputPanel.add(new JScrollPane(educationArea));

        JButton generateButton = new JButton("Generate PDF");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generatePDF();
                    JOptionPane.showMessageDialog(null, "PDF created successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error creating PDF");
                }
            }
        });

        add(inputPanel, BorderLayout.CENTER);
        add(generateButton, BorderLayout.SOUTH);
    }

    private void generatePDF() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();
        contentStream.newLineAtOffset(25, 750);
        contentStream.showText("Resume");
        contentStream.endText();

        addTextToPDF(contentStream, "Personal Details:", personalDetailsArea.getText(), 700);
        addTextToPDF(contentStream, "Skills:", skillsArea.getText(), 650);
        addTextToPDF(contentStream, "Experience:", experienceArea.getText(), 600);
        addTextToPDF(contentStream, "Projects:", projectsArea.getText(), 550);
        addTextToPDF(contentStream, "Hobbies:", hobbiesArea.getText(), 500);
        addTextToPDF(contentStream, "Education:", educationArea.getText(), 450);

        contentStream.close();
        document.save("resume.pdf");
        document.close();
    }

    private void addTextToPDF(PDPageContentStream contentStream, String title, String content, int yOffset) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(25, yOffset);
        contentStream.showText(title);
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText(content);
        contentStream.endText();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ResumeGenerator().setVisible(true);
            }
        });
    }
}


