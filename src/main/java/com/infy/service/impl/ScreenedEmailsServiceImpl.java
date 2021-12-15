/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ScreenedEmailsService;
import com.infy.domain.ScreenedEmails;
import com.infy.repository.ScreenedEmailsRepository;
import com.infy.service.dto.ScreenedEmailsDTO;
import com.infy.service.mapper.ScreenedEmailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ScreenedEmails}.
 */
@Service
@Transactional
public class ScreenedEmailsServiceImpl implements ScreenedEmailsService {

    private final Logger log = LoggerFactory.getLogger(ScreenedEmailsServiceImpl.class);

    private final ScreenedEmailsRepository screenedEmailsRepository;

    private final ScreenedEmailsMapper screenedEmailsMapper;

    public ScreenedEmailsServiceImpl(ScreenedEmailsRepository screenedEmailsRepository, ScreenedEmailsMapper screenedEmailsMapper) {
        this.screenedEmailsRepository = screenedEmailsRepository;
        this.screenedEmailsMapper = screenedEmailsMapper;
    }

    @Override
    public ScreenedEmailsDTO save(ScreenedEmailsDTO screenedEmailsDTO) {
        log.debug("Request to save ScreenedEmails : {}", screenedEmailsDTO);
        ScreenedEmails screenedEmails = screenedEmailsMapper.toEntity(screenedEmailsDTO);
        screenedEmails = screenedEmailsRepository.save(screenedEmails);
        return screenedEmailsMapper.toDto(screenedEmails);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScreenedEmailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ScreenedEmails");
        return screenedEmailsRepository.findAll(pageable)
            .map(screenedEmailsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ScreenedEmailsDTO> findOne(Long id) {
        log.debug("Request to get ScreenedEmails : {}", id);
        return screenedEmailsRepository.findById(id)
            .map(screenedEmailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScreenedEmails : {}", id);
        screenedEmailsRepository.deleteById(id);
    }
}
