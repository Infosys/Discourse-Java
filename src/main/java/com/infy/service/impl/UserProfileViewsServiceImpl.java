/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserProfileViewsService;
import com.infy.domain.UserProfileViews;
import com.infy.repository.UserProfileViewsRepository;
import com.infy.service.dto.UserProfileViewsDTO;
import com.infy.service.mapper.UserProfileViewsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserProfileViews}.
 */
@Service
@Transactional
public class UserProfileViewsServiceImpl implements UserProfileViewsService {

    private final Logger log = LoggerFactory.getLogger(UserProfileViewsServiceImpl.class);

    private final UserProfileViewsRepository userProfileViewsRepository;

    private final UserProfileViewsMapper userProfileViewsMapper;

    public UserProfileViewsServiceImpl(UserProfileViewsRepository userProfileViewsRepository, UserProfileViewsMapper userProfileViewsMapper) {
        this.userProfileViewsRepository = userProfileViewsRepository;
        this.userProfileViewsMapper = userProfileViewsMapper;
    }

    @Override
    public UserProfileViewsDTO save(UserProfileViewsDTO userProfileViewsDTO) {
        log.debug("Request to save UserProfileViews : {}", userProfileViewsDTO);
        UserProfileViews userProfileViews = userProfileViewsMapper.toEntity(userProfileViewsDTO);
        userProfileViews = userProfileViewsRepository.save(userProfileViews);
        return userProfileViewsMapper.toDto(userProfileViews);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserProfileViewsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserProfileViews");
        return userProfileViewsRepository.findAll(pageable)
            .map(userProfileViewsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserProfileViewsDTO> findOne(Long id) {
        log.debug("Request to get UserProfileViews : {}", id);
        return userProfileViewsRepository.findById(id)
            .map(userProfileViewsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserProfileViews : {}", id);
        userProfileViewsRepository.deleteById(id);
    }
}
