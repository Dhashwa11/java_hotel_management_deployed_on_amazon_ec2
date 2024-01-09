//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
import java.io.Console;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        //Class.forName("com.mysql.cj.jdbc.Driver");

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbs", "root", "dhashwa@2004");

            Statement statement = connection.createStatement();

            String query = "SELECT * FROM MENU";

            ResultSet resultSet = statement.executeQuery(query);

            /*while (resultSet.next()) {
                int code = resultSet.getInt("code");
                String item = resultSet.getString("item_name");
                int price = resultSet.getInt("price");

                System.out.println(code + " " + item + " " + price);
            }*/

            Console console = System.console();
            Scanner sc = new Scanner(System.in);
            String regid = "O101";
            int choice, choice1;
            double ph;
            String reg_name;
            String add;
            String password;
            int sum = 0;
            String flag = "true";
            System.out.println("=====================================================================");
            System.out.println("       Fourth Dimension Technologies Hotel Management System         ");
            System.out.println("=====================================================================");
            do {
                System.out.println("1. Registration");
                System.out.println("2. Log into Your account");
                System.out.println("3. Exit");
                System.out.println("Enter Your Choice:");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Enter Your User Name:");
                        reg_name = sc.next();
                        System.out.println("Enter Password:");
                        password = sc.next();
                        System.out.println("Please Enter the Following Details.....");
                        System.out.println("Enter your Phone Number:");
                        ph = sc.nextDouble();
                        System.out.println("Enter Address:");
                        add = sc.next();
                        System.out.println("Enter your City:");
                        String city = sc.next();
                        System.out.println("Enter your Email Address:");
                        String email = sc.next();
                        System.out.println("");
                        String query_reg = "INSERT INTO USER VALUES ('" + reg_name + "','" + password + "'," + ph + ",'" + add + "','" + city + "','" + email + "')";
                        System.out.println(query_reg);
                        statement.execute(query_reg);
                        System.out.println("Thanks For Registration, Hope We will be able to Serve You the Best FOOD of your life :)");
                        break;
                    case 2:
                        System.out.println("Enter Your User Name:");
                        reg_name = sc.next();
                        System.out.println("Enter Password:");
                        password = sc.next();
                        String userCheckQuery = "SELECT COUNT(USERNAME) AS C FROM USER WHERE USERNAME='" + reg_name + "' AND PASSWORD='" + password + "'";
                        System.out.println(userCheckQuery);
                        ResultSet userCheck = statement.executeQuery(userCheckQuery);
                        int isUser = 0;
                        try {
                            while (userCheck.next()){
                                isUser = userCheck.getInt("C");
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        if (isUser == 1) {
                            do {
                                System.out.println("1. Menu");
                                System.out.println("2. Place the Order");
                                System.out.println("3. For FeedBack");
                                System.out.println("4. For Cancel The Order");
                                System.out.println("5. For Main Page");
                                System.out.println("Enter the Choice");
                                choice1 = sc.nextInt();
                                switch (choice1) {
                                    case 1:
                                        String menuQuery = "SELECT * FROM MENU";
                                        ResultSet menu = statement.executeQuery(menuQuery);
                                        System.out.println("          \t\t****************************\t\t          ");
                                        System.out.println("          \t\t           MENU             \t\t          ");
                                        System.out.println("          \t\t****************************\t\t          ");
                                        System.out.println("CODE\tITEM NAME\tPRICE\tQTY\tV/NV");
                                        try {
                                            while (menu.next()) {
                                                System.out.println(menu.getInt("CODE") + "\t" +
                                                        menu.getString("ITEM_NAME") + "\t" +
                                                        menu.getInt("PRICE") + "\t\t" +
                                                        menu.getInt("QTY") + "\t" +
                                                        (menu.getInt("TYPE") == 0 ? "NON-VEG" : "VEG")
                                                );
                                            }
                                            System.out.println("");
                                        } catch (Exception e) {
                                            System.out.println(e);
                                        }
                                        break;
                                    case 2:
                                    	 
                                        String usernameOrder = reg_name;  

                                        
                                        int orderIdOrder = 0;
                                        String getMaxOrderIdQuery = "SELECT MAX(order_id) AS max_order_id FROM ORDERS";
                                        ResultSet maxOrderIdResultSet = statement.executeQuery(getMaxOrderIdQuery);
                                        if (maxOrderIdResultSet.next()) {
                                            orderIdOrder = maxOrderIdResultSet.getInt("max_order_id") + 1;
                                        } else {
                                            orderIdOrder = 1; // If no orders exist yet, start with order_id = 1
                                        }

                                        
                                        System.out.println("Your order ID is: " + orderIdOrder);

                                        
                                        java.util.Date utilDate = new java.util.Date();
                                        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());

                                        
                                        int noOfItems = sc.nextInt();
                                        int amount = 0;
                                        for (int i = 0; i < noOfItems; i++) {
                                            System.out.print("Enter the code for item " + (i + 1) + ": ");
                                            int codeOrder = sc.nextInt();
                                            System.out.print("Enter the Qty for item " + (i + 1) + ": ");
                                            int qtyOrder = sc.nextInt();

                                            
                                            String reduceQtyQuery = "UPDATE MENU SET QTY = QTY - " + qtyOrder + " WHERE CODE = " + codeOrder;
                                            statement.execute(reduceQtyQuery);
                                            String insertOrderQuery = "INSERT INTO ORDERS (order_id, datentime, username, code, qty) VALUES ("
                                                    + orderIdOrder + ", '" + sqlTimestamp + "', '" + usernameOrder + "', " + codeOrder + ", " + qtyOrder + ")";
                           statement.execute(insertOrderQuery);


                                            
                                         
                                            /*ResultSet generatedKeys = statement.getGeneratedKeys();
                                            int orderId;
                                            if (generatedKeys.next()) {
                                                orderId = generatedKeys.getInt(1);
                                            } else {
                                                throw new SQLException("Failed to get the auto-generated order id.");
                                            }*/

                                            // Calculate total amount for the order
                                            String getPriceQuery = "SELECT PRICE FROM MENU WHERE CODE = " + codeOrder;
                                            ResultSet priceResultSet = statement.executeQuery(getPriceQuery);
                                            if (priceResultSet.next()) {
                                                amount += priceResultSet.getInt("PRICE") * qtyOrder;
                                            }
                                            System.out.println("\nTotal Amount for Order ID " + orderIdOrder + " = " + amount);
                                        }
                                            
                                        break;
                                    case 3:
                                    	System.out.println("Enter your feedback:");
                                        String feedbackText = sc.next();  

                                        
                                        String usernameFeedback = reg_name;  
                                        int orderIdFeedback;  
                                        System.out.println("Enter the order ID for feedback:");
                                        orderIdFeedback = sc.nextInt();

                                       
                                        String feedbackInsertQuery = "INSERT INTO FEEDBACK (ORDER_ID, USERNAME, REVIEW) VALUES (" + orderIdFeedback + ", '" + usernameFeedback + "', '" + feedbackText + "')";
                                        try {
                                            statement.execute(feedbackInsertQuery);
                                            System.out.println("Feedback submitted successfully!");
                                        } catch (SQLException ex) {
                                            System.out.println("Error submitting feedback: " + ex.getMessage());
                                        }
                                        break;
                                    
                                    case 4:
                                        
                                        String usernameCancelOrder = reg_name; 

                                        
                                        String userOrdersQuery = "SELECT * FROM ORDERS WHERE username = '" + usernameCancelOrder + "'";
                                        try {
                                            ResultSet userOrders = statement.executeQuery(userOrdersQuery);
                                            System.out.println("\nYour Orders:");
                                            System.out.println("ID\tOrder_ID\tDate & Time\t\t\tCode\tQty");
                                            while (userOrders.next()) {
                                                System.out.println(userOrders.getInt("id") + "\t" +
                                                        userOrders.getInt("order_id") + "\t\t" +
                                                        userOrders.getTimestamp("datentime") + "\t" +
                                                        userOrders.getInt("code") + "\t" +
                                                        userOrders.getInt("qty"));
                                            }
                                        } catch (SQLException ex) {
                                            System.out.println("Error retrieving user orders: " + ex.getMessage());
                                        }

                                        
                                        System.out.println("\nEnter the ID of the order you want to cancel (0 to go back):");
                                        int orderIdToCancel = sc.nextInt();

                                       
                                        if (orderIdToCancel == 0) {
                                            break;
                                        }

                                       
                                        String getOrderDetailsQuery = "SELECT * FROM ORDERS WHERE order_id = " + orderIdToCancel + " AND username = '" + usernameCancelOrder + "'";
                                        try {
                                            ResultSet orderDetails = statement.executeQuery(getOrderDetailsQuery);

                                            if (orderDetails.next()) {
                                                int codeCancelledOrder = orderDetails.getInt("code");
                                                int qtyCancelledOrder = orderDetails.getInt("qty");

                                                
                                                String increaseQtyQuery = "UPDATE MENU SET QTY = QTY + " + qtyCancelledOrder + " WHERE CODE = " + codeCancelledOrder;
                                                statement.executeUpdate(increaseQtyQuery);

                                                
                                                String cancelOrderQuery = "DELETE FROM ORDERS WHERE order_id = " + orderIdToCancel + " AND username = '" + usernameCancelOrder + "'";
                                                int rowsAffected = statement.executeUpdate(cancelOrderQuery);

                                                if (rowsAffected > 0) {
                                                    System.out.println("Order canceled successfully! Quantity in the MENU table updated.");
                                                } else {
                                                    System.out.println("Invalid order ID or order does not belong to the current user.");
                                                }
                                            } else {
                                                System.out.println("Invalid order ID or order does not belong to the current user.");
                                            }
                                        } catch (SQLException ex) {
                                            System.out.println("Error canceling order: " + ex.getMessage());
                                        }
                                        break;

                                    	
                                        
                                    case 5:
                                        break;
                                }
                            } while (choice1 < 5);
                        }

                    case 3:
                        break;
                }
            } while (choice < 3);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
