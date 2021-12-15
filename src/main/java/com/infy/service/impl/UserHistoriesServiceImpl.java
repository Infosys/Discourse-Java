/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserHistoriesService;
import com.infy.domain.UserHistories;
import com.infy.repository.UserHistoriesRepository;
import com.infy.service.dto.UserHistoriesDTO;
import com.infy.service.mapper.UserHistoriesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserHistories}.
 */
@Service
@Transactional
public class UserHistoriesServiceImpl implements UserHistoriesService {

    private final Logger log = LoggerFactory.getLogger(UserHistoriesServiceImpl.class);

    private final UserHistoriesRepository userHistoriesRepository;

    private final UserHistoriesMapper userHistoriesMapper;

    public UserHistoriesServiceImpl(UserHistoriesRepository userHistoriesRepository, UserHistoriesMapper userHistoriesMapper) {
        this.userHistoriesRepository = userHistoriesRepository;
        this.userHistoriesMapper = userHistoriesMapper;
    }

    @Override
    public UserHistoriesDTO save(UserHistoriesDTO userHistoriesDTO) {
        log.debug("Request to save UserHistories : {}", userHistoriesDTO);
        UserHistories userHistories = userHistoriesMapper.toEntity(userHistoriesDTO);
        userHistories = userHistoriesRepository.save(userHistories);
        return userHistoriesMapper.toDto(userHistories);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserHistoriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserHistories");
        return userHistoriesRepository.findAll(pageable)
            .map(userHistoriesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserHistoriesDTO> findOne(Long id) {
        log.debug("Request to get UserHistories : {}", id);
        return userHistoriesRepository.findById(id)
            .map(userHistoriesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserHistories : {}", id);
        userHistoriesRepository.deleteById(id);
    }
}
