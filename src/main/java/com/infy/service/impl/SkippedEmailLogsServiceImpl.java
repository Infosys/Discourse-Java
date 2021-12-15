/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.SkippedEmailLogsService;
import com.infy.domain.SkippedEmailLogs;
import com.infy.repository.SkippedEmailLogsRepository;
import com.infy.service.dto.SkippedEmailLogsDTO;
import com.infy.service.mapper.SkippedEmailLogsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SkippedEmailLogs}.
 */
@Service
@Transactional
public class SkippedEmailLogsServiceImpl implements SkippedEmailLogsService {

    private final Logger log = LoggerFactory.getLogger(SkippedEmailLogsServiceImpl.class);

    private final SkippedEmailLogsRepository skippedEmailLogsRepository;

    private final SkippedEmailLogsMapper skippedEmailLogsMapper;

    public SkippedEmailLogsServiceImpl(SkippedEmailLogsRepository skippedEmailLogsRepository, SkippedEmailLogsMapper skippedEmailLogsMapper) {
        this.skippedEmailLogsRepository = skippedEmailLogsRepository;
        this.skippedEmailLogsMapper = skippedEmailLogsMapper;
    }

    @Override
    public SkippedEmailLogsDTO save(SkippedEmailLogsDTO skippedEmailLogsDTO) {
        log.debug("Request to save SkippedEmailLogs : {}", skippedEmailLogsDTO);
        SkippedEmailLogs skippedEmailLogs = skippedEmailLogsMapper.toEntity(skippedEmailLogsDTO);
        skippedEmailLogs = skippedEmailLogsRepository.save(skippedEmailLogs);
        return skippedEmailLogsMapper.toDto(skippedEmailLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SkippedEmailLogsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SkippedEmailLogs");
        return skippedEmailLogsRepository.findAll(pageable)
            .map(skippedEmailLogsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SkippedEmailLogsDTO> findOne(Long id) {
        log.debug("Request to get SkippedEmailLogs : {}", id);
        return skippedEmailLogsRepository.findById(id)
            .map(skippedEmailLogsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SkippedEmailLogs : {}", id);
        skippedEmailLogsRepository.deleteById(id);
    }
}
