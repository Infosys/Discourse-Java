/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserFieldOptionsService;
import com.infy.domain.UserFieldOptions;
import com.infy.repository.UserFieldOptionsRepository;
import com.infy.service.dto.UserFieldOptionsDTO;
import com.infy.service.mapper.UserFieldOptionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserFieldOptions}.
 */
@Service
@Transactional
public class UserFieldOptionsServiceImpl implements UserFieldOptionsService {

    private final Logger log = LoggerFactory.getLogger(UserFieldOptionsServiceImpl.class);

    private final UserFieldOptionsRepository userFieldOptionsRepository;

    private final UserFieldOptionsMapper userFieldOptionsMapper;

    public UserFieldOptionsServiceImpl(UserFieldOptionsRepository userFieldOptionsRepository, UserFieldOptionsMapper userFieldOptionsMapper) {
        this.userFieldOptionsRepository = userFieldOptionsRepository;
        this.userFieldOptionsMapper = userFieldOptionsMapper;
    }

    @Override
    public UserFieldOptionsDTO save(UserFieldOptionsDTO userFieldOptionsDTO) {
        log.debug("Request to save UserFieldOptions : {}", userFieldOptionsDTO);
        UserFieldOptions userFieldOptions = userFieldOptionsMapper.toEntity(userFieldOptionsDTO);
        userFieldOptions = userFieldOptionsRepository.save(userFieldOptions);
        return userFieldOptionsMapper.toDto(userFieldOptions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserFieldOptionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserFieldOptions");
        return userFieldOptionsRepository.findAll(pageable)
            .map(userFieldOptionsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserFieldOptionsDTO> findOne(Long id) {
        log.debug("Request to get UserFieldOptions : {}", id);
        return userFieldOptionsRepository.findById(id)
            .map(userFieldOptionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserFieldOptions : {}", id);
        userFieldOptionsRepository.deleteById(id);
    }
}
