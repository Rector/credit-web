package ru.kir.credit.web.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kir.credit.web.entities.Bank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class BankDto {
    private String uuid;
    private List<ClientDto> clientDtoList;
    private List<CreditDto> creditDtoList;

    public BankDto(Bank bank) {
        this.uuid = bank.getUuid();

        if (bank.getClientList() != null) {
            this.clientDtoList = bank.getClientList()
                    .stream()
                    .map(ClientDto::new)
                    .collect(Collectors.toList());
        } else {
            this.clientDtoList = new ArrayList<>();
        }

        if (bank.getCreditList() != null) {
            this.creditDtoList = bank.getCreditList()
                    .stream()
                    .map(CreditDto::new)
                    .collect(Collectors.toList());
        } else {
            this.creditDtoList = new ArrayList<>();
        }

    }

}
