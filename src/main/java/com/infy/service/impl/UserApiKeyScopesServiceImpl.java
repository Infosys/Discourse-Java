/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserApiKeyScopesService;
import com.infy.domain.UserApiKeyScopes;
import com.infy.repository.UserApiKeyScopesRepository;
import com.infy.service.dto.UserApiKeyScopesDTO;
import com.infy.service.mapper.UserApiKeyScopesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserApiKeyScopes}.
 */
@Service
@Transactional
public class UserApiKeyScopesServiceImpl implements UserApiKeyScopesService {

    private final Logger log = LoggerFactory.getLogger(UserApiKeyScopesServiceImpl.class);

    private final UserApiKeyScopesRepository userApiKeyScopesRepository;

    private final UserApiKeyScopesMapper userApiKeyScopesMapper;

    public UserApiKeyScopesServiceImpl(UserApiKeyScopesRepository userApiKeyScopesRepository, UserApiKeyScopesMapper userApiKeyScopesMapper) {
        this.userApiKeyScopesRepository = userApiKeyScopesRepository;
        this.userApiKeyScopesMapper = userApiKeyScopesMapper;
    }

    @Override
    public UserApiKeyScopesDTO save(UserApiKeyScopesDTO userApiKeyScopesDTO) {
        log.debug("Request to save UserApiKeyScopes : {}", userApiKeyScopesDTO);
        UserApiKeyScopes userApiKeyScopes = userApiKeyScopesMapper.toEntity(userApiKeyScopesDTO);
        userApiKeyScopes = userApiKeyScopesRepository.save(userApiKeyScopes);
        return userApiKeyScopesMapper.toDto(userApiKeyScopes);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserApiKeyScopesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserApiKeyScopes");
        return userApiKeyScopesRepository.findAll(pageable)
            .map(userApiKeyScopesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserApiKeyScopesDTO> findOne(Long id) {
        log.debug("Request to get UserApiKeyScopes : {}", id);
        return userApiKeyScopesRepository.findById(id)
            .map(userApiKeyScopesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserApiKeyScopes : {}", id);
        userApiKeyScopesRepository.deleteById(id);
    }
}
