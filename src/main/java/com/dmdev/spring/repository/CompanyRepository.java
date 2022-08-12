package com.dmdev.spring.repository;

import com.dmdev.spring.bpp.Auditing;
import com.dmdev.spring.bpp.Transactional;
import com.dmdev.spring.dao.CrudRepository;
import com.dmdev.spring.entity.Company;
import com.dmdev.spring.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Slf4j
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
@Auditing
@Repository
@RequiredArgsConstructor
public class CompanyRepository implements CrudRepository<Integer, Company> {

    private final ConnectionPool pool1;
    @Value("${db.pool.size}")
    private final Integer poolSize;
    private final List<ConnectionPool> pools;

    @PostConstruct
    private void init(){
        log.info("init company repository");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        log.info("findById method...");
        return Optional.of(new Company(id, null, Collections.emptyMap()));
    }

    @Override
    public void delete(Integer id) {
        log.info("delete method...");
    }

}
