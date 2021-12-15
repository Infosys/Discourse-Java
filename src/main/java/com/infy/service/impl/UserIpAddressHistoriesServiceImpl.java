/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserIpAddressHistoriesService;
import com.infy.domain.UserIpAddressHistories;
import com.infy.repository.UserIpAddressHistoriesRepository;
import com.infy.service.dto.UserIpAddressHistoriesDTO;
import com.infy.service.mapper.UserIpAddressHistoriesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserIpAddressHistories}.
 */
@Service
@Transactional
public class UserIpAddressHistoriesServiceImpl implements UserIpAddressHistoriesService {

    private final Logger log = LoggerFactory.getLogger(UserIpAddressHistoriesServiceImpl.class);

    private final UserIpAddressHistoriesRepository userIpAddressHistoriesRepository;

    private final UserIpAddressHistoriesMapper userIpAddressHistoriesMapper;

    public UserIpAddressHistoriesServiceImpl(UserIpAddressHistoriesRepository userIpAddressHistoriesRepository, UserIpAddressHistoriesMapper userIpAddressHistoriesMapper) {
        this.userIpAddressHistoriesRepository = userIpAddressHistoriesRepository;
        this.userIpAddressHistoriesMapper = userIpAddressHistoriesMapper;
    }

    @Override
    public UserIpAddressHistoriesDTO save(UserIpAddressHistoriesDTO userIpAddressHistoriesDTO) {
        log.debug("Request to save UserIpAddressHistories : {}", userIpAddressHistoriesDTO);
        UserIpAddressHistories userIpAddressHistories = userIpAddressHistoriesMapper.toEntity(userIpAddressHistoriesDTO);
        userIpAddressHistories = userIpAddressHistoriesRepository.save(userIpAddressHistories);
        return userIpAddressHistoriesMapper.toDto(userIpAddressHistories);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserIpAddressHistoriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserIpAddressHistories");
        return userIpAddressHistoriesRepository.findAll(pageable)
            .map(userIpAddressHistoriesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserIpAddressHistoriesDTO> findOne(Long id) {
        log.debug("Request to get UserIpAddressHistories : {}", id);
        return userIpAddressHistoriesRepository.findById(id)
            .map(userIpAddressHistoriesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserIpAddressHistories : {}", id);
        userIpAddressHistoriesRepository.deleteById(id);
    }
}
