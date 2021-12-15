/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserFieldsService;
import com.infy.domain.UserFields;
import com.infy.repository.UserFieldsRepository;
import com.infy.service.dto.UserFieldsDTO;
import com.infy.service.mapper.UserFieldsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserFields}.
 */
@Service
@Transactional
public class UserFieldsServiceImpl implements UserFieldsService {

    private final Logger log = LoggerFactory.getLogger(UserFieldsServiceImpl.class);

    private final UserFieldsRepository userFieldsRepository;

    private final UserFieldsMapper userFieldsMapper;

    public UserFieldsServiceImpl(UserFieldsRepository userFieldsRepository, UserFieldsMapper userFieldsMapper) {
        this.userFieldsRepository = userFieldsRepository;
        this.userFieldsMapper = userFieldsMapper;
    }

    @Override
    public UserFieldsDTO save(UserFieldsDTO userFieldsDTO) {
        log.debug("Request to save UserFields : {}", userFieldsDTO);
        UserFields userFields = userFieldsMapper.toEntity(userFieldsDTO);
        userFields = userFieldsRepository.save(userFields);
        return userFieldsMapper.toDto(userFields);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserFieldsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserFields");
        return userFieldsRepository.findAll(pageable)
            .map(userFieldsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserFieldsDTO> findOne(Long id) {
        log.debug("Request to get UserFields : {}", id);
        return userFieldsRepository.findById(id)
            .map(userFieldsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserFields : {}", id);
        userFieldsRepository.deleteById(id);
    }
}
