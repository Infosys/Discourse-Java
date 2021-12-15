/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicThumbnailsService;
import com.infy.domain.TopicThumbnails;
import com.infy.repository.TopicThumbnailsRepository;
import com.infy.service.dto.TopicThumbnailsDTO;
import com.infy.service.mapper.TopicThumbnailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicThumbnails}.
 */
@Service
@Transactional
public class TopicThumbnailsServiceImpl implements TopicThumbnailsService {

    private final Logger log = LoggerFactory.getLogger(TopicThumbnailsServiceImpl.class);

    private final TopicThumbnailsRepository topicThumbnailsRepository;

    private final TopicThumbnailsMapper topicThumbnailsMapper;

    public TopicThumbnailsServiceImpl(TopicThumbnailsRepository topicThumbnailsRepository, TopicThumbnailsMapper topicThumbnailsMapper) {
        this.topicThumbnailsRepository = topicThumbnailsRepository;
        this.topicThumbnailsMapper = topicThumbnailsMapper;
    }

    @Override
    public TopicThumbnailsDTO save(TopicThumbnailsDTO topicThumbnailsDTO) {
        log.debug("Request to save TopicThumbnails : {}", topicThumbnailsDTO);
        TopicThumbnails topicThumbnails = topicThumbnailsMapper.toEntity(topicThumbnailsDTO);
        topicThumbnails = topicThumbnailsRepository.save(topicThumbnails);
        return topicThumbnailsMapper.toDto(topicThumbnails);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicThumbnailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicThumbnails");
        return topicThumbnailsRepository.findAll(pageable)
            .map(topicThumbnailsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicThumbnailsDTO> findOne(Long id) {
        log.debug("Request to get TopicThumbnails : {}", id);
        return topicThumbnailsRepository.findById(id)
            .map(topicThumbnailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicThumbnails : {}", id);
        topicThumbnailsRepository.deleteById(id);
    }
}
