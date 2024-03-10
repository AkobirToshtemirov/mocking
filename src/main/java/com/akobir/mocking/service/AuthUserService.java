package com.akobir.mocking.service;

import com.akobir.mocking.dto.AuthUserCreateDTO;
import com.akobir.mocking.dto.AuthUserCriteria;
import com.akobir.mocking.dto.AuthUserGetDTO;
import com.akobir.mocking.dto.AuthUserUpdateDTO;

import java.util.List;

public interface AuthUserService {
    void create(AuthUserCreateDTO dto);

    void update(AuthUserUpdateDTO dto);

    void delete(String id);

    AuthUserGetDTO get(String id);

    List<AuthUserGetDTO> list(AuthUserCriteria criteria);
}
