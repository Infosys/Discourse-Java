/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicViewsService;
import com.infy.domain.TopicViews;
import com.infy.repository.TopicViewsRepository;
import com.infy.service.dto.TopicViewsDTO;
import com.infy.service.mapper.TopicViewsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicViews}.
 */
@Service
@Transactional
public class TopicViewsServiceImpl implements TopicViewsService {

    private final Logger log = LoggerFactory.getLogger(TopicViewsServiceImpl.class);

    private final TopicViewsRepository topicViewsRepository;

    private final TopicViewsMapper topicViewsMapper;

    public TopicViewsServiceImpl(TopicViewsRepository topicViewsRepository, TopicViewsMapper topicViewsMapper) {
        this.topicViewsRepository = topicViewsRepository;
        this.topicViewsMapper = topicViewsMapper;
    }

    @Override
    public TopicViewsDTO save(TopicViewsDTO topicViewsDTO) {
        log.debug("Request to save TopicViews : {}", topicViewsDTO);
        TopicViews topicViews = topicViewsMapper.toEntity(topicViewsDTO);
        topicViews = topicViewsRepository.save(topicViews);
        return topicViewsMapper.toDto(topicViews);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicViewsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicViews");
        return topicViewsRepository.findAll(pageable)
            .map(topicViewsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicViewsDTO> findOne(Long id) {
        log.debug("Request to get TopicViews : {}", id);
        return topicViewsRepository.findById(id)
            .map(topicViewsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicViews : {}", id);
        topicViewsRepository.deleteById(id);
    }
}
