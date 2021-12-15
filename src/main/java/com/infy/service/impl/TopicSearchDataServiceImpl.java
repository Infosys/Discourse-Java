/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicSearchDataService;
import com.infy.domain.TopicSearchData;
import com.infy.repository.TopicSearchDataRepository;
import com.infy.service.dto.TopicSearchDataDTO;
import com.infy.service.mapper.TopicSearchDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicSearchData}.
 */
@Service
@Transactional
public class TopicSearchDataServiceImpl implements TopicSearchDataService {

    private final Logger log = LoggerFactory.getLogger(TopicSearchDataServiceImpl.class);

    private final TopicSearchDataRepository topicSearchDataRepository;

    private final TopicSearchDataMapper topicSearchDataMapper;

    public TopicSearchDataServiceImpl(TopicSearchDataRepository topicSearchDataRepository, TopicSearchDataMapper topicSearchDataMapper) {
        this.topicSearchDataRepository = topicSearchDataRepository;
        this.topicSearchDataMapper = topicSearchDataMapper;
    }

    @Override
    public TopicSearchDataDTO save(TopicSearchDataDTO topicSearchDataDTO) {
        log.debug("Request to save TopicSearchData : {}", topicSearchDataDTO);
        TopicSearchData topicSearchData = topicSearchDataMapper.toEntity(topicSearchDataDTO);
        topicSearchData = topicSearchDataRepository.save(topicSearchData);
        return topicSearchDataMapper.toDto(topicSearchData);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicSearchDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicSearchData");
        return topicSearchDataRepository.findAll(pageable)
            .map(topicSearchDataMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicSearchDataDTO> findOne(Long id) {
        log.debug("Request to get TopicSearchData : {}", id);
        return topicSearchDataRepository.findById(id)
            .map(topicSearchDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicSearchData : {}", id);
        topicSearchDataRepository.deleteById(id);
    }
}
