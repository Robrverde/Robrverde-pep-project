package Service;

import Model.Message;
import DAO.MessageDAO;

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
}
