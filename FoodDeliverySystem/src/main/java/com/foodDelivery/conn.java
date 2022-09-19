package com.foodDelivery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class conn {
    Statement st;
    static int flag=0;
    public conn() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/fooddeliverysystem";
        String user= "root";
        String password = "vishalgupta";
        Connection con = DriverManager.getConnection(url,user,password);
        st = con.createStatement();
    }
}
