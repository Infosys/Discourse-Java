/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserAuthTokenLogsService;
import com.infy.domain.UserAuthTokenLogs;
import com.infy.repository.UserAuthTokenLogsRepository;
import com.infy.service.dto.UserAuthTokenLogsDTO;
import com.infy.service.mapper.UserAuthTokenLogsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserAuthTokenLogs}.
 */
@Service
@Transactional
public class UserAuthTokenLogsServiceImpl implements UserAuthTokenLogsService {

    private final Logger log = LoggerFactory.getLogger(UserAuthTokenLogsServiceImpl.class);

    private final UserAuthTokenLogsRepository userAuthTokenLogsRepository;

    private final UserAuthTokenLogsMapper userAuthTokenLogsMapper;

    public UserAuthTokenLogsServiceImpl(UserAuthTokenLogsRepository userAuthTokenLogsRepository, UserAuthTokenLogsMapper userAuthTokenLogsMapper) {
        this.userAuthTokenLogsRepository = userAuthTokenLogsRepository;
        this.userAuthTokenLogsMapper = userAuthTokenLogsMapper;
    }

    @Override
    public UserAuthTokenLogsDTO save(UserAuthTokenLogsDTO userAuthTokenLogsDTO) {
        log.debug("Request to save UserAuthTokenLogs : {}", userAuthTokenLogsDTO);
        UserAuthTokenLogs userAuthTokenLogs = userAuthTokenLogsMapper.toEntity(userAuthTokenLogsDTO);
        userAuthTokenLogs = userAuthTokenLogsRepository.save(userAuthTokenLogs);
        return userAuthTokenLogsMapper.toDto(userAuthTokenLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAuthTokenLogsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAuthTokenLogs");
        return userAuthTokenLogsRepository.findAll(pageable)
            .map(userAuthTokenLogsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserAuthTokenLogsDTO> findOne(Long id) {
        log.debug("Request to get UserAuthTokenLogs : {}", id);
        return userAuthTokenLogsRepository.findById(id)
            .map(userAuthTokenLogsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAuthTokenLogs : {}", id);
        userAuthTokenLogsRepository.deleteById(id);
    }
}
