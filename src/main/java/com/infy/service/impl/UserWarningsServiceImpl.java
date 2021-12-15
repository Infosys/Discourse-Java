/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserWarningsService;
import com.infy.domain.UserWarnings;
import com.infy.repository.UserWarningsRepository;
import com.infy.service.dto.UserWarningsDTO;
import com.infy.service.mapper.UserWarningsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserWarnings}.
 */
@Service
@Transactional
public class UserWarningsServiceImpl implements UserWarningsService {

    private final Logger log = LoggerFactory.getLogger(UserWarningsServiceImpl.class);

    private final UserWarningsRepository userWarningsRepository;

    private final UserWarningsMapper userWarningsMapper;

    public UserWarningsServiceImpl(UserWarningsRepository userWarningsRepository, UserWarningsMapper userWarningsMapper) {
        this.userWarningsRepository = userWarningsRepository;
        this.userWarningsMapper = userWarningsMapper;
    }

    @Override
    public UserWarningsDTO save(UserWarningsDTO userWarningsDTO) {
        log.debug("Request to save UserWarnings : {}", userWarningsDTO);
        UserWarnings userWarnings = userWarningsMapper.toEntity(userWarningsDTO);
        userWarnings = userWarningsRepository.save(userWarnings);
        return userWarningsMapper.toDto(userWarnings);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserWarningsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserWarnings");
        return userWarningsRepository.findAll(pageable)
            .map(userWarningsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserWarningsDTO> findOne(Long id) {
        log.debug("Request to get UserWarnings : {}", id);
        return userWarningsRepository.findById(id)
            .map(userWarningsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserWarnings : {}", id);
        userWarningsRepository.deleteById(id);
    }
}
