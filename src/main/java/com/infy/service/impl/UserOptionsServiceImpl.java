/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserOptionsService;
import com.infy.domain.UserOptions;
import com.infy.repository.UserOptionsRepository;
import com.infy.service.dto.UserOptionsDTO;
import com.infy.service.mapper.UserOptionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserOptions}.
 */
@Service
@Transactional
public class UserOptionsServiceImpl implements UserOptionsService {

    private final Logger log = LoggerFactory.getLogger(UserOptionsServiceImpl.class);

    private final UserOptionsRepository userOptionsRepository;

    private final UserOptionsMapper userOptionsMapper;

    public UserOptionsServiceImpl(UserOptionsRepository userOptionsRepository, UserOptionsMapper userOptionsMapper) {
        this.userOptionsRepository = userOptionsRepository;
        this.userOptionsMapper = userOptionsMapper;
    }

    @Override
    public UserOptionsDTO save(UserOptionsDTO userOptionsDTO) {
        log.debug("Request to save UserOptions : {}", userOptionsDTO);
        UserOptions userOptions = userOptionsMapper.toEntity(userOptionsDTO);
        userOptions = userOptionsRepository.save(userOptions);
        return userOptionsMapper.toDto(userOptions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserOptionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserOptions");
        return userOptionsRepository.findAll(pageable)
            .map(userOptionsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserOptionsDTO> findOne(Long id) {
        log.debug("Request to get UserOptions : {}", id);
        return userOptionsRepository.findById(id)
            .map(userOptionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserOptions : {}", id);
        userOptionsRepository.deleteById(id);
    }
}
