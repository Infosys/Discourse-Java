/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.EmailLogsService;
import com.infy.domain.EmailLogs;
import com.infy.repository.EmailLogsRepository;
import com.infy.service.dto.EmailLogsDTO;
import com.infy.service.mapper.EmailLogsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmailLogs}.
 */
@Service
@Transactional
public class EmailLogsServiceImpl implements EmailLogsService {

    private final Logger log = LoggerFactory.getLogger(EmailLogsServiceImpl.class);

    private final EmailLogsRepository emailLogsRepository;

    private final EmailLogsMapper emailLogsMapper;

    public EmailLogsServiceImpl(EmailLogsRepository emailLogsRepository, EmailLogsMapper emailLogsMapper) {
        this.emailLogsRepository = emailLogsRepository;
        this.emailLogsMapper = emailLogsMapper;
    }

    @Override
    public EmailLogsDTO save(EmailLogsDTO emailLogsDTO) {
        log.debug("Request to save EmailLogs : {}", emailLogsDTO);
        EmailLogs emailLogs = emailLogsMapper.toEntity(emailLogsDTO);
        emailLogs = emailLogsRepository.save(emailLogs);
        return emailLogsMapper.toDto(emailLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmailLogsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailLogs");
        return emailLogsRepository.findAll(pageable)
            .map(emailLogsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EmailLogsDTO> findOne(Long id) {
        log.debug("Request to get EmailLogs : {}", id);
        return emailLogsRepository.findById(id)
            .map(emailLogsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailLogs : {}", id);
        emailLogsRepository.deleteById(id);
    }
}
