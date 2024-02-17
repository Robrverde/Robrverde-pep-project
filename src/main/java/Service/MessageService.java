package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    //No-args constructor
    public MessageService()
    {
        messageDAO = new MessageDAO();
    }

    //constructor for when an accountDAO is provided
    public MessageService(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    //add new message to the database
    public Message addMessage(Message message)
    {
        //check if the message to be added is valid (message_text is not blank, is not over 255 characters)
        if(message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255)
            return null;

        return messageDAO.createMessage(message);
    }

    //get all messages
    public List<Message> getAllMessages()
    {
        return messageDAO.getAllMessages();
    }

    //search message by ID
    public Message searchMessage(int message_id)
    {
        return messageDAO.searchMessageByID(message_id);
    }

    public Message deleteMessage(int message_id)
    {
        //check if the message exist, and the new text is valid (the new message_text is not blank and is not over 255 characters)
        Message message = messageDAO.searchMessageByID(message_id);
        
        //if the message exist delete it and return a reference of the deleted message
        if(message != null)
        {
            messageDAO.deleteMessageByID(message_id);
            return message;
        }

        return null;
    }

    public Message updateMessage(int message_id, Message message)
    {
        //check if the message exist, and the new text is valid (the new message_text is not blank and is not over 255 characters)
        if(messageDAO.searchMessageByID(message_id) != null && message.getMessage_text().length() > 0 && message.getMessage_text().length() <= 255)
        {
            messageDAO.updateMessage(message_id, message);
            return messageDAO.searchMessageByID(message_id);
        }

        return null;
    }
}
