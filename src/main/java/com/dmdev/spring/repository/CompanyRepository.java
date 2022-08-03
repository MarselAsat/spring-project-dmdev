package com.dmdev.spring.repository;

import com.dmdev.spring.bpp.Auditing;
import com.dmdev.spring.bpp.InjectBeans;
import com.dmdev.spring.bpp.Transactional;
import com.dmdev.spring.dao.CrudRepository;
import com.dmdev.spring.entity.Company;
import com.dmdev.spring.pool.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Transactional
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {


    private ConnectionPool pool1;
    @Value("${db.pool.size}")
    private Integer poolSize;
    @Autowired
    private List<ConnectionPool> pools;

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


    @Autowired
    public void setPool1(ConnectionPool pool1) {
        this.pool1 = pool1;
    }
}
