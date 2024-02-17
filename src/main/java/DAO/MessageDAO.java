package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;

import java.util.List;
import java.util.ArrayList;

public class MessageDAO {

    public Message createMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();
        try 
        {
            //Write SQL logic to insert an entry into the database
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write the prepated statement set methods
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    //get all messages
    public List<Message> getAllMessages()
    {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                rs.getInt("posted_by"), 
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));

                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }

    //get message by ID
    public Message searchMessageByID(int message_id)
    {
        Connection connection = ConnectionUtil.getConnection();

        try 
        {
            //Write SQL logic here
            String sql = "SELECT * FROM Message WHERE Message.message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                Message message = new Message(rs.getInt("message_id"), 
                rs.getInt("posted_by"), 
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));

                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void deleteMessageByID(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "DELETE FROM Message WHERE Message.message_id = ?;";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1, message_id);
            
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateMessage(int message_id, Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "UPDATE Message SET Message.message_text = ? WHERE Message.message_id = ?;";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //Set values
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message_id);
            
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
