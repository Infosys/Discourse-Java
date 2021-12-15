/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserEmailsService;
import com.infy.domain.UserEmails;
import com.infy.repository.UserEmailsRepository;
import com.infy.service.dto.UserEmailsDTO;
import com.infy.service.mapper.UserEmailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserEmails}.
 */
@Service
@Transactional
public class UserEmailsServiceImpl implements UserEmailsService {

    private final Logger log = LoggerFactory.getLogger(UserEmailsServiceImpl.class);

    private final UserEmailsRepository userEmailsRepository;

    private final UserEmailsMapper userEmailsMapper;

    public UserEmailsServiceImpl(UserEmailsRepository userEmailsRepository, UserEmailsMapper userEmailsMapper) {
        this.userEmailsRepository = userEmailsRepository;
        this.userEmailsMapper = userEmailsMapper;
    }

    @Override
    public UserEmailsDTO save(UserEmailsDTO userEmailsDTO) {
        log.debug("Request to save UserEmails : {}", userEmailsDTO);
        UserEmails userEmails = userEmailsMapper.toEntity(userEmailsDTO);
        userEmails = userEmailsRepository.save(userEmails);
        return userEmailsMapper.toDto(userEmails);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserEmailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserEmails");
        return userEmailsRepository.findAll(pageable)
            .map(userEmailsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserEmailsDTO> findOne(Long id) {
        log.debug("Request to get UserEmails : {}", id);
        return userEmailsRepository.findById(id)
            .map(userEmailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserEmails : {}", id);
        userEmailsRepository.deleteById(id);
    }
}
