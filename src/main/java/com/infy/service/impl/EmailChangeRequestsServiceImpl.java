/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.EmailChangeRequestsService;
import com.infy.domain.EmailChangeRequests;
import com.infy.repository.EmailChangeRequestsRepository;
import com.infy.service.dto.EmailChangeRequestsDTO;
import com.infy.service.mapper.EmailChangeRequestsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmailChangeRequests}.
 */
@Service
@Transactional
public class EmailChangeRequestsServiceImpl implements EmailChangeRequestsService {

    private final Logger log = LoggerFactory.getLogger(EmailChangeRequestsServiceImpl.class);

    private final EmailChangeRequestsRepository emailChangeRequestsRepository;

    private final EmailChangeRequestsMapper emailChangeRequestsMapper;

    public EmailChangeRequestsServiceImpl(EmailChangeRequestsRepository emailChangeRequestsRepository, EmailChangeRequestsMapper emailChangeRequestsMapper) {
        this.emailChangeRequestsRepository = emailChangeRequestsRepository;
        this.emailChangeRequestsMapper = emailChangeRequestsMapper;
    }

    @Override
    public EmailChangeRequestsDTO save(EmailChangeRequestsDTO emailChangeRequestsDTO) {
        log.debug("Request to save EmailChangeRequests : {}", emailChangeRequestsDTO);
        EmailChangeRequests emailChangeRequests = emailChangeRequestsMapper.toEntity(emailChangeRequestsDTO);
        emailChangeRequests = emailChangeRequestsRepository.save(emailChangeRequests);
        return emailChangeRequestsMapper.toDto(emailChangeRequests);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmailChangeRequestsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailChangeRequests");
        return emailChangeRequestsRepository.findAll(pageable)
            .map(emailChangeRequestsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EmailChangeRequestsDTO> findOne(Long id) {
        log.debug("Request to get EmailChangeRequests : {}", id);
        return emailChangeRequestsRepository.findById(id)
            .map(emailChangeRequestsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailChangeRequests : {}", id);
        emailChangeRequestsRepository.deleteById(id);
    }
}
