/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserBadgesService;
import com.infy.domain.UserBadges;
import com.infy.repository.UserBadgesRepository;
import com.infy.service.dto.UserBadgesDTO;
import com.infy.service.mapper.UserBadgesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserBadges}.
 */
@Service
@Transactional
public class UserBadgesServiceImpl implements UserBadgesService {

    private final Logger log = LoggerFactory.getLogger(UserBadgesServiceImpl.class);

    private final UserBadgesRepository userBadgesRepository;

    private final UserBadgesMapper userBadgesMapper;

    public UserBadgesServiceImpl(UserBadgesRepository userBadgesRepository, UserBadgesMapper userBadgesMapper) {
        this.userBadgesRepository = userBadgesRepository;
        this.userBadgesMapper = userBadgesMapper;
    }

    @Override
    public UserBadgesDTO save(UserBadgesDTO userBadgesDTO) {
        log.debug("Request to save UserBadges : {}", userBadgesDTO);
        UserBadges userBadges = userBadgesMapper.toEntity(userBadgesDTO);
        userBadges = userBadgesRepository.save(userBadges);
        return userBadgesMapper.toDto(userBadges);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserBadgesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserBadges");
        return userBadgesRepository.findAll(pageable)
            .map(userBadgesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserBadgesDTO> findOne(Long id) {
        log.debug("Request to get UserBadges : {}", id);
        return userBadgesRepository.findById(id)
            .map(userBadgesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserBadges : {}", id);
        userBadgesRepository.deleteById(id);
    }
}
