/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserProfilesService;
import com.infy.domain.UserProfiles;
import com.infy.repository.UserProfilesRepository;
import com.infy.service.dto.UserProfilesDTO;
import com.infy.service.mapper.UserProfilesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserProfiles}.
 */
@Service
@Transactional
public class UserProfilesServiceImpl implements UserProfilesService {

    private final Logger log = LoggerFactory.getLogger(UserProfilesServiceImpl.class);

    private final UserProfilesRepository userProfilesRepository;

    private final UserProfilesMapper userProfilesMapper;

    public UserProfilesServiceImpl(UserProfilesRepository userProfilesRepository, UserProfilesMapper userProfilesMapper) {
        this.userProfilesRepository = userProfilesRepository;
        this.userProfilesMapper = userProfilesMapper;
    }

    @Override
    public UserProfilesDTO save(UserProfilesDTO userProfilesDTO) {
        log.debug("Request to save UserProfiles : {}", userProfilesDTO);
        UserProfiles userProfiles = userProfilesMapper.toEntity(userProfilesDTO);
        userProfiles = userProfilesRepository.save(userProfiles);
        return userProfilesMapper.toDto(userProfiles);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserProfilesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserProfiles");
        return userProfilesRepository.findAll(pageable)
            .map(userProfilesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserProfilesDTO> findOne(Long id) {
        log.debug("Request to get UserProfiles : {}", id);
        return userProfilesRepository.findById(id)
            .map(userProfilesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserProfiles : {}", id);
        userProfilesRepository.deleteById(id);
    }
}
