package ru.kir.credit.web.error_handling;

import lombok.Data;

import java.util.*;

@Data
public class CreditWebError {
    private int status;
    private List<String> messages;
    private Date timestamp;

    public CreditWebError(int status, String... messages) {
        this.status = status;
        this.messages = new ArrayList<>(Arrays.asList(messages));
        this.timestamp = new Date();
    }

    public CreditWebError(int status, Collection<String> messages) {
        this.status = status;
        this.messages = new ArrayList<>(messages);
        this.timestamp = new Date();
    }

}