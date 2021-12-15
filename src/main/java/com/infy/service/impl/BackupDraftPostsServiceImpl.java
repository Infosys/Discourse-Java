/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.BackupDraftPostsService;
import com.infy.domain.BackupDraftPosts;
import com.infy.repository.BackupDraftPostsRepository;
import com.infy.service.dto.BackupDraftPostsDTO;
import com.infy.service.mapper.BackupDraftPostsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BackupDraftPosts}.
 */
@Service
@Transactional
public class BackupDraftPostsServiceImpl implements BackupDraftPostsService {

    private final Logger log = LoggerFactory.getLogger(BackupDraftPostsServiceImpl.class);

    private final BackupDraftPostsRepository backupDraftPostsRepository;

    private final BackupDraftPostsMapper backupDraftPostsMapper;

    public BackupDraftPostsServiceImpl(BackupDraftPostsRepository backupDraftPostsRepository, BackupDraftPostsMapper backupDraftPostsMapper) {
        this.backupDraftPostsRepository = backupDraftPostsRepository;
        this.backupDraftPostsMapper = backupDraftPostsMapper;
    }

    @Override
    public BackupDraftPostsDTO save(BackupDraftPostsDTO backupDraftPostsDTO) {
        log.debug("Request to save BackupDraftPosts : {}", backupDraftPostsDTO);
        BackupDraftPosts backupDraftPosts = backupDraftPostsMapper.toEntity(backupDraftPostsDTO);
        backupDraftPosts = backupDraftPostsRepository.save(backupDraftPosts);
        return backupDraftPostsMapper.toDto(backupDraftPosts);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BackupDraftPostsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BackupDraftPosts");
        return backupDraftPostsRepository.findAll(pageable)
            .map(backupDraftPostsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BackupDraftPostsDTO> findOne(Long id) {
        log.debug("Request to get BackupDraftPosts : {}", id);
        return backupDraftPostsRepository.findById(id)
            .map(backupDraftPostsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BackupDraftPosts : {}", id);
        backupDraftPostsRepository.deleteById(id);
    }
}
