package com.foodDelivery;

import java.sql.SQLException;
import java.util.Scanner;

public class MainClass extends conn {
    static String user;
    public MainClass() throws SQLException, ClassNotFoundException {
        super();
        st.executeUpdate("update logindetails set status='logout'");
    }


    public static void main(String[] args) throws Exception {
        MainClass obj = new MainClass();
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("=======================================================================================================================");
            System.out.printf("||%84s%33s\n","********* Welcome to Food Delivery System *********","||");
            System.out.printf("||%117s\n","||");
            System.out.printf("||%14s","1. Login");
            System.out.printf("%28s","2. Register");
            System.out.printf("%28s","3. Place Order");
            System.out.printf("%29s%18s\n","4. User Details","||");
            System.out.printf("||%22s","5. Order Details");
            System.out.printf("%26s","6. Wallet Balance");
            System.out.printf("%30s","7. Logout from Account");
            System.out.printf("%30s%9s\n","8. Exit from Application","||");

            System.out.println("=======================================================================================================================");
            System.out.print("Choose Option (1,2,3,4,5,6,7,8) => ");
            int choice = scanner.nextInt();
            System.out.println("=======================================================================================================================");

            switch (choice){
                case 1:{
                    try{
                        login login = new login();
                        user=login.userId;
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    break;
                }
                case 2:{
                    try{
                        Register register = new Register();
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    break;
                }
                case 3:{
                    try{
                        OrderFood food = new OrderFood(user);
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    break;
                }
                case 4:{
                    try{
                        Userdetails userdetails = new Userdetails(user);
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                    break;
                }
                case 5:{
                    try{
                        OrderDetails order = new OrderDetails("vg270898");
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    break;
                }
                case 6:{
                    try{
                        Wallet wallet = new Wallet(user);
                        System.out.println("Current Wallet Balance is : ₹" + wallet.balanceMoney);
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    break;
                }
                case 7:{
                    try{
                        logout logout = new logout(user);
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    break;
                }
                case 8:{
                    System.out.println("\t\t\t\tThank You for Using our Application! Visit Again!");
                    System.exit(1);

                }
                default:{
                    System.out.println("Choose Correct Options!");
                }
            }
        }
    }
}
