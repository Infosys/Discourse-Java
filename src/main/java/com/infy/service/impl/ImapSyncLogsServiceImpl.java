/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ImapSyncLogsService;
import com.infy.domain.ImapSyncLogs;
import com.infy.repository.ImapSyncLogsRepository;
import com.infy.service.dto.ImapSyncLogsDTO;
import com.infy.service.mapper.ImapSyncLogsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ImapSyncLogs}.
 */
@Service
@Transactional
public class ImapSyncLogsServiceImpl implements ImapSyncLogsService {

    private final Logger log = LoggerFactory.getLogger(ImapSyncLogsServiceImpl.class);

    private final ImapSyncLogsRepository imapSyncLogsRepository;

    private final ImapSyncLogsMapper imapSyncLogsMapper;

    public ImapSyncLogsServiceImpl(ImapSyncLogsRepository imapSyncLogsRepository, ImapSyncLogsMapper imapSyncLogsMapper) {
        this.imapSyncLogsRepository = imapSyncLogsRepository;
        this.imapSyncLogsMapper = imapSyncLogsMapper;
    }

    @Override
    public ImapSyncLogsDTO save(ImapSyncLogsDTO imapSyncLogsDTO) {
        log.debug("Request to save ImapSyncLogs : {}", imapSyncLogsDTO);
        ImapSyncLogs imapSyncLogs = imapSyncLogsMapper.toEntity(imapSyncLogsDTO);
        imapSyncLogs = imapSyncLogsRepository.save(imapSyncLogs);
        return imapSyncLogsMapper.toDto(imapSyncLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImapSyncLogsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ImapSyncLogs");
        return imapSyncLogsRepository.findAll(pageable)
            .map(imapSyncLogsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ImapSyncLogsDTO> findOne(Long id) {
        log.debug("Request to get ImapSyncLogs : {}", id);
        return imapSyncLogsRepository.findById(id)
            .map(imapSyncLogsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImapSyncLogs : {}", id);
        imapSyncLogsRepository.deleteById(id);
    }
}
