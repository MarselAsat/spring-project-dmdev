package com.dmdev.spring.integration.service;

import com.dmdev.spring.config.DatabaseProperties;
import com.dmdev.spring.dto.CompanyReadDto;
import com.dmdev.spring.integration.annotation.IT;
import com.dmdev.spring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@IT
@RequiredArgsConstructor
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = ApplicationRunner.class, initializers = ConfigDataApplicationContextInitializer.class)
public class CompanyServiceIT {

    private static final Integer COMPANY_ID = 1;

    private final CompanyService companyService;
    private final DatabaseProperties databaseProperties;

    @Test
    void findById(){

        Optional<CompanyReadDto> actualResult = companyService.findById(COMPANY_ID);
        CompanyReadDto expectedResult = new CompanyReadDto(COMPANY_ID, null);
        System.out.println(databaseProperties.password());

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }
}



