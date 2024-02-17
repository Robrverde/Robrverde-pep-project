package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;

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
    
}
