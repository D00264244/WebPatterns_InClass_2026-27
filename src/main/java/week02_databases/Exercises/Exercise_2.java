package week02_databases.Exercises;

import week02_databases.entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Exercise_2 {

    public static void main(String[] args) {

        // Database details
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/classicmodels";
        String username = "root";
        String password = "";

        // Ask user for product line
        Scanner input = new Scanner(System.in);

        System.out.print("Enter product line: ");
        String productLineInput = input.nextLine();

        // Load driver
        try {
            Class.forName(driver);

            // Connect to database
            try (Connection conn =
                         DriverManager.getConnection(url, username, password)) {

                // SQL query
                String sql = "SELECT * FROM products WHERE productLine = ?";

                // Prepare statement
                try (PreparedStatement ps = conn.prepareStatement(sql)) {

                    // Put user's product line into the ?
                    ps.setString(1, productLineInput);

                    // Create ArrayList
                    ArrayList<Product> products = new ArrayList<>();

                    // Run query
                    ResultSet rs = ps.executeQuery();

                    // Process results
                    while (rs.next()) {

                        String productName = rs.getString("productName");
                        String productCode = rs.getString("productCode");
                        String productLine = rs.getString("productLine");
                        String productScale = rs.getString("productScale");
                        String productVendor = rs.getString("productVendor");
                        String productDescription = rs.getString("productDescription");
                        int quantityInStock = rs.getInt("quantityInStock");
                        double buyPrice = rs.getDouble("buyPrice");
                        double MSRP = rs.getDouble("MSRP");

                        // Create Product object
                        Product currentProduct = new Product(
                                productName,
                                productCode,
                                productLine,
                                productScale,
                                productVendor,
                                productDescription,
                                quantityInStock,
                                buyPrice,
                                MSRP
                        );

                        // Add Product to ArrayList
                        products.add(currentProduct);
                    }

                    // Sort products using compareTo()
//                    Collections.sort(products);

                    // Display results
                    System.out.println("\nProducts in product line: "
                            + productLineInput);

                    for (Product product : products) {
                        System.out.println(product);
                    }

                } catch (SQLException e) {
                    System.out.println("Cannot prepare statement: " + sql);
                }

            } catch (SQLException e) {
                System.out.println(
                        "Cannot establish a connection to " + url
                );
            }

        } catch (ClassNotFoundException e) {
            System.out.println(
                    "No driver files found - please check dependencies"
            );
        }

        input.close();
    }
}