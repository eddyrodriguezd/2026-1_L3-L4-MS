package pe.edu.pucp.microservices.transactions.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.edu.pucp.microservices.transactions.dto.AccountDto;
import pe.edu.pucp.microservices.transactions.dto.UpdateBalanceDto;

@FeignClient(name = "accounts-service")
public interface AccountFeignClient {

    @GetMapping("/accounts/{accountId}")
    AccountDto getAccount(@PathVariable("accountId") Long accountId);

    @PutMapping("/accounts/{accountId}")
    void updateAccountBalance(@PathVariable("accountId") Long accountId, @RequestBody UpdateBalanceDto updateBalanceDto);
}
