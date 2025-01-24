package org.example.moneyapplication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mySql extends cashApplication {

    private static final Logger logger = LogManager.getLogger(mySql.class);

    Connection sqlConnect() throws SQLException, ClassNotFoundException {



        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bank";
        String user = "root";
        String password = "";

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);

        logger.info("Connection to Sql Server Established");
        System.out.println("Connected to database");



        return conn;

    }

    void addMoney(String user, String money) throws SQLException, ClassNotFoundException {

        double transfer = Double.parseDouble(money) + sqlCheckBalance(user);
        String totalMoney = String.valueOf(transfer);

        String query = "update cashBalance set balance = '" + totalMoney + "' where account = '" + user + "'";

        System.out.println("Added " + totalMoney + " to " + user);

        Statement stmt = sqlConnect().createStatement();
        stmt.executeUpdate(query);

        logger.info("SQL Updated (removed transaction) Contents of Database");

        sqlConnect().close();

    }

    void removeMoney(String user, String money) throws SQLException, ClassNotFoundException {

        double transfer =   sqlCheckBalance(user) - Double.parseDouble(money);
        String totalMoney = String.valueOf(transfer);


        String query = "update cashBalance set balance = '" + totalMoney + "' where account = '" + user + "'";

        System.out.println("Removed " + totalMoney + " from " + user);

        Statement stmt = sqlConnect().createStatement();
        stmt.executeUpdate(query);

        logger.info("Sql Updated (added transaction) Contents of Database");

        sqlConnect().close();

    }



    boolean checkLogin(String user, String password) throws SQLException, ClassNotFoundException {



        String query = "SELECT idbankAccounts,idPassword from bankAccounts where idbankAccounts = " + user;

        Statement stmt = sqlConnect().createStatement();
        ResultSet rs = stmt.executeQuery(query);



        String acct = "";
        String pass = "";

        while (rs.next()) {


           acct = rs.getObject(1).toString();
           pass = rs.getObject(2).toString();


        }
        logger.info("Authentication Confirmed");
        return user.equals(acct) && password.equals(pass);

    }

    double sqlCheckBalance(String account) throws SQLException, ClassNotFoundException {


        String query = "SELECT balance from cashBalance where account = " + account;
        Statement stmt = sqlConnect().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();

        List<Map<String, Object>> list = new ArrayList<>();

        while (rs.next()) {

            Map<String, Object> row = new HashMap<>(columnCount);

            for (int i = 1; i <= columnCount; i++) {

                row.put(metaData.getColumnName(i), rs.getObject(i));

            }

            list.add(row);

        }

        String result = list.get(0).toString();

        result = result.replace("{balance=","");
        result = result.replace("}","");

        double amount = Double.parseDouble(result);

        System.out.println(result);

        sqlConnect().close();

        logger.info("Balance Request Returned");

        return amount;


    }


}
