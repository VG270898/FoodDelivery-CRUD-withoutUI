package com.foodDelivery;

import java.sql.ResultSet;

public class OrderDetails extends conn{
    public OrderDetails(String userId) throws Exception {
        ResultSet rs = st.executeQuery("select * from loginDetails where userId='"+userId+"' and status='login'");
        if(rs.next()) {
            ResultSet orders = st.executeQuery("select * from orders where userId='"+userId+"'");
            System.out.printf("\n%83s\n","Order Details");
            System.out.println("*****************************************************************************************************************************************************************");
            System.out.printf("\t%s%20s%25s%22s%28s%25s%22s\n","Order Id","Restaurant Id","Restaurant Name","Food Id's","Food Item's","Total Cost","Order Date");
            System.out.println("*****************************************************************************************************************************************************************");

            while (orders.next()){

                System.out.printf("|\t%-17s%-22s%-22s%-27s%-32s%-15s%7s  |\n",orders.getString("OrderId"),orders.getString("restaurantId"),orders.getString("restaurantName"),orders.getString("foodIds"),orders.getString("foodNames"),orders.getString("totalcost"),orders.getString("orderdate"));
            }
            System.out.println("*****************************************************************************************************************************************************************");

        }
        else{
            throw new Exception("Login First!");
        }
    }

//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//
//        try{
//            OrderDetails obj = new OrderDetails("vg270898");
//        }catch (Exception e){
//            System.out.println(e);
//        }
//    }
}
