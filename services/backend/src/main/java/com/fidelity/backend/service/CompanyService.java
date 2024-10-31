package com.fidelity.backend.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.fidelity.backend.model.entity.CompanyEntity;

@Service
public class CompanyService {

    public Set<CompanyEntity> getAllCompanies() {
        Set<CompanyEntity> listCompanies = Set.of();

        return listCompanies;
    }

    public CompanyEntity getCompanyById(final Long companyId) {
        // pega e empresa do banco de dados
        return null;
    }

    public CompanyEntity createCompany(final CompanyEntity companyEntity) {
        Boolean companyAlreadyExists = verifyCompanyAlreadyExists(companyEntity);
        if (companyAlreadyExists) {
            // return a exception
        }

        return companyEntity;
    }

    private Boolean verifyCompanyAlreadyExists(final CompanyEntity companyEntity) {
        Boolean companyAlreadyExists = Boolean.FALSE;
        // verifica no banco de dados se a empresa existe

        return companyAlreadyExists;
    }

    public CompanyEntity updateCompany(final Long companyId, final CompanyEntity companyEntity) {
        CompanyEntity companyById = getCompanyById(companyId);
        if (companyById == null) {
            // throw Exception Not Found
        }

        // atualiza os dados

        return companyEntity;
    }

    public void deleteCompany(final Long companyId) {
        CompanyEntity companyById = getCompanyById(companyId);
        if (companyById == null) {
            // throw Exception Not Found
        }

        // delete a empresa;

        throw new UnsupportedOperationException("Unimplemented method 'deleteCompany'");
    }

}
