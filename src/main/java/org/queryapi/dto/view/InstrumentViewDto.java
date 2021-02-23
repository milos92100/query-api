package org.queryapi.dto.view;

public class InstrumentViewDto {
    private Long id;
    private String name;
    private String market;

    public InstrumentViewDto() {
    }

    public InstrumentViewDto(Long id, String name, String market) {
        this.id = id;
        this.name = name;
        this.market = market;
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

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

}

