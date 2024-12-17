import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Queries {

    public static JPanel queries() {
        JPanel insertData = new JPanel(new BorderLayout());
        JLabel d = new JLabel("Queries");
        insertData.add(d);
        JTextArea sqlInput = new JTextArea(5, 40);
        JScrollPane scrollPane = new JScrollPane(sqlInput);
        insertData.add(scrollPane, BorderLayout.NORTH);
        JButton executeButton = new JButton("Execute");
        executeButton.setPreferredSize(new Dimension(100, 30));

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
//        insertData.add(resultScrollPane, BorderLayout.SOUTH);
//        executeButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String query = sqlInput.getText();
//                executeSQL(query, resultArea);
//            } });
        JPanel centerPanel = new JPanel(new BorderLayout());
        JComboBox<String> sqlCommandsCB = new JComboBox<>();
        JScrollPane comboBoxScrollPane = new JScrollPane(sqlCommandsCB);
        JRadioButton fileInputRadioButton = new JRadioButton("Select Query");
        JPanel southPanel = new JPanel(new BorderLayout());
        JRadioButton manualInputRadioButton = new JRadioButton("Manual Input", true);
        centerPanel.add(comboBoxScrollPane, BorderLayout.CENTER);

        southPanel.add(manualInputRadioButton, BorderLayout.WEST);
        southPanel.add(fileInputRadioButton, BorderLayout.EAST);

        southPanel.add(resultScrollPane, BorderLayout.SOUTH);

        JButton queryButton = new JButton("Execute");
        insertData.add(queryButton, BorderLayout.EAST);
        insertData.add(centerPanel, BorderLayout.CENTER);
        insertData.add(southPanel, BorderLayout.SOUTH);
        loadQuery("src/queries.txt", sqlCommandsCB);
        queryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query;
                if (manualInputRadioButton.isSelected()) {
                    query = sqlInput.getText();
                }
                else {
                    query = (String) sqlCommandsCB.getSelectedItem();
                }
                executeSQL(query, resultArea);
            }
        });
        return insertData;
    }
    private static void executeSQL(String query, JTextArea resultArea) {
        try {
            Connection connection = SQL.getConnection();
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            if (query.trim().toUpperCase().startsWith("SELECT")) {
                ResultSet resultSet = statement.executeQuery(query);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                StringBuilder resultBuilder = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    resultBuilder.append(metaData.getColumnName(i)).append("\t");
                }
                resultBuilder.append("\n");
                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        resultBuilder.append(resultSet.getString(i)).append("\t");
                    } resultBuilder.append("\n");
                }
                resultArea.setText(resultBuilder.toString());
                resultSet.close();
            } else {
                int result = statement.executeUpdate(query);
                connection.commit();
                resultArea.setText("Query executed successfully. Rows affected: " + result);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error executing query: " + e.getMessage());
        }
    }
    private static void loadQuery(String filePath, JComboBox<String> comboBox) {
        try (BufferedReader fileRead = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = fileRead.readLine()) != null) {
                comboBox.addItem(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
