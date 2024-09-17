import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

    public class CustomerInfo extends Frame implements ActionListener
    {
        // UI components
        Label nameLabel, emailLabel, phoneLabel;
        TextField nameText, emailText, phoneText;
        Button submitButton;

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/customerDB";
        String user = "root";
        String password = "ParimalaRao";

        // Constructor
        public CustomerInfo()
        {
            // Frame layout
            // 4 rows, 2 columns, 5 pixels gap
            setLayout(new GridLayout(4, 2, 5, 5));


            // Name Label and TextField
            nameLabel = new Label("Name: ");
            nameText = new TextField(20);
            add(nameLabel);
            add(nameText);

           //nameLabel = new JLabel("Name:");
            //nameLabel.setFont(new Font("Raleway", Font.BOLD, 20));

            // Email Label and TextField
            emailLabel = new Label("Email: ");
            emailText = new TextField(20);
            add(emailLabel);
            add(emailText);

            phoneLabel = new Label("Phone: ");
            phoneText = new TextField(20);
            add(phoneLabel);
            add(phoneText);

            submitButton = new Button("Submit");
            add(new Label()); // Empty label for alignment
            add(submitButton);

            // Add action listener for button
            submitButton.addActionListener(this);

            // Frame settings
            setTitle("Customer Information Entry");
            setSize(500, 400);
            setVisible(true);

            // Close window functionality
            addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent we)
                {
                    System.exit(0);
                }
            });

        }
        // Action listener for button click
        public void actionPerformed(ActionEvent ae) {
            String name = nameText.getText();
            String email = emailText.getText();
            String phone = phoneText.getText();

            // Validate user input
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                System.out.println("oops looks like you have not entered anything.");
                return; // Stop further execution if input is invalid
            }
            // JDBC Connection
            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                // Try establishing the connection
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Database connection established.");
                // Prepare the SQL query
                String query = "INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, phone);
                // Execute the query and check if data was inserted
                int rows = pstmt.executeUpdate();
                if (rows > 0)
                {
                    System.out.println("Customer data inserted successfully.");
                } else
                {
                    System.out.println("Data insertion failed.");
                }
            } catch (SQLException e)
            {
                // Handle SQL exceptions (e.g., connection failure, invalid query)
                System.out.println("Error connecting to database or executing SQL query.");
                e.printStackTrace();
            } finally
            {
                // Ensure resources are properly closed
                try {
                    if (pstmt != null)
                    {
                        pstmt.close();
                    }
                    if (conn != null)
                    {
                        conn.close();
                    }
                }
                catch (SQLException e)
                {
                    // Handle exception while closing the connection
                    System.out.println("Error closing database connection.");
                    e.printStackTrace();
                }
            }

        }

        public static void main(String[] args)
        {
            try
            {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("JDBC Driver loaded successfully.");
            }
            catch (ClassNotFoundException e)
            {
                // Handle error if JDBC driver is not found
                System.out.println("Could not load JDBC driver.");
                e.printStackTrace();
                return; // Exit the program if the driver isn't loaded
            }

            // Create and display the GUI
            new CustomerInfo();
        }
    }







