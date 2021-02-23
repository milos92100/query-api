package org.queryapi.dto;

import java.time.Instant;

public class AccountDto {
    private Long id;
    private String name;
    private Long accountNumber;
    private Instant createdAt;

    public AccountDto() {
    }

    public AccountDto(Long id, String name, Long accountNumber) {
        this.id = id;
        this.name = name;
        this.accountNumber = accountNumber;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
