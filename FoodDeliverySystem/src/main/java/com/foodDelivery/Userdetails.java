package com.foodDelivery;

import java.sql.ResultSet;

public class Userdetails extends conn{
    Userdetails(String userId) throws Exception {
        ResultSet rs = st.executeQuery("select * from loginDetails where userId='"+userId+"' and status='login'");
        if(rs.next()) {
            System.out.println("Your details are : ");
            ResultSet res = st.executeQuery("select * from userDetails where userId='"+userId+"'");
            while (res.next()){
                System.out.println("User Id : " + res.getString("userId"));
                System.out.println("User Name : " + res.getString("username"));
                System.out.println("User Mobile No : " + res.getString("usermobileno"));
                System.out.println("User Address : " + res.getString("useraddress") );
            }
        }
        else{
            throw new Exception("Login First!");
        }
    }

}
