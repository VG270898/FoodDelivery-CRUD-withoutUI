package com.foodDelivery;

import java.sql.ResultSet;
import java.util.Scanner;

public class Register extends conn {
    String userId;
    String password;
    String username;
    String userMobileNo;
    String userAddress;

    Register() throws Exception {
        ResultSet rs = st.executeQuery("select * from loginDetails where userId='"+userId+"' and status='login'");
        if(!rs.next()) {
            while(true) {
                randomalphnumeric obj = new randomalphnumeric();
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter Unique UserId : ");
                userId = scanner.nextLine();
                System.out.print("Enter Password (at least 8 character long) : ");
                password = scanner.nextLine();
                System.out.print("Enter your Name : ");
                username = scanner.nextLine();
                System.out.print("Enter Your Mobile No : ");
                userMobileNo = scanner.nextLine();
                System.out.print("Enter Your Address : ");
                userAddress = scanner.nextLine();

                ResultSet users = st.executeQuery("Select * from logindetails");
                while (users.next()) {
                    if (users.getString("userId").equals(userId)) {
                        throw new Exception("UserID Already Exists in the Database");
                    }
                }

                if (password.length() < 8) {
                    throw new Exception("Password must be 8 character long");
                }

                int t1 = st.executeUpdate("insert into logindetails(userId,password) values('" + userId + "','" + password + "'");
                int t2 = st.executeUpdate("insert into userdetails values('" + userId + "','" + username + "','" + userMobileNo + "','" + userAddress + "'");
                int t3 = st.executeUpdate("insert into wallet(walletId,userId) values('" + obj.walletId() + "','" + userId + "')");
                if (t1 == 1 && t2 == 1 && t3 == 1) {
                    System.out.println("Registered Successfully!");
                    break;
                } else {
                    throw new Exception("Something Went Wrong!");
                }
            }
        }else {
            throw new Exception("Logout to register new account");
        }

    }

}
