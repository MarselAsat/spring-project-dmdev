package com.dmdev.spring.service;


import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.dto.UserFilter;
import com.dmdev.spring.dto.UserReadDto;
import com.dmdev.spring.entity.QUser;
import com.dmdev.spring.entity.User;
import com.dmdev.spring.mapper.UserCreateEditMapper;
import com.dmdev.spring.mapper.UserReadMapper;
import com.dmdev.spring.repository.CompanyRepository;
import com.dmdev.spring.repository.QPredicate;
import com.dmdev.spring.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserCreateEditMapper userCreateEditMapper;
    private final UserReadMapper userReadMapper;
    private final ImageService imageService;

//    @PostFilter("filterObject.role.name().equals('ADMIN')")
//    @PostFilter("@companyService.findById(filterObject.company.id()).isPresent()")
    public Page<UserReadDto> findAll(UserFilter userFilter, Pageable pageable) {
        Predicate predicate = QPredicate.builder()
                .add(userFilter.getFirstname(), QUser.user.firstname::containsIgnoreCase)
                .add(userFilter.getLastname(), QUser.user.lastname::containsIgnoreCase)
                .add(userFilter.getBirthDate(), QUser.user.birthDate::loe)
                .build();

        return userRepository.findAll(predicate, pageable)
                .map(userReadMapper::mapTo);
    }
    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::mapTo)
                .toList();
    }

    @PreAuthorize("hasAuthority('ADMIN', 'USER')")
    public Optional<UserReadDto> findById(Long id){
        return userRepository.findById(id)
                .map(userReadMapper::mapTo);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto user) {
        return Optional.ofNullable(user)
                .map(dto -> {
                    uploadImage(user.getImage());
                    return userCreateEditMapper.mapTo(user);
                })
                .map(userRepository::save)
                .map(userReadMapper::mapTo)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long userId, UserCreateEditDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.
                map(user -> {
                    uploadImage(userDto.getImage());
                    return userCreateEditMapper.mapTo(userDto, user);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::mapTo);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    @Transactional
    public boolean delete(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    userRepository.flush();
                    return true;
                }).orElse(false);
    }

    public Optional<byte[]> findAvatar(Long id){
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed ti retrieve user: " + username));
    }
}
