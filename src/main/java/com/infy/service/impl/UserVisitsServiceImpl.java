/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserVisitsService;
import com.infy.domain.UserVisits;
import com.infy.repository.UserVisitsRepository;
import com.infy.service.dto.UserVisitsDTO;
import com.infy.service.mapper.UserVisitsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserVisits}.
 */
@Service
@Transactional
public class UserVisitsServiceImpl implements UserVisitsService {

    private final Logger log = LoggerFactory.getLogger(UserVisitsServiceImpl.class);

    private final UserVisitsRepository userVisitsRepository;

    private final UserVisitsMapper userVisitsMapper;

    public UserVisitsServiceImpl(UserVisitsRepository userVisitsRepository, UserVisitsMapper userVisitsMapper) {
        this.userVisitsRepository = userVisitsRepository;
        this.userVisitsMapper = userVisitsMapper;
    }

    @Override
    public UserVisitsDTO save(UserVisitsDTO userVisitsDTO) {
        log.debug("Request to save UserVisits : {}", userVisitsDTO);
        UserVisits userVisits = userVisitsMapper.toEntity(userVisitsDTO);
        userVisits = userVisitsRepository.save(userVisits);
        return userVisitsMapper.toDto(userVisits);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserVisitsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserVisits");
        return userVisitsRepository.findAll(pageable)
            .map(userVisitsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserVisitsDTO> findOne(Long id) {
        log.debug("Request to get UserVisits : {}", id);
        return userVisitsRepository.findById(id)
            .map(userVisitsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserVisits : {}", id);
        userVisitsRepository.deleteById(id);
    }
}
