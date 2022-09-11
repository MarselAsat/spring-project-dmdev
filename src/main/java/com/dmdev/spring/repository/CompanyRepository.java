package com.dmdev.spring.repository;

import com.dmdev.spring.bpp.Auditing;
import com.dmdev.spring.bpp.Transactional;
import com.dmdev.spring.entity.Company;
import com.dmdev.spring.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Optional<Company> findByName(@Param("name") String name);

    List<Company> findByNameContainingIgnoreCase(String fragment);
}
