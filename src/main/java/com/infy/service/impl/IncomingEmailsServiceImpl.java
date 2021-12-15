/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.IncomingEmailsService;
import com.infy.domain.IncomingEmails;
import com.infy.repository.IncomingEmailsRepository;
import com.infy.service.dto.IncomingEmailsDTO;
import com.infy.service.mapper.IncomingEmailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IncomingEmails}.
 */
@Service
@Transactional
public class IncomingEmailsServiceImpl implements IncomingEmailsService {

    private final Logger log = LoggerFactory.getLogger(IncomingEmailsServiceImpl.class);

    private final IncomingEmailsRepository incomingEmailsRepository;

    private final IncomingEmailsMapper incomingEmailsMapper;

    public IncomingEmailsServiceImpl(IncomingEmailsRepository incomingEmailsRepository, IncomingEmailsMapper incomingEmailsMapper) {
        this.incomingEmailsRepository = incomingEmailsRepository;
        this.incomingEmailsMapper = incomingEmailsMapper;
    }

    @Override
    public IncomingEmailsDTO save(IncomingEmailsDTO incomingEmailsDTO) {
        log.debug("Request to save IncomingEmails : {}", incomingEmailsDTO);
        IncomingEmails incomingEmails = incomingEmailsMapper.toEntity(incomingEmailsDTO);
        incomingEmails = incomingEmailsRepository.save(incomingEmails);
        return incomingEmailsMapper.toDto(incomingEmails);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IncomingEmailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IncomingEmails");
        return incomingEmailsRepository.findAll(pageable)
            .map(incomingEmailsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<IncomingEmailsDTO> findOne(Long id) {
        log.debug("Request to get IncomingEmails : {}", id);
        return incomingEmailsRepository.findById(id)
            .map(incomingEmailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IncomingEmails : {}", id);
        incomingEmailsRepository.deleteById(id);
    }
}
