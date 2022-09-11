package com.dmdev.spring.repository;

import com.dmdev.spring.dto.PersonalInfo;
import com.dmdev.spring.dto.PersonalInfo2;
import com.dmdev.spring.entity.Role;
import com.dmdev.spring.entity.User;
import com.dmdev.spring.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends
        JpaRepository<User, Long>,
        FilterUserRepository,
        RevisionRepository<User, Long, Integer>,
        QuerydslPredicateExecutor<User> {

    @Query("select u from User u where u.firstname like %:firstname% and u.lastname like %:lastname%")
    List<User> findBy(@Param("firstname") String firstname, @Param("lastname")  String lastname);

    @Query(nativeQuery = true,
    value = "select u.* from users u where username like :username")
    List<User> findByUsername(@Param("username") String username);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User u " +
            "set u.role = :role " +
            "where u.id in (:ids)")
    int updateRole(@Param("role") Role role, @Param("ids") Long... ids);

    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    List<User> findFirst3ByBirthDateBefore(LocalDate date, Sort sort);

//    List<User> findAllBy(Pageable pageable);

    @EntityGraph(attributePaths = {"company", "company.companyLocales"})
    @Query(value = "select u from User u",
    countQuery = "select count(distinct u.firstname) from User u")
    Page<User> findAllBy(Pageable pageable);

    @Query(value = "select " +
            "firstname, " +
            "lastname, " +
            "birth_date birthDate " +
            "from users " +
            "where company_id = :companyId",
            nativeQuery = true)
    List<PersonalInfo2> findAllByCompanyId(@Param("companyId") Integer companyId);


}
