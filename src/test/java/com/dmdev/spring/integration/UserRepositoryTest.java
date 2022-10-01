package com.dmdev.spring.integration;

import com.dmdev.spring.dto.PersonalInfo;
import com.dmdev.spring.dto.PersonalInfo2;
import com.dmdev.spring.dto.UserFilter;
import com.dmdev.spring.entity.Role;
import com.dmdev.spring.entity.User;
import com.dmdev.spring.integration.annotation.IT;
import com.dmdev.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
@Sql(scripts = {
        "classpath:sql/data.sql"
})
@RequiredArgsConstructor
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void batchTest(){
        List<User> users = userRepository.findAll();
//        userRepository.updateCompanyAndRole(users);
        userRepository.updateCompanyAndRoleNamed(users);
        System.out.println();
    }

    @Test
    void jdbcTest(){
        List<PersonalInfo> users = userRepository.findAllByCompanyIdAndRole(1, Role.USER);
        System.out.println();
    }

    @Test
    void auditingTest(){
        User user = userRepository.findById(1L).get();
        user.setBirthDate(user.getBirthDate().plusYears(1L));
        userRepository.flush();
        System.out.println();
    }

    @Test
    void customRepository(){
        UserFilter userFilter = UserFilter.builder().
                lastname("ov").
                birthDate(LocalDate.now()).
                build();
        List<User> users = userRepository.findAllByFilter(userFilter);
        System.out.println();
    }

    @Test
    void projectionsTest(){
        List<PersonalInfo2> users = userRepository.findAllByCompanyId(1);
        Assertions.assertThat(users).hasSize(2);
    }

    @Test
    void sliceTest(){
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by("id"));
        Page<User> allBy = userRepository.findAllBy(pageRequest);
        allBy.forEach(user -> System.out.println(user.getCompany().getName()));
        while (allBy.hasNext()){
            allBy = userRepository.findAllBy(allBy.nextPageable());
            allBy.forEach(user -> System.out.println(user.getCompany().getName()));
        }
    }

    @Test
    void pageableTest(){
        PageRequest pageRequest = PageRequest.of(1, 2, Sort.by("id"));
//        List<User> allBy = userRepository.findAllBy(pageRequest);
//        Assertions.assertThat(allBy).hasSize(2);
    }

    @Test
    void sortQuery(){

//        Sort sort = Sort.by("firstname").descending().and(Sort.by("lastname"));
        Sort.TypedSort<User> sort = Sort.sort(User.class);
        sort.by(User::getUsername);
        List<User> result = userRepository.findFirst3ByBirthDateBefore(LocalDate.now(), sort);
        Assertions.assertThat(result).hasSize(3);

//        Optional<User> firstByOrderByIdDesc = userRepository.findFirstByOrderByIdDesc();
//        assertTrue(firstByOrderByIdDesc.isPresent());
    }

    @Test
    void updateQuery(){
        User beforeUpdate = userRepository.getById(1L);
        assertSame(Role.ADMIN, beforeUpdate.getRole());
        beforeUpdate.setBirthDate(LocalDate.now());

        int result = userRepository.updateRole(Role.USER, 1L, 5L);
        assertSame(2, result);
        beforeUpdate.getCompany().getName();

        User afterUpdate = userRepository.getById(1L);
        assertSame(Role.USER, afterUpdate.getRole());
    }

    @Test
    void testQueries(){
        List<User> users = userRepository.findBy("a", "ov");
//        List<User> byUsername = userRepository.findByUsername("ivan@gmail.com");
        System.out.println(users);
    }

}