package Controller;

import Model.Account;
import Model.Message;

import Service.AccountService;
//TODO: import Service.MessageService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    //MessageService messageService;

    public SocialMediaController()
    {
        this.accountService = new AccountService();
        //this.MessageService = new MessageService();
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

    private void newMessagesHandler(Context context) {
        context.json("sample text");
    }

    private void getAllMessagesHandler(Context context) {
        context.json("sample text");
    }

    private void getMessageByIDHandler(Context context) {
        context.json("sample text");
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