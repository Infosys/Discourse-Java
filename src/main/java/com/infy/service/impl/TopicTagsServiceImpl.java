/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicTagsService;
import com.infy.domain.TopicTags;
import com.infy.repository.TopicTagsRepository;
import com.infy.service.dto.TopicTagsDTO;
import com.infy.service.mapper.TopicTagsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicTags}.
 */
@Service
@Transactional
public class TopicTagsServiceImpl implements TopicTagsService {

    private final Logger log = LoggerFactory.getLogger(TopicTagsServiceImpl.class);

    private final TopicTagsRepository topicTagsRepository;

    private final TopicTagsMapper topicTagsMapper;

    public TopicTagsServiceImpl(TopicTagsRepository topicTagsRepository, TopicTagsMapper topicTagsMapper) {
        this.topicTagsRepository = topicTagsRepository;
        this.topicTagsMapper = topicTagsMapper;
    }

    @Override
    public TopicTagsDTO save(TopicTagsDTO topicTagsDTO) {
        log.debug("Request to save TopicTags : {}", topicTagsDTO);
        TopicTags topicTags = topicTagsMapper.toEntity(topicTagsDTO);
        topicTags = topicTagsRepository.save(topicTags);
        return topicTagsMapper.toDto(topicTags);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicTagsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicTags");
        return topicTagsRepository.findAll(pageable)
            .map(topicTagsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicTagsDTO> findOne(Long id) {
        log.debug("Request to get TopicTags : {}", id);
        return topicTagsRepository.findById(id)
            .map(topicTagsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicTags : {}", id);
        topicTagsRepository.deleteById(id);
    }
}
