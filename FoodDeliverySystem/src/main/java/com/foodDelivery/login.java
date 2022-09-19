package com.foodDelivery;

import java.sql.ResultSet;
import java.util.Scanner;

public class login extends conn{
    String userId;
    String password;
    public login() throws Exception {

        if(flag==0) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter UserId => ");
            userId = scanner.nextLine();
            System.out.print("Enter Password => ");
            password = scanner.nextLine();
        }
        else{
            throw new Exception("Already logged in!");
        }

        ResultSet rs = st.executeQuery("select * from loginDetails where userId='"+userId+"' and password = '"+password+"'");
        if(rs.next()) {
            if(rs.getString("status").equals("login")){
                throw new Exception("Already Logged in");
            }
            else {
                int t = st.executeUpdate("update logindetails set status='login' where userId='" + userId + "'");
                System.out.println("Logged in Successfully");
                flag = 1;
            }
        }
        else{
            throw new Exception("UserId or Password is Wrong!");
        }

    }
}
