package com.dmdev.spring.service;

import com.dmdev.spring.dao.CrudRepository;
import com.dmdev.spring.dto.CompanyReadDto;
import com.dmdev.spring.entity.Company;
import com.dmdev.spring.listener.entity.AccessType;
import com.dmdev.spring.listener.entity.EventEntity;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.dmdev.spring.repository.CompanyRepository;

import java.util.Optional;

@Service
public class CompanyService {
    private final CrudRepository<Integer, Company> companyRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CompanyService(CrudRepository<Integer, Company> companyRepository,
                          ApplicationEventPublisher applicationEventPublisher) {
        this.companyRepository = companyRepository;
        this.eventPublisher = applicationEventPublisher;
    }

    public Optional<CompanyReadDto> findById(Integer id){
        return companyRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EventEntity(entity, AccessType.READ));
                   return new CompanyReadDto(entity.id());
                });
    }
}
