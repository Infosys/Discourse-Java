/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserOpenIdsService;
import com.infy.domain.UserOpenIds;
import com.infy.repository.UserOpenIdsRepository;
import com.infy.service.dto.UserOpenIdsDTO;
import com.infy.service.mapper.UserOpenIdsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserOpenIds}.
 */
@Service
@Transactional
public class UserOpenIdsServiceImpl implements UserOpenIdsService {

    private final Logger log = LoggerFactory.getLogger(UserOpenIdsServiceImpl.class);

    private final UserOpenIdsRepository userOpenIdsRepository;

    private final UserOpenIdsMapper userOpenIdsMapper;

    public UserOpenIdsServiceImpl(UserOpenIdsRepository userOpenIdsRepository, UserOpenIdsMapper userOpenIdsMapper) {
        this.userOpenIdsRepository = userOpenIdsRepository;
        this.userOpenIdsMapper = userOpenIdsMapper;
    }

    @Override
    public UserOpenIdsDTO save(UserOpenIdsDTO userOpenIdsDTO) {
        log.debug("Request to save UserOpenIds : {}", userOpenIdsDTO);
        UserOpenIds userOpenIds = userOpenIdsMapper.toEntity(userOpenIdsDTO);
        userOpenIds = userOpenIdsRepository.save(userOpenIds);
        return userOpenIdsMapper.toDto(userOpenIds);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserOpenIdsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserOpenIds");
        return userOpenIdsRepository.findAll(pageable)
            .map(userOpenIdsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserOpenIdsDTO> findOne(Long id) {
        log.debug("Request to get UserOpenIds : {}", id);
        return userOpenIdsRepository.findById(id)
            .map(userOpenIdsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserOpenIds : {}", id);
        userOpenIdsRepository.deleteById(id);
    }
}
