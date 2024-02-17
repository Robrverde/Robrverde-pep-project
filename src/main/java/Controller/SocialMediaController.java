package Controller;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController()
    {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::userRegistrationHandler);
        app.post("login", this::userLoginHandler);

        app.post("messages", this::newMessagesHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIDHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);

        app.get("accounts/{account_id}/messages", this::getAllMessagesByUserHandler);

        return app;
    }

    private void userRegistrationHandler(Context context) throws JsonProcessingException 
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
 
        Account addedAccount = accountService.addAccount(account);

        //check if the account was added succesfully
        if(addedAccount != null)
            context.json(mapper.writeValueAsString(addedAccount));
        else
            context.status(400);
    }

    private void userLoginHandler(Context context) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
 
        Account login = accountService.loginAccount(account.getUsername(), account.getPassword());
        //check if the account was added succesfully
        if(login != null)
            context.json(mapper.writeValueAsString(login));
        else
            context.status(401);
    }

    private void newMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
 
        Message addedMessage = messageService.addMessage(message);

        //check if the account was added succesfully and if posted_by refers to a real, existing user
        if(addedMessage != null && accountService.searchAccount(addedMessage.getPosted_by()) != null)
            context.json(mapper.writeValueAsString(addedMessage));
        else
            context.status(400);
    }

    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageByIDHandler(Context context) {
        Message message = messageService.searchMessage(Integer.parseInt(context.pathParam("message_id")));

        if(message != null)
            context.json(message);
        else
            context.json("");
    }

    private void deleteMessageHandler(Context context) {
        context.json("sample text");
    }

    private void updateMessageHandler(Context context) {
        context.json("sample text");
    }

    private void getAllMessagesByUserHandler(Context context) {
        context.json("sample text");
    }


}