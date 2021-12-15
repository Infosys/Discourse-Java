/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.BackupDraftTopicsService;
import com.infy.domain.BackupDraftTopics;
import com.infy.repository.BackupDraftTopicsRepository;
import com.infy.service.dto.BackupDraftTopicsDTO;
import com.infy.service.mapper.BackupDraftTopicsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BackupDraftTopics}.
 */
@Service
@Transactional
public class BackupDraftTopicsServiceImpl implements BackupDraftTopicsService {

    private final Logger log = LoggerFactory.getLogger(BackupDraftTopicsServiceImpl.class);

    private final BackupDraftTopicsRepository backupDraftTopicsRepository;

    private final BackupDraftTopicsMapper backupDraftTopicsMapper;

    public BackupDraftTopicsServiceImpl(BackupDraftTopicsRepository backupDraftTopicsRepository, BackupDraftTopicsMapper backupDraftTopicsMapper) {
        this.backupDraftTopicsRepository = backupDraftTopicsRepository;
        this.backupDraftTopicsMapper = backupDraftTopicsMapper;
    }

    @Override
    public BackupDraftTopicsDTO save(BackupDraftTopicsDTO backupDraftTopicsDTO) {
        log.debug("Request to save BackupDraftTopics : {}", backupDraftTopicsDTO);
        BackupDraftTopics backupDraftTopics = backupDraftTopicsMapper.toEntity(backupDraftTopicsDTO);
        backupDraftTopics = backupDraftTopicsRepository.save(backupDraftTopics);
        return backupDraftTopicsMapper.toDto(backupDraftTopics);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BackupDraftTopicsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BackupDraftTopics");
        return backupDraftTopicsRepository.findAll(pageable)
            .map(backupDraftTopicsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BackupDraftTopicsDTO> findOne(Long id) {
        log.debug("Request to get BackupDraftTopics : {}", id);
        return backupDraftTopicsRepository.findById(id)
            .map(backupDraftTopicsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BackupDraftTopics : {}", id);
        backupDraftTopicsRepository.deleteById(id);
    }
}
