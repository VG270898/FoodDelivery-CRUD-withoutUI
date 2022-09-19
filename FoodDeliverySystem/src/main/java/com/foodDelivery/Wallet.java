package com.foodDelivery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Wallet extends conn{
    String balanceMoney;
    String userId;

    public Wallet(String userId) throws Exception {
        this.userId = userId;
        ResultSet rs = st.executeQuery("select * from loginDetails where userId='"+userId+"' and status='login'");
        if(rs.next()) {
            ResultSet balance = st.executeQuery("select balanceMoney from wallet where userId='"+userId+"'");
            if(balance.next()){
                balanceMoney = balance.getString("balanceMoney");
            }
        }
        else {
            throw new Exception("Login First!");
        }
    }

    void updateWallet() throws SQLException {
        int update = st.executeUpdate("update wallet set balanceMoney='"+balanceMoney+"' where userId='"+userId+"'");
    }
}
