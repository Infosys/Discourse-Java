/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserCustomFieldsService;
import com.infy.domain.UserCustomFields;
import com.infy.repository.UserCustomFieldsRepository;
import com.infy.service.dto.UserCustomFieldsDTO;
import com.infy.service.mapper.UserCustomFieldsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserCustomFields}.
 */
@Service
@Transactional
public class UserCustomFieldsServiceImpl implements UserCustomFieldsService {

    private final Logger log = LoggerFactory.getLogger(UserCustomFieldsServiceImpl.class);

    private final UserCustomFieldsRepository userCustomFieldsRepository;

    private final UserCustomFieldsMapper userCustomFieldsMapper;

    public UserCustomFieldsServiceImpl(UserCustomFieldsRepository userCustomFieldsRepository, UserCustomFieldsMapper userCustomFieldsMapper) {
        this.userCustomFieldsRepository = userCustomFieldsRepository;
        this.userCustomFieldsMapper = userCustomFieldsMapper;
    }

    @Override
    public UserCustomFieldsDTO save(UserCustomFieldsDTO userCustomFieldsDTO) {
        log.debug("Request to save UserCustomFields : {}", userCustomFieldsDTO);
        UserCustomFields userCustomFields = userCustomFieldsMapper.toEntity(userCustomFieldsDTO);
        userCustomFields = userCustomFieldsRepository.save(userCustomFields);
        return userCustomFieldsMapper.toDto(userCustomFields);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserCustomFieldsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserCustomFields");
        return userCustomFieldsRepository.findAll(pageable)
            .map(userCustomFieldsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserCustomFieldsDTO> findOne(Long id) {
        log.debug("Request to get UserCustomFields : {}", id);
        return userCustomFieldsRepository.findById(id)
            .map(userCustomFieldsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserCustomFields : {}", id);
        userCustomFieldsRepository.deleteById(id);
    }
}
