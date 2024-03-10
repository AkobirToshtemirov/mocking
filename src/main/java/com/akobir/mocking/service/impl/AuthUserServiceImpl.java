package com.akobir.mocking.service.impl;

import com.akobir.mocking.dto.AuthUserCreateDTO;
import com.akobir.mocking.dto.AuthUserCriteria;
import com.akobir.mocking.dto.AuthUserGetDTO;
import com.akobir.mocking.dto.AuthUserUpdateDTO;
import com.akobir.mocking.entity.AuthUser;
import com.akobir.mocking.repository.AuthUserRepository;
import com.akobir.mocking.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;

    @Autowired
    public AuthUserServiceImpl(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public void create(AuthUserCreateDTO dto) {
        AuthUser authUser = new AuthUser();
        authUser.setUsername(dto.username());
        authUser.setEmail(dto.email());
        authUser.setPassword(dto.password());
        authUserRepository.save(authUser);
    }

    @Override
    public void update(AuthUserUpdateDTO dto) {
        AuthUser authUser = authUserRepository.findById(dto.id())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        authUser.setUsername(dto.username());
        authUser.setEmail(dto.email());
        authUser.setPassword(dto.password());

        authUserRepository.save(authUser);
    }

    @Override
    public void delete(String id) {
        authUserRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public AuthUserGetDTO get(String id) {
        AuthUser authUser = authUserRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new AuthUserGetDTO(
                authUser.getId(),
                authUser.getUsername(),
                authUser.getEmail()
        );
    }

    @Override
    public List<AuthUserGetDTO> list(AuthUserCriteria criteria) {
        List<AuthUser> authUsers = authUserRepository.findAll();
        return authUsers.stream()
                .map(user -> new AuthUserGetDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }

}
