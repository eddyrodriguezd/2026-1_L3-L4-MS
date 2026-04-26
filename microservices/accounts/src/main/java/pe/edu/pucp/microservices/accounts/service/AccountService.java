package pe.edu.pucp.microservices.accounts.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.edu.pucp.microservices.accounts.client.EmailClient;
import pe.edu.pucp.microservices.accounts.client.UserClient;
import pe.edu.pucp.microservices.accounts.dto.AccountCreateDto;
import pe.edu.pucp.microservices.accounts.dto.AccountUpdateDto;
import pe.edu.pucp.microservices.accounts.dto.UserDto;
import pe.edu.pucp.microservices.accounts.entity.Account;
import pe.edu.pucp.microservices.accounts.repository.AccountRepository;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserClient userClient;
    private final EmailClient emailClient;

    @Value("${server.port}")
    private int serverPort;

    public AccountService(AccountRepository accountRepository, UserClient userClient, EmailClient emailClient) {
        this.accountRepository = accountRepository;
        this.userClient = userClient;
        this.emailClient = emailClient;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long accountId) throws Exception {
        // Call repository layer to retrieve account from database
        Account account = accountRepository.findById(accountId).orElse(null);

        if(account == null) {
            System.out.println("Account couldn't be found");
            throw new Exception("Account couldn't be found");
        }

        account.setPort(serverPort);
        return account;
    }

    public Account createAccount(AccountCreateDto accountCreateDto) {
        // Retrieve user from Users microservice
        UserDto userDto = userClient.getUser(accountCreateDto.getUserId());

        // Create account
        Account account = new Account();
        account.setBalance(0);
        account.setUserId(accountCreateDto.getUserId());

        // Call repository layer to save in DB
        Account createdAccount = accountRepository.save(account);

        // Send email
        emailClient.sendEmail(userDto.getEmail());

        return createdAccount;
    }

    public Account updateAccount(Long accountId, AccountUpdateDto accountUpdateDto) throws Exception {
        // Call repository layer to retrieve account from database
        Account account = accountRepository.findById(accountId).orElse(null);

        if(account == null) {
            System.out.println("Account couldn't be found");
            throw new Exception("Account couldn't be found");
        }

        account.setBalance(accountUpdateDto.getBalance()); // Update account balance

        return accountRepository.save(account);
    }
}
