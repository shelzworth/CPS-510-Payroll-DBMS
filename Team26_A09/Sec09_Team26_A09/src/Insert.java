import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class Insert{
    public static JPanel insertData() {
        JPanel insertData = new JPanel(new BorderLayout());
        JLabel d = new JLabel("Insert your data");
        insertData.add(d);

        JTextField tableNameField = new JTextField(20);
        JTextField columnNameField = new JTextField(20);
        JTextField valuesField = new JTextField(20);
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Table Name:"));
        inputPanel.add(tableNameField);
        inputPanel.add(new JLabel("Column Names (comma separated):"));
        inputPanel.add(columnNameField);
        inputPanel.add(new JLabel("Values (comma separated):"));
        inputPanel.add(valuesField);
        insertData.add(inputPanel, BorderLayout.CENTER);
        JButton executeButton = new JButton("Insert Data");
        insertData.add(executeButton, BorderLayout.SOUTH);
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        insertData.add(resultScrollPane, BorderLayout.EAST);
        executeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tableName = tableNameField.getText().trim();
                String columnNames = columnNameField.getText().trim();
                String values = valuesField.getText().trim();
                String query = "INSERT INTO " + tableName + " (" + columnNames + ") VALUES (" + values + ")";
                try {
                    Connection connection = SQL.getConnection();
                    connection.setAutoCommit(false);
                    Statement statement = connection.createStatement();
                    int result = statement.executeUpdate(query);
                    connection.commit();
                    resultArea.setText("Data inserted successfully. Rows affected: " + result);
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    resultArea.setText("Error inserting data: " + ex.getMessage());
                }
            }
        });
        return insertData;
    }
}
