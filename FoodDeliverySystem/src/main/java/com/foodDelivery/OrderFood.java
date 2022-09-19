package com.foodDelivery;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderFood extends conn {

    public OrderFood(String userId) throws Exception {
        Scanner scanner = new Scanner(System.in);

        ArrayList restaurant = new ArrayList();
        ArrayList food = new ArrayList();
        String foodnames ="";
        String restaurantName="";
        String totalCost="";
        String ch;
        int count=0;
        int v=-1;
        String fi = "";
        String[] restaurantDic={};
        int reorder=1;

        ResultSet rs = st.executeQuery("select * from loginDetails where userId='"+userId+"' and status='login'");
        if(rs.next()) {

            ResultSet res = st.executeQuery("select * from userDetails where userId='"+userId+"'");
            if(res.next()){
                String addr = res.getString("useraddress");
                ResultSet rest = st.executeQuery("select restaurantName,restaurants.restaurantId,foodItems.foodId,foodItems.foodName,foodItems.foodcost from restaurants inner join restaurantfooditem on restaurants.restaurantId = restaurantfooditem.restaurantId inner join foodItems on restaurantfooditem.foodId=foodItems.foodId where restaurantAddress='"+addr+"'");

                System.out.printf("%52s\n","Restaurant Availables Near "+addr.toUpperCase());
                System.out.println("***************************************************************************************************");
                System.out.printf("\t%10s%25s%15s%20s%18s\n","Restaurant Id","Restaurant Name","Food Id","Food Item","Food Cost");
                System.out.println("***************************************************************************************************");

                while (rest.next()){

                    if(!restaurant.contains(rest.getString("restaurantId"))){
                        System.out.println("---------------------------------------------------------------------------------------------------");
                        System.out.printf("\t\t%-20s%-23s%-15s%-15s%11s\n",rest.getString("restaurantId"),rest.getString("restaurantName"),rest.getString("foodId"),rest.getString("foodName"),rest.getString("foodCost"));
                        restaurant.add(rest.getString("restaurantId"));
                        count++;
                    }
                    else{
                    System.out.printf("\t\t%-20s%-23s%-15s%-15s%11s\n","   ","             ",rest.getString("foodId"),rest.getString("foodName"),rest.getString("foodCost"));
                    }
                }


                while(reorder!=0) {
                    foodnames="";
                    fi="";
                    System.out.println("---------------------------------------------------------------------------------------------------");
                    System.out.println("Select Restaurant Id and Food Id to make order");
                    System.out.println("For example: R101:F101,F102");
                    System.out.print("Enter Restaurant Id followed by Food Id : ");
                    String rf = scanner.nextLine();

                    try {
                        restaurantDic = rf.split(":");
                        ResultSet fo = st.executeQuery("select foodItems.foodId from restaurants inner join restaurantfooditem on restaurants.restaurantId = restaurantfooditem.restaurantId inner join foodItems on restaurantfooditem.foodId=foodItems.foodId where restaurantfooditem.restaurantId='"+restaurantDic[0]+"'");
                        while (fo.next()){
                            food.add(fo.getString("foodId"));
                        }
                        for (String f : restaurantDic[1].split(",")) {
                            if (!food.contains(f)) {
                                System.out.println("Food Id \"" + f + "\" does not exist for this Restaurant");
                            } else {
                                fi = fi + "\"" + f + "\",";
                            }

                        }
                    } catch (Exception e) {
                        throw new Exception("Enter in Correct Format!");
                    }
                    fi = fi + "\"\"";


                    if (!restaurant.contains(restaurantDic[0])) {
                        throw new Exception("Enter Correct Restaurant Id");
                    }
                    ResultSet rfname = st.executeQuery("select restaurants.restaurantName,foodItems.foodName,foodItems.foodcost from restaurants inner join restaurantfooditem on restaurants.restaurantId = restaurantfooditem.restaurantId inner join foodItems on restaurantfooditem.foodId=foodItems.foodId where restaurantfooditem.restaurantId='" + restaurantDic[0] + "' and restaurantfooditem.foodId in (" + fi + ")");
                    while (rfname.next()) {
                        restaurantName = rfname.getString("restaurantName");
                        foodnames += rfname.getString("foodName") + ", ";
                    }
                    ResultSet cost = st.executeQuery("select sum(foodcost) from restaurants inner join restaurantfooditem on restaurants.restaurantId = restaurantfooditem.restaurantId inner join foodItems on restaurantfooditem.foodId=foodItems.foodId where restaurants.restaurantId='" + restaurantDic[0] + "' and foodItems.foodId in (" + fi + ")");
                    if (cost.next()) {
                        totalCost = cost.getString("sum(foodcost)");
                    }


                    System.out.println("Order Details : ");
                    System.out.println("Restaurant Name : " + restaurantName);
                    System.out.println("Food Names : " + foodnames);
                    System.out.println("Total Cost : " + totalCost);

                    System.out.println("\t1. To Proceed with current Order");
                    System.out.println("\t2. To change current order");
                    System.out.println("\t3. To skip to main menu");
                    ch = scanner.nextLine();

                    switch (ch){
                        case "1":{
                            Wallet wallet = new Wallet(userId);
                            wallet.balanceMoney = String.valueOf(Double.parseDouble(wallet.balanceMoney)-Double.parseDouble(totalCost));
                            wallet.updateWallet();

                            System.out.println("Money ₹"+totalCost+" deducted from your wallet!");

                            System.out.println("Balance left in your Wallet : "+wallet.balanceMoney);

                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();
                            String orderDate = dtf.format(now);

                            randomalphnumeric random = new randomalphnumeric();
                            int updateOrder = st.executeUpdate("insert into orders values('" + random.orderId() + "','"+userId+"','" + restaurantDic[0] + "','" + restaurantName + "','" + fi + "','" + foodnames + "','" + totalCost + "','"+orderDate+"')");
                            if(updateOrder==1){
                                System.out.println("Order Placed Successfully!");
                                System.out.println("Thank you for placing Order");
                            }
                            else{
                                throw new Exception("Something went Wrong!");
                            }

                            System.out.print("Want to Order More ? (yes/no) : ");
                            String yn = scanner.nextLine();
                            if(yn.equals("yes")){
                                reorder=1;
                            }
                            else{
                                reorder=0;
                            }

                            break;
                        }//case 1 end here...

                        case "2":{
                            reorder=1;
                            break;
                        }//case 2 end here...

                        case "3":{
                            reorder=0;
                            break;
                        }//case 3 end here...
                        default:{
                            throw new Exception("Enter Correct Option...");
                        }
                    }
                }


            }
        }
        else{
            throw new Exception("Login First!");
        }
    }

//    public static void main(String[] args) throws Exception {
//        try {
//            OrderFood obj = new OrderFood("vg270898");
//        }catch (Exception e){
//            System.out.println(e);
//        }
//    }
}
