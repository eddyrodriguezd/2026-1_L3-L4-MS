package pe.edu.pucp.microservices.transactions.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AccountDto {
    private Long id;
    private String userId;
    private double balance;
    private LocalDateTime createdAt;
}