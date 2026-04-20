package pe.edu.pucp.microservices.transactions.service;

import org.springframework.stereotype.Service;
import pe.edu.pucp.microservices.transactions.client.AccountClient;
import pe.edu.pucp.microservices.transactions.dto.AccountDto;
import pe.edu.pucp.microservices.transactions.dto.TransactionCreateDto;
import pe.edu.pucp.microservices.transactions.dto.UpdateBalanceDto;
import pe.edu.pucp.microservices.transactions.entity.Transaction;
import pe.edu.pucp.microservices.transactions.enums.TransactionType;
import pe.edu.pucp.microservices.transactions.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;

    public TransactionService(TransactionRepository transactionRepository, AccountClient accountClient) {
        this.transactionRepository = transactionRepository;
        this.accountClient = accountClient;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction createTransaction(TransactionCreateDto transactionCreateDto, TransactionType transactionType) throws Exception {
        // Retrieve account from Account microservice
        AccountDto accountDto = accountClient.getAccount(transactionCreateDto.getAccountId());

        // Create entity
        Transaction transaction = new Transaction();
        transaction.setAccountId(transactionCreateDto.getAccountId());
        transaction.setTransactionType(transactionType);
        transaction.setAmount(transactionCreateDto.getAmount());

        // Validate balance (for withdraw operations)
        if(transactionType.equals(TransactionType.WITHDRAW)) {
            if(transactionCreateDto.getAmount() > accountDto.getBalance()) {
                System.out.println("Attempt to withdraw more than available balance");
                throw new Exception("Attempt to withdraw more than available balance");
            }
        }

        // Update balance
        UpdateBalanceDto updateBalanceDto = new UpdateBalanceDto();
        updateBalanceDto.setBalance(accountDto.getBalance() + (transactionCreateDto.getAmount() * (transactionType.equals(TransactionType.WITHDRAW) ? -1 : 1)));
        accountClient.updateAccountBalance(transactionCreateDto.getAccountId(), updateBalanceDto);

        // Call repository layer to save in database
        Transaction createdTransaction = transactionRepository.save(transaction);

        return createdTransaction;
    }
}
