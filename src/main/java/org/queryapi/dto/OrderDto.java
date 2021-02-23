package org.queryapi.dto;

import java.time.Instant;

public class OrderDto {
    private Long id;
    private Long accountId;
    private Long instrumentId;
    private Double volume;
    private Double limit;
    private Instant createdAt;

    public OrderDto() {
    }

    public OrderDto(Long id, Long accountId, Long instrumentId, Double volume, Double limit) {
        this.id = id;
        this.accountId = accountId;
        this.instrumentId = instrumentId;
        this.volume = volume;
        this.limit = limit;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
