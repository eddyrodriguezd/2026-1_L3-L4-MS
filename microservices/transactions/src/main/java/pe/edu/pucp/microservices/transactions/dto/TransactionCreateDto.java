package pe.edu.pucp.microservices.transactions.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionCreateDto {
    private Long accountId;
    private double amount;
}
