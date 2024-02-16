package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public Account insertAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();
        try 
        {
            //Write SQL logic to insert an entry into the database
            String sql = "INSERT INTO Account (username, password) VALUES(?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write the prepated statement set methods
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    public Account searchForAccountByUsername(String username, String password)
    {
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic to search for an entry in the database
            String sql = "SELECT * FROM Account WHERE Account.username = ? AND Account.password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's set methods.
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"), 
                        rs.getString("password"));

                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}