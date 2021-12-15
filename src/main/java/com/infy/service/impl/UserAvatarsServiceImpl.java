/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserAvatarsService;
import com.infy.domain.UserAvatars;
import com.infy.repository.UserAvatarsRepository;
import com.infy.service.dto.UserAvatarsDTO;
import com.infy.service.mapper.UserAvatarsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserAvatars}.
 */
@Service
@Transactional
public class UserAvatarsServiceImpl implements UserAvatarsService {

    private final Logger log = LoggerFactory.getLogger(UserAvatarsServiceImpl.class);

    private final UserAvatarsRepository userAvatarsRepository;

    private final UserAvatarsMapper userAvatarsMapper;

    public UserAvatarsServiceImpl(UserAvatarsRepository userAvatarsRepository, UserAvatarsMapper userAvatarsMapper) {
        this.userAvatarsRepository = userAvatarsRepository;
        this.userAvatarsMapper = userAvatarsMapper;
    }

    @Override
    public UserAvatarsDTO save(UserAvatarsDTO userAvatarsDTO) {
        log.debug("Request to save UserAvatars : {}", userAvatarsDTO);
        UserAvatars userAvatars = userAvatarsMapper.toEntity(userAvatarsDTO);
        userAvatars = userAvatarsRepository.save(userAvatars);
        return userAvatarsMapper.toDto(userAvatars);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAvatarsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAvatars");
        return userAvatarsRepository.findAll(pageable)
            .map(userAvatarsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserAvatarsDTO> findOne(Long id) {
        log.debug("Request to get UserAvatars : {}", id);
        return userAvatarsRepository.findById(id)
            .map(userAvatarsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAvatars : {}", id);
        userAvatarsRepository.deleteById(id);
    }
}
