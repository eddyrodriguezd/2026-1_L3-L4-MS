package pe.edu.pucp.microservices.accounts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailDto {
    private String recipient;
    private String subject;
    private String body;
}
