/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.EmailTokensService;
import com.infy.domain.EmailTokens;
import com.infy.repository.EmailTokensRepository;
import com.infy.service.dto.EmailTokensDTO;
import com.infy.service.mapper.EmailTokensMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmailTokens}.
 */
@Service
@Transactional
public class EmailTokensServiceImpl implements EmailTokensService {

    private final Logger log = LoggerFactory.getLogger(EmailTokensServiceImpl.class);

    private final EmailTokensRepository emailTokensRepository;

    private final EmailTokensMapper emailTokensMapper;

    public EmailTokensServiceImpl(EmailTokensRepository emailTokensRepository, EmailTokensMapper emailTokensMapper) {
        this.emailTokensRepository = emailTokensRepository;
        this.emailTokensMapper = emailTokensMapper;
    }

    @Override
    public EmailTokensDTO save(EmailTokensDTO emailTokensDTO) {
        log.debug("Request to save EmailTokens : {}", emailTokensDTO);
        EmailTokens emailTokens = emailTokensMapper.toEntity(emailTokensDTO);
        emailTokens = emailTokensRepository.save(emailTokens);
        return emailTokensMapper.toDto(emailTokens);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmailTokensDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailTokens");
        return emailTokensRepository.findAll(pageable)
            .map(emailTokensMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EmailTokensDTO> findOne(Long id) {
        log.debug("Request to get EmailTokens : {}", id);
        return emailTokensRepository.findById(id)
            .map(emailTokensMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailTokens : {}", id);
        emailTokensRepository.deleteById(id);
    }
}
