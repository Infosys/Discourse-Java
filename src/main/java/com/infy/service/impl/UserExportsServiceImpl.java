/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserExportsService;
import com.infy.domain.UserExports;
import com.infy.repository.UserExportsRepository;
import com.infy.service.dto.UserExportsDTO;
import com.infy.service.mapper.UserExportsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserExports}.
 */
@Service
@Transactional
public class UserExportsServiceImpl implements UserExportsService {

    private final Logger log = LoggerFactory.getLogger(UserExportsServiceImpl.class);

    private final UserExportsRepository userExportsRepository;

    private final UserExportsMapper userExportsMapper;

    public UserExportsServiceImpl(UserExportsRepository userExportsRepository, UserExportsMapper userExportsMapper) {
        this.userExportsRepository = userExportsRepository;
        this.userExportsMapper = userExportsMapper;
    }

    @Override
    public UserExportsDTO save(UserExportsDTO userExportsDTO) {
        log.debug("Request to save UserExports : {}", userExportsDTO);
        UserExports userExports = userExportsMapper.toEntity(userExportsDTO);
        userExports = userExportsRepository.save(userExports);
        return userExportsMapper.toDto(userExports);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserExportsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserExports");
        return userExportsRepository.findAll(pageable)
            .map(userExportsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserExportsDTO> findOne(Long id) {
        log.debug("Request to get UserExports : {}", id);
        return userExportsRepository.findById(id)
            .map(userExportsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserExports : {}", id);
        userExportsRepository.deleteById(id);
    }
}
