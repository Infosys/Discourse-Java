/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicGroupsService;
import com.infy.domain.TopicGroups;
import com.infy.repository.TopicGroupsRepository;
import com.infy.service.dto.TopicGroupsDTO;
import com.infy.service.mapper.TopicGroupsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicGroups}.
 */
@Service
@Transactional
public class TopicGroupsServiceImpl implements TopicGroupsService {

    private final Logger log = LoggerFactory.getLogger(TopicGroupsServiceImpl.class);

    private final TopicGroupsRepository topicGroupsRepository;

    private final TopicGroupsMapper topicGroupsMapper;

    public TopicGroupsServiceImpl(TopicGroupsRepository topicGroupsRepository, TopicGroupsMapper topicGroupsMapper) {
        this.topicGroupsRepository = topicGroupsRepository;
        this.topicGroupsMapper = topicGroupsMapper;
    }

    @Override
    public TopicGroupsDTO save(TopicGroupsDTO topicGroupsDTO) {
        log.debug("Request to save TopicGroups : {}", topicGroupsDTO);
        TopicGroups topicGroups = topicGroupsMapper.toEntity(topicGroupsDTO);
        topicGroups = topicGroupsRepository.save(topicGroups);
        return topicGroupsMapper.toDto(topicGroups);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicGroupsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicGroups");
        return topicGroupsRepository.findAll(pageable)
            .map(topicGroupsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicGroupsDTO> findOne(Long id) {
        log.debug("Request to get TopicGroups : {}", id);
        return topicGroupsRepository.findById(id)
            .map(topicGroupsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicGroups : {}", id);
        topicGroupsRepository.deleteById(id);
    }
}
