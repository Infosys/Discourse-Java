/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicLinkClicksService;
import com.infy.domain.TopicLinkClicks;
import com.infy.repository.TopicLinkClicksRepository;
import com.infy.service.dto.TopicLinkClicksDTO;
import com.infy.service.mapper.TopicLinkClicksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicLinkClicks}.
 */
@Service
@Transactional
public class TopicLinkClicksServiceImpl implements TopicLinkClicksService {

    private final Logger log = LoggerFactory.getLogger(TopicLinkClicksServiceImpl.class);

    private final TopicLinkClicksRepository topicLinkClicksRepository;

    private final TopicLinkClicksMapper topicLinkClicksMapper;

    public TopicLinkClicksServiceImpl(TopicLinkClicksRepository topicLinkClicksRepository, TopicLinkClicksMapper topicLinkClicksMapper) {
        this.topicLinkClicksRepository = topicLinkClicksRepository;
        this.topicLinkClicksMapper = topicLinkClicksMapper;
    }

    @Override
    public TopicLinkClicksDTO save(TopicLinkClicksDTO topicLinkClicksDTO) {
        log.debug("Request to save TopicLinkClicks : {}", topicLinkClicksDTO);
        TopicLinkClicks topicLinkClicks = topicLinkClicksMapper.toEntity(topicLinkClicksDTO);
        topicLinkClicks = topicLinkClicksRepository.save(topicLinkClicks);
        return topicLinkClicksMapper.toDto(topicLinkClicks);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicLinkClicksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicLinkClicks");
        return topicLinkClicksRepository.findAll(pageable)
            .map(topicLinkClicksMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicLinkClicksDTO> findOne(Long id) {
        log.debug("Request to get TopicLinkClicks : {}", id);
        return topicLinkClicksRepository.findById(id)
            .map(topicLinkClicksMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicLinkClicks : {}", id);
        topicLinkClicksRepository.deleteById(id);
    }
}
