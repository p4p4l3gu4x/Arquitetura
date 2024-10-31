package com.fidelity.backend.controller;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.backend.model.dto.CompanyDTO;
import com.fidelity.backend.model.dto.converter.CompanyConverter;
import com.fidelity.backend.model.entity.CompanyEntity;
import com.fidelity.backend.service.CompanyService;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyConverter companyConverter;

    public CompanyController(final CompanyService companyService, final CompanyConverter companyConverter) {
        this.companyService = companyService;
        this.companyConverter = companyConverter;
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getCompanyList() {

        Set<CompanyEntity> companiesEntity = companyService.getAllCompanies();
        List<CompanyDTO> companiesDto = companiesEntity
                .stream()
                .map(companyEntity -> companyConverter.convertToDTO(companyEntity))
                .toList();

        return ResponseEntity.ok(companiesDto);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathParam("companyId") Long companyId) {

        CompanyEntity companyEntity = companyService.getCompanyById(companyId);
        CompanyDTO companyDTO = companyConverter.convertToDTO(companyEntity);

        return ResponseEntity.ok(companyDTO);
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) throws Exception {
        CompanyEntity companyEntity = companyConverter.convertToEntity(companyDTO);

        CompanyEntity companyEntityCreated = companyService.createCompany(companyEntity);

        CompanyDTO companyDTOCreated = companyConverter.convertToDTO(companyEntityCreated);

        return ResponseEntity.created(new URI("")).body(companyDTOCreated);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable("companyId") Long companyId,
            @RequestBody CompanyDTO companyDTO) {

        CompanyEntity companyEntity = companyConverter.convertToEntity(companyDTO);

        CompanyEntity companyEntityCreated = companyService.updateCompany(companyId, companyEntity);

        CompanyDTO companyDTOCreated = companyConverter.convertToDTO(companyEntityCreated);

        return ResponseEntity.ok().body(companyDTOCreated);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable("companyId") Long companyId) {

        companyService.deleteCompany(companyId);

        return ResponseEntity.noContent().build();
    }
}
