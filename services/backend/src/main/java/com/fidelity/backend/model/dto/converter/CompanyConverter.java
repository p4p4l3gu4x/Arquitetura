package com.fidelity.backend.model.dto.converter;

import org.springframework.stereotype.Component;

import com.fidelity.backend.model.dto.CompanyDTO;
import com.fidelity.backend.model.entity.CompanyEntity;

@Component
public class CompanyConverter implements BaseEntityConverter<CompanyEntity, CompanyDTO> {

    @Override
    public CompanyEntity convertToEntity(final CompanyDTO dto) {
        CompanyEntity entity = new CompanyEntity();

        entity.setId(dto.getId());
        entity.setCompanyName(dto.getCompanyName());
        entity.setCompanyNumber(dto.getCompanyNumber());

        return entity;
    }

    @Override
    public CompanyDTO convertToDTO(final CompanyEntity entity) {
        CompanyDTO dto = new CompanyDTO();

        dto.setId(entity.getId());
        dto.setCompanyName(entity.getCompanyName());
        dto.setCompanyNumber(entity.getCompanyNumber());

        return dto;
    }

}
