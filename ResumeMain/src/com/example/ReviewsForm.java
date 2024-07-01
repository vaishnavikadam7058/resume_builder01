package com.example;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ReviewsForm extends JFrame {
    private JTextField nameField, contentField, ratingField;
    private JTable reviewsTable;
    private DefaultTableModel tableModel;
    private Connection connection;

    public ReviewsForm() {
        setTitle("Reviews Section");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        
        initializeDatabase();

       
        String[] columnNames = {"ID", "Name", "Content", "Rating"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reviewsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reviewsTable);

        
        nameField = new JTextField(20);
        contentField = new JTextField(20);
        ratingField = new JTextField(4);

        
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Content:"));
        inputPanel.add(contentField);
        inputPanel.add(new JLabel("Rating:"));
        inputPanel.add(ratingField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

      
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addReview();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editReview();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteReview();
            }
        });

        add(mainPanel);
        loadReviews();
    }

    private void initializeDatabase() {
        try {
            
            String url = "jdbc:mysql://localhost:3306/resume_main";
            String user = "root";
            String password = "root@123";

          
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadReviews() {
        try {
            String query = "SELECT * FROM reviews";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String content = resultSet.getString("content");
                int rating = resultSet.getInt("rating");
                tableModel.addRow(new Object[]{id, name, content, rating});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addReview() {
        String name = nameField.getText();
        String content = contentField.getText();
        int rating = Integer.parseInt(ratingField.getText());

        try {
            String query = "INSERT INTO reviews (name, content, rating) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, content);
            preparedStatement.setInt(3, rating);
            preparedStatement.executeUpdate();

         
            nameField.setText("");
            contentField.setText("");
            ratingField.setText("");

           
            loadReviews();

            JOptionPane.showMessageDialog(this, "Review added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editReview() {
        int selectedRow = reviewsTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String name = nameField.getText();
            String content = contentField.getText();
            int rating = Integer.parseInt(ratingField.getText());

            try {
                String query = "UPDATE reviews SET name = ?, content = ?, rating = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, content);
                preparedStatement.setInt(3, rating);
                preparedStatement.setInt(4, id);
                preparedStatement.executeUpdate();

               
                nameField.setText("");
                contentField.setText("");
                ratingField.setText("");

                
                loadReviews();

                JOptionPane.showMessageDialog(this, "Review updated successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteReview() {
        int selectedRow = reviewsTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);

            try {
                String query = "DELETE FROM reviews WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

              
                nameField.setText("");
                contentField.setText("");
                ratingField.setText("");

              
                loadReviews();

                JOptionPane.showMessageDialog(this, "Review deleted successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReviewsForm().setVisible(true);
            }
        });
    }
}

