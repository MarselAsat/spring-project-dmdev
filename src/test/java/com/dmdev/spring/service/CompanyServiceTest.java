package com.dmdev.spring.service;

import com.dmdev.spring.dao.CrudRepository;
import com.dmdev.spring.dto.CompanyReadDto;
import com.dmdev.spring.entity.Company;
import com.dmdev.spring.listener.entity.EventEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    private static final Integer COMPANY_ID = 1;

    @Mock
    private CrudRepository<Integer,Company> companyRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Company(COMPANY_ID, null, Collections.emptyMap())))
                .when(companyRepository).findById(COMPANY_ID);

        Optional<CompanyReadDto> actualResult = companyService.findById(COMPANY_ID);

        assertTrue(actualResult.isPresent());

        CompanyReadDto expectedResult = new CompanyReadDto(COMPANY_ID);

        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));

        verify(eventPublisher).publishEvent(any(EventEntity.class));
    }
}