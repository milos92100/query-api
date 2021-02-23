package org.queryapi.dto.view;

import java.time.Instant;

public class OrderViewDto {
    private Long id;
    private AccountViewDto account;
    private InstrumentViewDto instrument;
    private Double volume;
    private Double limit;
    private Instant createdAt;

    public OrderViewDto() {
    }

    public OrderViewDto(Long id, AccountViewDto account, InstrumentViewDto instrument, Double volume, Double limit) {
        this.id = id;
        this.account = account;
        this.instrument = instrument;
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

    public AccountViewDto getAccount() {
        return account;
    }

    public void setAccount(AccountViewDto account) {
        this.account = account;
    }

    public InstrumentViewDto getInstrument() {
        return instrument;
    }

    public void setInstrument(InstrumentViewDto instrument) {
        this.instrument = instrument;
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
