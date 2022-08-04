package com.dmdev.spring.repository;

import com.dmdev.spring.bpp.Auditing;
import com.dmdev.spring.bpp.Transactional;
import com.dmdev.spring.dao.CrudRepository;
import com.dmdev.spring.entity.Company;
import com.dmdev.spring.pool.ConnectionPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;


@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

    private final ConnectionPool pool1;
    private final Integer poolSize;
    private final List<ConnectionPool> pools;


    public CompanyRepository(ConnectionPool pool1,
                             @Value("${db.pool.size}")Integer poolSize,
                             List<ConnectionPool> pools) {
        this.pool1 = pool1;
        this.poolSize = poolSize;
        this.pools = pools;
    }

    @PostConstruct
    private void init(){
        System.out.println("init company repository");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        System.out.println("findById method...");
        return Optional.of(new Company(id));
    }

    @Override
    public void delete(Integer id) {
        System.out.println("delete method...");
    }

}
