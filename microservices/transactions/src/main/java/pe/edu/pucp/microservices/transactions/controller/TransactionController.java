package pe.edu.pucp.microservices.transactions.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.pucp.microservices.transactions.dto.ErrorDto;
import pe.edu.pucp.microservices.transactions.dto.TransactionCreateDto;
import pe.edu.pucp.microservices.transactions.entity.Transaction;
import pe.edu.pucp.microservices.transactions.enums.TransactionType;
import pe.edu.pucp.microservices.transactions.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        return ResponseEntity.ok().body(transactionService.getAllTransactions());
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawMoney(@RequestBody TransactionCreateDto transactionCreateDto) {
        try {
            Transaction transaction = transactionService.createTransaction(transactionCreateDto, TransactionType.WITHDRAW);
            return ResponseEntity.ok().body(transaction);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            ErrorDto errorDto = new ErrorDto();
            errorDto.setError("Couldn't create withdraw transaction");
            return ResponseEntity.badRequest().body(errorDto);
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> depositMoney(@RequestBody TransactionCreateDto transactionCreateDto) {
        try {
            Transaction transaction = transactionService.createTransaction(transactionCreateDto, TransactionType.DEPOSIT);
            return ResponseEntity.ok().body(transaction);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            ErrorDto errorDto = new ErrorDto();
            errorDto.setError("Couldn't create withdraw transaction");
            return ResponseEntity.badRequest().body(errorDto);
        }
    }
}