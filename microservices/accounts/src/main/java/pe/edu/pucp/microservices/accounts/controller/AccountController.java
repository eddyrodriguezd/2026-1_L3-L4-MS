package pe.edu.pucp.microservices.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.pucp.microservices.accounts.dto.AccountCreateDto;
import pe.edu.pucp.microservices.accounts.dto.AccountUpdateDto;
import pe.edu.pucp.microservices.accounts.dto.ErrorDto;
import pe.edu.pucp.microservices.accounts.entity.Account;
import pe.edu.pucp.microservices.accounts.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
        return ResponseEntity.ok().body(accountService.getAllAccounts());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable("accountId") Long accountId) {
        try {
            Account account = accountService.getAccountById(accountId);
            return ResponseEntity.ok().body(account);
        }
        catch (Exception e) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setError("Couldn't retrieve account");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
        }
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountCreateDto accountCreateDto) {
        try {
            Account account = accountService.createAccount(accountCreateDto);
            return ResponseEntity.ok().body(account);
        }
        catch (Exception e) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setError("Couldn't create account");
            return ResponseEntity.badRequest().body(errorDto);
        }
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<?> updateAccount(@PathVariable("accountId") Long accountId, @RequestBody AccountUpdateDto accountUpdateDto) {
        System.out.println("Updating account = <" + accountId + ">");
        try {
            Account updatedAccount = accountService.updateAccount(accountId, accountUpdateDto);
            return ResponseEntity.ok().body(updatedAccount);
        }
        catch (Exception e) {
            ErrorDto error = new ErrorDto();
            error.setError("Couldn't update account");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
