package com.foodDelivery;

import java.sql.ResultSet;

public class logout extends conn {

    public logout(String userId) throws Exception {
        ResultSet rs = st.executeQuery("select * from loginDetails where userId='"+userId+"' and status='login'");
        if(rs.next()){
            int t = st.executeUpdate("update logindetails set status='logout' where userId='"+userId+"'");
            System.out.println("Logged Out Successfully");
            flag=0;
        }else{
            throw new Exception("Login First!");
        }
    }


}
