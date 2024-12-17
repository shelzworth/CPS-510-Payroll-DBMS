import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class DropTables {
    public static JPanel dropTables() {
        JPanel dropTables = new JPanel(new BorderLayout());
        JLabel s = new JLabel("Drop the table");
        dropTables.add(s);
        String[] tableNames = ViewTables.getTableNames();
        JComboBox<String> comboBox = new JComboBox<>(tableNames);
        dropTables.add(comboBox, BorderLayout.CENTER);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedTable = (String) comboBox.getSelectedItem();
                JFrame dataFrame = new JFrame("Data from Oracle: " + selectedTable);
                dataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dataFrame.setSize(600, 400);
                JPanel viewTables = DropTables.dropTable(selectedTable);
                dataFrame.add(viewTables);
                dataFrame.setVisible(true);
            }
        });

        return dropTables;
    }
    public static JPanel dropTable(String tableName) {
        JPanel dropTable = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Attempting to drop table: " + tableName);
        dropTable.add(label, BorderLayout.NORTH);
        try {
            Connection connection = SQL.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE " + tableName);
            JLabel successLabel = new JLabel("Table " + tableName + " dropped successfully.");
            dropTable.add(successLabel, BorderLayout.CENTER);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Error dropping table: " + e.getMessage());
            dropTable.add(errorLabel, BorderLayout.CENTER);
        } return dropTable; }

}
