package com.dmdev.spring.service;


import com.dmdev.spring.dto.CompanyReadDto;
import com.dmdev.spring.entity.Company;
import com.dmdev.spring.listener.entity.AccessType;
import com.dmdev.spring.listener.entity.EventEntity;
import com.dmdev.spring.mapper.CompanyReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.dmdev.spring.repository.CompanyRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyReadMapper companyReadMapper;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public Optional<CompanyReadDto> findById(Integer id){
        return companyRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EventEntity(entity, AccessType.READ));
                   return companyReadMapper.mapTo(entity);
                });
    }

    public List<CompanyReadDto> findAll(){
        return companyRepository.findAll().stream()
                .map(companyReadMapper::mapTo)
                .toList();
    }
}
