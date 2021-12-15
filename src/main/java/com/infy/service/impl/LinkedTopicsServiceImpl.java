/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.LinkedTopicsService;
import com.infy.domain.LinkedTopics;
import com.infy.repository.LinkedTopicsRepository;
import com.infy.service.dto.LinkedTopicsDTO;
import com.infy.service.mapper.LinkedTopicsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LinkedTopics}.
 */
@Service
@Transactional
public class LinkedTopicsServiceImpl implements LinkedTopicsService {

    private final Logger log = LoggerFactory.getLogger(LinkedTopicsServiceImpl.class);

    private final LinkedTopicsRepository linkedTopicsRepository;

    private final LinkedTopicsMapper linkedTopicsMapper;

    public LinkedTopicsServiceImpl(LinkedTopicsRepository linkedTopicsRepository, LinkedTopicsMapper linkedTopicsMapper) {
        this.linkedTopicsRepository = linkedTopicsRepository;
        this.linkedTopicsMapper = linkedTopicsMapper;
    }

    @Override
    public LinkedTopicsDTO save(LinkedTopicsDTO linkedTopicsDTO) {
        log.debug("Request to save LinkedTopics : {}", linkedTopicsDTO);
        LinkedTopics linkedTopics = linkedTopicsMapper.toEntity(linkedTopicsDTO);
        linkedTopics = linkedTopicsRepository.save(linkedTopics);
        return linkedTopicsMapper.toDto(linkedTopics);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LinkedTopicsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LinkedTopics");
        return linkedTopicsRepository.findAll(pageable)
            .map(linkedTopicsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LinkedTopicsDTO> findOne(Long id) {
        log.debug("Request to get LinkedTopics : {}", id);
        return linkedTopicsRepository.findById(id)
            .map(linkedTopicsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LinkedTopics : {}", id);
        linkedTopicsRepository.deleteById(id);
    }
}
