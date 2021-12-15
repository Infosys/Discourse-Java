/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicLinksService;
import com.infy.domain.TopicLinks;
import com.infy.repository.TopicLinksRepository;
import com.infy.service.dto.TopicLinksDTO;
import com.infy.service.mapper.TopicLinksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicLinks}.
 */
@Service
@Transactional
public class TopicLinksServiceImpl implements TopicLinksService {

    private final Logger log = LoggerFactory.getLogger(TopicLinksServiceImpl.class);

    private final TopicLinksRepository topicLinksRepository;

    private final TopicLinksMapper topicLinksMapper;

    public TopicLinksServiceImpl(TopicLinksRepository topicLinksRepository, TopicLinksMapper topicLinksMapper) {
        this.topicLinksRepository = topicLinksRepository;
        this.topicLinksMapper = topicLinksMapper;
    }

    @Override
    public TopicLinksDTO save(TopicLinksDTO topicLinksDTO) {
        log.debug("Request to save TopicLinks : {}", topicLinksDTO);
        TopicLinks topicLinks = topicLinksMapper.toEntity(topicLinksDTO);
        topicLinks = topicLinksRepository.save(topicLinks);
        return topicLinksMapper.toDto(topicLinks);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicLinksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicLinks");
        return topicLinksRepository.findAll(pageable)
            .map(topicLinksMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicLinksDTO> findOne(Long id) {
        log.debug("Request to get TopicLinks : {}", id);
        return topicLinksRepository.findById(id)
            .map(topicLinksMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicLinks : {}", id);
        topicLinksRepository.deleteById(id);
    }
}
