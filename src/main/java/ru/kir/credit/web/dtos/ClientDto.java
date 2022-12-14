package ru.kir.credit.web.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kir.credit.web.entities.Client;

@NoArgsConstructor
@Data
public class ClientDto {
    private String uuid;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String passportNumber;

    public ClientDto(Client client) {
        this.uuid = client.getUuid();
        this.fullName = client.getFullName();
        this.phoneNumber = client.getPhoneNumber();
        this.email = client.getEmail();
        this.passportNumber = client.getPassportNumber();
    }

}
