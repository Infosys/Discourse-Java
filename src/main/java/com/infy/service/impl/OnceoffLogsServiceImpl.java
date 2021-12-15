/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.OnceoffLogsService;
import com.infy.domain.OnceoffLogs;
import com.infy.repository.OnceoffLogsRepository;
import com.infy.service.dto.OnceoffLogsDTO;
import com.infy.service.mapper.OnceoffLogsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OnceoffLogs}.
 */
@Service
@Transactional
public class OnceoffLogsServiceImpl implements OnceoffLogsService {

    private final Logger log = LoggerFactory.getLogger(OnceoffLogsServiceImpl.class);

    private final OnceoffLogsRepository onceoffLogsRepository;

    private final OnceoffLogsMapper onceoffLogsMapper;

    public OnceoffLogsServiceImpl(OnceoffLogsRepository onceoffLogsRepository, OnceoffLogsMapper onceoffLogsMapper) {
        this.onceoffLogsRepository = onceoffLogsRepository;
        this.onceoffLogsMapper = onceoffLogsMapper;
    }

    @Override
    public OnceoffLogsDTO save(OnceoffLogsDTO onceoffLogsDTO) {
        log.debug("Request to save OnceoffLogs : {}", onceoffLogsDTO);
        OnceoffLogs onceoffLogs = onceoffLogsMapper.toEntity(onceoffLogsDTO);
        onceoffLogs = onceoffLogsRepository.save(onceoffLogs);
        return onceoffLogsMapper.toDto(onceoffLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OnceoffLogsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OnceoffLogs");
        return onceoffLogsRepository.findAll(pageable)
            .map(onceoffLogsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OnceoffLogsDTO> findOne(Long id) {
        log.debug("Request to get OnceoffLogs : {}", id);
        return onceoffLogsRepository.findById(id)
            .map(onceoffLogsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OnceoffLogs : {}", id);
        onceoffLogsRepository.deleteById(id);
    }
}
