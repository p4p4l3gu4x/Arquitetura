package com.fidelity.backend.model.dto;

public class CompanyDTO extends BaseDTO<Long> {

    private Long id;
    private String companyName;
    private String companyNumber;// CNPJ

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
