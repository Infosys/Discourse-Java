/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserSecondFactorsService;
import com.infy.domain.UserSecondFactors;
import com.infy.repository.UserSecondFactorsRepository;
import com.infy.service.dto.UserSecondFactorsDTO;
import com.infy.service.mapper.UserSecondFactorsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserSecondFactors}.
 */
@Service
@Transactional
public class UserSecondFactorsServiceImpl implements UserSecondFactorsService {

    private final Logger log = LoggerFactory.getLogger(UserSecondFactorsServiceImpl.class);

    private final UserSecondFactorsRepository userSecondFactorsRepository;

    private final UserSecondFactorsMapper userSecondFactorsMapper;

    public UserSecondFactorsServiceImpl(UserSecondFactorsRepository userSecondFactorsRepository, UserSecondFactorsMapper userSecondFactorsMapper) {
        this.userSecondFactorsRepository = userSecondFactorsRepository;
        this.userSecondFactorsMapper = userSecondFactorsMapper;
    }

    @Override
    public UserSecondFactorsDTO save(UserSecondFactorsDTO userSecondFactorsDTO) {
        log.debug("Request to save UserSecondFactors : {}", userSecondFactorsDTO);
        UserSecondFactors userSecondFactors = userSecondFactorsMapper.toEntity(userSecondFactorsDTO);
        userSecondFactors = userSecondFactorsRepository.save(userSecondFactors);
        return userSecondFactorsMapper.toDto(userSecondFactors);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserSecondFactorsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserSecondFactors");
        return userSecondFactorsRepository.findAll(pageable)
            .map(userSecondFactorsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserSecondFactorsDTO> findOne(Long id) {
        log.debug("Request to get UserSecondFactors : {}", id);
        return userSecondFactorsRepository.findById(id)
            .map(userSecondFactorsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserSecondFactors : {}", id);
        userSecondFactorsRepository.deleteById(id);
    }
}
