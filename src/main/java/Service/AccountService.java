package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    //No-args constructor
    public AccountService()
    {
        accountDAO = new AccountDAO();
    }

    //constructor for when an accountDAO is provided
    public AccountService(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    //add new account to the database
    public Account addAccount(Account account)
    {
        //check if the account to be added is valid (username is not blank, password longer than 4 chars, and unique username)
        if(account.getUsername().length() == 0 || account.getPassword().length() < 4 || searchAccount(account.getAccount_id()) != null)
            return null;

        return accountDAO.insertAccount(account);
    }

    public Account loginAccount(String username, String password)
    {
        return accountDAO.searchAccountByUsername(username, password);
    }

    public Account searchAccount(int account_id)
    {
        return accountDAO.searchAccountByID(account_id);
    }


}
