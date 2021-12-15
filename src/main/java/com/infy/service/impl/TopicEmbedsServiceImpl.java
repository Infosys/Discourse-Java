/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicEmbedsService;
import com.infy.domain.TopicEmbeds;
import com.infy.repository.TopicEmbedsRepository;
import com.infy.service.dto.TopicEmbedsDTO;
import com.infy.service.mapper.TopicEmbedsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicEmbeds}.
 */
@Service
@Transactional
public class TopicEmbedsServiceImpl implements TopicEmbedsService {

    private final Logger log = LoggerFactory.getLogger(TopicEmbedsServiceImpl.class);

    private final TopicEmbedsRepository topicEmbedsRepository;

    private final TopicEmbedsMapper topicEmbedsMapper;

    public TopicEmbedsServiceImpl(TopicEmbedsRepository topicEmbedsRepository, TopicEmbedsMapper topicEmbedsMapper) {
        this.topicEmbedsRepository = topicEmbedsRepository;
        this.topicEmbedsMapper = topicEmbedsMapper;
    }

    @Override
    public TopicEmbedsDTO save(TopicEmbedsDTO topicEmbedsDTO) {
        log.debug("Request to save TopicEmbeds : {}", topicEmbedsDTO);
        TopicEmbeds topicEmbeds = topicEmbedsMapper.toEntity(topicEmbedsDTO);
        topicEmbeds = topicEmbedsRepository.save(topicEmbeds);
        return topicEmbedsMapper.toDto(topicEmbeds);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicEmbedsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicEmbeds");
        return topicEmbedsRepository.findAll(pageable)
            .map(topicEmbedsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicEmbedsDTO> findOne(Long id) {
        log.debug("Request to get TopicEmbeds : {}", id);
        return topicEmbedsRepository.findById(id)
            .map(topicEmbedsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicEmbeds : {}", id);
        topicEmbedsRepository.deleteById(id);
    }
}
