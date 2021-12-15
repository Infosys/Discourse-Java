/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopTopicsService;
import com.infy.domain.TopTopics;
import com.infy.repository.TopTopicsRepository;
import com.infy.service.dto.TopTopicsDTO;
import com.infy.service.mapper.TopTopicsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopTopics}.
 */
@Service
@Transactional
public class TopTopicsServiceImpl implements TopTopicsService {

    private final Logger log = LoggerFactory.getLogger(TopTopicsServiceImpl.class);

    private final TopTopicsRepository topTopicsRepository;

    private final TopTopicsMapper topTopicsMapper;

    public TopTopicsServiceImpl(TopTopicsRepository topTopicsRepository, TopTopicsMapper topTopicsMapper) {
        this.topTopicsRepository = topTopicsRepository;
        this.topTopicsMapper = topTopicsMapper;
    }

    @Override
    public TopTopicsDTO save(TopTopicsDTO topTopicsDTO) {
        log.debug("Request to save TopTopics : {}", topTopicsDTO);
        TopTopics topTopics = topTopicsMapper.toEntity(topTopicsDTO);
        topTopics = topTopicsRepository.save(topTopics);
        return topTopicsMapper.toDto(topTopics);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopTopicsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopTopics");
        return topTopicsRepository.findAll(pageable)
            .map(topTopicsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopTopicsDTO> findOne(Long id) {
        log.debug("Request to get TopTopics : {}", id);
        return topTopicsRepository.findById(id)
            .map(topTopicsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopTopics : {}", id);
        topTopicsRepository.deleteById(id);
    }
}
