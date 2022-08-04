package com.dmdev.spring.service;

import com.dmdev.spring.dao.CrudRepository;
import com.dmdev.spring.dto.CompanyReadDto;
import com.dmdev.spring.entity.Company;
import org.springframework.stereotype.Service;
import com.dmdev.spring.repository.CompanyRepository;

import java.util.Optional;

@Service
public class CompanyService {
    private final CrudRepository<Integer, Company> companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Optional<CompanyReadDto> findById(Integer id){
        return companyRepository.findById(id)
                .map(entity -> new CompanyReadDto(entity.id()));
    }
}
