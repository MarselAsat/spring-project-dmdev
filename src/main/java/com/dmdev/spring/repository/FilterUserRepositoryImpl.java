package com.dmdev.spring.repository;

import com.dmdev.spring.dto.PersonalInfo;
import com.dmdev.spring.dto.UserFilter;
import com.dmdev.spring.entity.QUser;
import com.dmdev.spring.entity.Role;
import com.dmdev.spring.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private static final String FIND_ALL_BY_COMPANY_ROLE = """
            select 
            firstname, 
            lastname, 
            birth_date 
            from users 
            where company_id = ? 
            and role = ?
            """;

    private static final String BATCH_UPDATE_COMPANY_AND_ROLE = """
            update users
            set company_id = ?, 
                role = ? 
            where id = ?
            """;
    private static final String BATCH_UPDATE_COMPANY_AND_ROLE_NAMED = """
            update users
            set company_id = :company_id, 
                role = :role 
            where id = :id
            """;

    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public List<User> findAllByFilter(UserFilter userFilter) {

        com.querydsl.core.types.Predicate predicate = QPredicate.builder()
                .add(userFilter.getFirstname(), QUser.user.firstname::containsIgnoreCase)
                .add(userFilter.getLastname(), QUser.user.lastname::containsIgnoreCase)
                .add(userFilter.getBirthDate(), QUser.user.birthDate::before)
                .build();

        return new JPAQuery<User>(entityManager)
                .select(QUser.user)
                .from(QUser.user)
                .where(predicate)
                .fetch();
    }

    @Override
    public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {
        return jdbcTemplate.query(FIND_ALL_BY_COMPANY_ROLE, (rs, rowNum) -> new PersonalInfo(
                rs.getString("firstname"),
         rs.getString("lastname"),
         rs.getDate("birth_date").toLocalDate()), companyId, role.name());
    }

    @Override
    public void updateCompanyAndRole(List<User> users) {
        List<Object[]> args = users.stream()
                .map(user -> new Object[]{user.getCompany().getId(), user.getRole().name(), user.getId()})
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate(BATCH_UPDATE_COMPANY_AND_ROLE, args);
    }

    @Override
    public void updateCompanyAndRoleNamed(List<User> users) {
        MapSqlParameterSource[] args = users.stream()
                .map(user -> Map.of(
                        "company_id", user.getCompany().getId(),
                        "role", user.getRole().name(),
                        "id", user.getId()))
                .map(MapSqlParameterSource::new)
                .toArray(MapSqlParameterSource[]::new);

        namedJdbcTemplate.batchUpdate(BATCH_UPDATE_COMPANY_AND_ROLE_NAMED, args);
    }
}
