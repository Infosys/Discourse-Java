/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserSecurityKeysService;
import com.infy.domain.UserSecurityKeys;
import com.infy.repository.UserSecurityKeysRepository;
import com.infy.service.dto.UserSecurityKeysDTO;
import com.infy.service.mapper.UserSecurityKeysMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserSecurityKeys}.
 */
@Service
@Transactional
public class UserSecurityKeysServiceImpl implements UserSecurityKeysService {

    private final Logger log = LoggerFactory.getLogger(UserSecurityKeysServiceImpl.class);

    private final UserSecurityKeysRepository userSecurityKeysRepository;

    private final UserSecurityKeysMapper userSecurityKeysMapper;

    public UserSecurityKeysServiceImpl(UserSecurityKeysRepository userSecurityKeysRepository, UserSecurityKeysMapper userSecurityKeysMapper) {
        this.userSecurityKeysRepository = userSecurityKeysRepository;
        this.userSecurityKeysMapper = userSecurityKeysMapper;
    }

    @Override
    public UserSecurityKeysDTO save(UserSecurityKeysDTO userSecurityKeysDTO) {
        log.debug("Request to save UserSecurityKeys : {}", userSecurityKeysDTO);
        UserSecurityKeys userSecurityKeys = userSecurityKeysMapper.toEntity(userSecurityKeysDTO);
        userSecurityKeys = userSecurityKeysRepository.save(userSecurityKeys);
        return userSecurityKeysMapper.toDto(userSecurityKeys);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserSecurityKeysDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserSecurityKeys");
        return userSecurityKeysRepository.findAll(pageable)
            .map(userSecurityKeysMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserSecurityKeysDTO> findOne(Long id) {
        log.debug("Request to get UserSecurityKeys : {}", id);
        return userSecurityKeysRepository.findById(id)
            .map(userSecurityKeysMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserSecurityKeys : {}", id);
        userSecurityKeysRepository.deleteById(id);
    }
}
