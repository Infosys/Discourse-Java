/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserApiKeysService;
import com.infy.domain.UserApiKeys;
import com.infy.repository.UserApiKeysRepository;
import com.infy.service.dto.UserApiKeysDTO;
import com.infy.service.mapper.UserApiKeysMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserApiKeys}.
 */
@Service
@Transactional
public class UserApiKeysServiceImpl implements UserApiKeysService {

    private final Logger log = LoggerFactory.getLogger(UserApiKeysServiceImpl.class);

    private final UserApiKeysRepository userApiKeysRepository;

    private final UserApiKeysMapper userApiKeysMapper;

    public UserApiKeysServiceImpl(UserApiKeysRepository userApiKeysRepository, UserApiKeysMapper userApiKeysMapper) {
        this.userApiKeysRepository = userApiKeysRepository;
        this.userApiKeysMapper = userApiKeysMapper;
    }

    @Override
    public UserApiKeysDTO save(UserApiKeysDTO userApiKeysDTO) {
        log.debug("Request to save UserApiKeys : {}", userApiKeysDTO);
        UserApiKeys userApiKeys = userApiKeysMapper.toEntity(userApiKeysDTO);
        userApiKeys = userApiKeysRepository.save(userApiKeys);
        return userApiKeysMapper.toDto(userApiKeys);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserApiKeysDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserApiKeys");
        return userApiKeysRepository.findAll(pageable)
            .map(userApiKeysMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserApiKeysDTO> findOne(Long id) {
        log.debug("Request to get UserApiKeys : {}", id);
        return userApiKeysRepository.findById(id)
            .map(userApiKeysMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserApiKeys : {}", id);
        userApiKeysRepository.deleteById(id);
    }
}
