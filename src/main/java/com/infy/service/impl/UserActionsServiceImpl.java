/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserActionsService;
import com.infy.domain.UserActions;
import com.infy.repository.UserActionsRepository;
import com.infy.service.dto.UserActionsDTO;
import com.infy.service.mapper.UserActionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserActions}.
 */
@Service
@Transactional
public class UserActionsServiceImpl implements UserActionsService {

    private final Logger log = LoggerFactory.getLogger(UserActionsServiceImpl.class);

    private final UserActionsRepository userActionsRepository;

    private final UserActionsMapper userActionsMapper;

    public UserActionsServiceImpl(UserActionsRepository userActionsRepository, UserActionsMapper userActionsMapper) {
        this.userActionsRepository = userActionsRepository;
        this.userActionsMapper = userActionsMapper;
    }

    @Override
    public UserActionsDTO save(UserActionsDTO userActionsDTO) {
        log.debug("Request to save UserActions : {}", userActionsDTO);
        UserActions userActions = userActionsMapper.toEntity(userActionsDTO);
        userActions = userActionsRepository.save(userActions);
        return userActionsMapper.toDto(userActions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserActionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserActions");
        return userActionsRepository.findAll(pageable)
            .map(userActionsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserActionsDTO> findOne(Long id) {
        log.debug("Request to get UserActions : {}", id);
        return userActionsRepository.findById(id)
            .map(userActionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserActions : {}", id);
        userActionsRepository.deleteById(id);
    }
}
