/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.BackupMetadataService;
import com.infy.domain.BackupMetadata;
import com.infy.repository.BackupMetadataRepository;
import com.infy.service.dto.BackupMetadataDTO;
import com.infy.service.mapper.BackupMetadataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BackupMetadata}.
 */
@Service
@Transactional
public class BackupMetadataServiceImpl implements BackupMetadataService {

    private final Logger log = LoggerFactory.getLogger(BackupMetadataServiceImpl.class);

    private final BackupMetadataRepository backupMetadataRepository;

    private final BackupMetadataMapper backupMetadataMapper;

    public BackupMetadataServiceImpl(BackupMetadataRepository backupMetadataRepository, BackupMetadataMapper backupMetadataMapper) {
        this.backupMetadataRepository = backupMetadataRepository;
        this.backupMetadataMapper = backupMetadataMapper;
    }

    @Override
    public BackupMetadataDTO save(BackupMetadataDTO backupMetadataDTO) {
        log.debug("Request to save BackupMetadata : {}", backupMetadataDTO);
        BackupMetadata backupMetadata = backupMetadataMapper.toEntity(backupMetadataDTO);
        backupMetadata = backupMetadataRepository.save(backupMetadata);
        return backupMetadataMapper.toDto(backupMetadata);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BackupMetadataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BackupMetadata");
        return backupMetadataRepository.findAll(pageable)
            .map(backupMetadataMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BackupMetadataDTO> findOne(Long id) {
        log.debug("Request to get BackupMetadata : {}", id);
        return backupMetadataRepository.findById(id)
            .map(backupMetadataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BackupMetadata : {}", id);
        backupMetadataRepository.deleteById(id);
    }
}
