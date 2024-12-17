import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = SQL.getConnection();
        // Create the frame
        JFrame frame = new JFrame("PMS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create the card layout and main panel
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Go to Main Page");
        buttonPanel.add(button);

        // Create the main page panel with different sections
        JPanel mainPage = new JPanel();
        mainPage.setLayout(new BoxLayout(mainPage, BoxLayout.Y_AXIS));
        JLabel begTitle = new JLabel("Payroll Management Software");
        begTitle.setFont(new Font("Arial", Font.BOLD,24));
// View Tables

        JButton viewTable = new JButton("View Data");

        viewTable.addActionListener(new ActionListener() {
    /***
     *
     *
     * View Tables
     *
     *
     * **/
            public void actionPerformed(ActionEvent e) {
                JFrame dataFrame = new JFrame("Data from Oracle");
                dataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dataFrame.setSize(600, 400);
                JPanel viewTables = ViewTables.showData();
                dataFrame.add(viewTables);
                dataFrame.setVisible(true);
            }
        });
// Drop Tables
        JButton dropTable = new JButton("Drop Tables");
        dropTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame dataFrame = new JFrame("Data from Oracle");
                dataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dataFrame.setSize(600, 400);
                JPanel dropped = DropTables.dropTables();
                dataFrame.add(dropped);
                dataFrame.setVisible(true);
            }
        });
// Insert Tables
        JButton insertInto = new JButton("Insert Data");
        insertInto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame dataFrame = new JFrame("Data from Oracle");
                dataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dataFrame.setSize(600, 400);
                JPanel inserted = Insert.insertData();
                dataFrame.add(inserted);
                dataFrame.setVisible(true);
            }
        });
// Queries

        JButton queries = new JButton("Queries");
        queries.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame dataFrame = new JFrame("Data from Oracle");
                dataFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dataFrame.setSize(600, 400);
                JPanel queries1 = Queries.queries();
                dataFrame.add(queries1);
                dataFrame.setVisible(true);
            }
        });

        mainPage.add(begTitle);
        mainPage.add(viewTable);
        mainPage.add(dropTable);
        mainPage.add(insertInto);
        mainPage.add(queries);


        // Add panels to the card layout
        mainPanel.add(buttonPanel, "Button Panel");
        mainPanel.add(mainPage, "Main Page");

        // Add action listener to the button to switch to the main page
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Main Page");
            }
        });

        // Add the main panel to the frame
        frame.add(mainPanel);
        // Make the frame visible
        frame.setVisible(true);
    }
}
