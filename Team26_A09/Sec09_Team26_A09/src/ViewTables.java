import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ViewTables {
    public static JPanel showData() {
        JPanel showData = new JPanel(new BorderLayout());
        JLabel s = new JLabel("Show your data");
        showData.add(s);
        String[] tableNames = getTableNames();
        JComboBox<String> comboBox = new JComboBox<>(tableNames);
        showData.add(comboBox, BorderLayout.CENTER);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedTable = (String) comboBox.getSelectedItem();
                JFrame dataFrame = new JFrame("Data from Oracle: " + selectedTable);
                dataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dataFrame.setSize(600, 400);
                JPanel viewTables = ViewTables.showData(selectedTable);
                dataFrame.add(viewTables);
                dataFrame.setVisible(true);
            }
        });

        return showData;
    }
    public static JPanel showData(String tableName) {
        JPanel showData = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Data from table: " + tableName);
        showData.add(title, BorderLayout.NORTH);
        try {
            Connection connection = SQL.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            } DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(row);
            }
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            showData.add(scrollPane, BorderLayout.CENTER);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Error retrieving data.");
            showData.add(errorLabel, BorderLayout.CENTER);
        } return showData;
    }
    public static String[] getTableNames() {
        List<String> tableNames = new ArrayList<>();
        try {
            Connection connection = SQL.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables("Payroll System", null, null, new String[] { "TABLE" });
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                    tableNames.add(tableName);

            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace(); }
        return tableNames.toArray(new String[0]);
    }
}
