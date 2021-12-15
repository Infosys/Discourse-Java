/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserAuthTokensService;
import com.infy.domain.UserAuthTokens;
import com.infy.repository.UserAuthTokensRepository;
import com.infy.service.dto.UserAuthTokensDTO;
import com.infy.service.mapper.UserAuthTokensMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserAuthTokens}.
 */
@Service
@Transactional
public class UserAuthTokensServiceImpl implements UserAuthTokensService {

    private final Logger log = LoggerFactory.getLogger(UserAuthTokensServiceImpl.class);

    private final UserAuthTokensRepository userAuthTokensRepository;

    private final UserAuthTokensMapper userAuthTokensMapper;

    public UserAuthTokensServiceImpl(UserAuthTokensRepository userAuthTokensRepository, UserAuthTokensMapper userAuthTokensMapper) {
        this.userAuthTokensRepository = userAuthTokensRepository;
        this.userAuthTokensMapper = userAuthTokensMapper;
    }

    @Override
    public UserAuthTokensDTO save(UserAuthTokensDTO userAuthTokensDTO) {
        log.debug("Request to save UserAuthTokens : {}", userAuthTokensDTO);
        UserAuthTokens userAuthTokens = userAuthTokensMapper.toEntity(userAuthTokensDTO);
        userAuthTokens = userAuthTokensRepository.save(userAuthTokens);
        return userAuthTokensMapper.toDto(userAuthTokens);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAuthTokensDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAuthTokens");
        return userAuthTokensRepository.findAll(pageable)
            .map(userAuthTokensMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserAuthTokensDTO> findOne(Long id) {
        log.debug("Request to get UserAuthTokens : {}", id);
        return userAuthTokensRepository.findById(id)
            .map(userAuthTokensMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAuthTokens : {}", id);
        userAuthTokensRepository.deleteById(id);
    }
}
