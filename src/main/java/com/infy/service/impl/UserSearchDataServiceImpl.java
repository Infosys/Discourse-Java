/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserSearchDataService;
import com.infy.domain.UserSearchData;
import com.infy.repository.UserSearchDataRepository;
import com.infy.service.dto.UserSearchDataDTO;
import com.infy.service.mapper.UserSearchDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserSearchData}.
 */
@Service
@Transactional
public class UserSearchDataServiceImpl implements UserSearchDataService {

    private final Logger log = LoggerFactory.getLogger(UserSearchDataServiceImpl.class);

    private final UserSearchDataRepository userSearchDataRepository;

    private final UserSearchDataMapper userSearchDataMapper;

    public UserSearchDataServiceImpl(UserSearchDataRepository userSearchDataRepository, UserSearchDataMapper userSearchDataMapper) {
        this.userSearchDataRepository = userSearchDataRepository;
        this.userSearchDataMapper = userSearchDataMapper;
    }

    @Override
    public UserSearchDataDTO save(UserSearchDataDTO userSearchDataDTO) {
        log.debug("Request to save UserSearchData : {}", userSearchDataDTO);
        UserSearchData userSearchData = userSearchDataMapper.toEntity(userSearchDataDTO);
        userSearchData = userSearchDataRepository.save(userSearchData);
        return userSearchDataMapper.toDto(userSearchData);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserSearchDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserSearchData");
        return userSearchDataRepository.findAll(pageable)
            .map(userSearchDataMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserSearchDataDTO> findOne(Long id) {
        log.debug("Request to get UserSearchData : {}", id);
        return userSearchDataRepository.findById(id)
            .map(userSearchDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserSearchData : {}", id);
        userSearchDataRepository.deleteById(id);
    }
}
