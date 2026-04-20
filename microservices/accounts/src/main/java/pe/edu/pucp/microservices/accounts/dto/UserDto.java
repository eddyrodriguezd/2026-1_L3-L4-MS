package pe.edu.pucp.microservices.accounts.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userId;
    private String givenName;
    private String surname;
    private String email;
}
