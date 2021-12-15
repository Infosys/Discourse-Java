/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicAllowedGroupsService;
import com.infy.domain.TopicAllowedGroups;
import com.infy.repository.TopicAllowedGroupsRepository;
import com.infy.service.dto.TopicAllowedGroupsDTO;
import com.infy.service.mapper.TopicAllowedGroupsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicAllowedGroups}.
 */
@Service
@Transactional
public class TopicAllowedGroupsServiceImpl implements TopicAllowedGroupsService {

    private final Logger log = LoggerFactory.getLogger(TopicAllowedGroupsServiceImpl.class);

    private final TopicAllowedGroupsRepository topicAllowedGroupsRepository;

    private final TopicAllowedGroupsMapper topicAllowedGroupsMapper;

    public TopicAllowedGroupsServiceImpl(TopicAllowedGroupsRepository topicAllowedGroupsRepository, TopicAllowedGroupsMapper topicAllowedGroupsMapper) {
        this.topicAllowedGroupsRepository = topicAllowedGroupsRepository;
        this.topicAllowedGroupsMapper = topicAllowedGroupsMapper;
    }

    @Override
    public TopicAllowedGroupsDTO save(TopicAllowedGroupsDTO topicAllowedGroupsDTO) {
        log.debug("Request to save TopicAllowedGroups : {}", topicAllowedGroupsDTO);
        TopicAllowedGroups topicAllowedGroups = topicAllowedGroupsMapper.toEntity(topicAllowedGroupsDTO);
        topicAllowedGroups = topicAllowedGroupsRepository.save(topicAllowedGroups);
        return topicAllowedGroupsMapper.toDto(topicAllowedGroups);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicAllowedGroupsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicAllowedGroups");
        return topicAllowedGroupsRepository.findAll(pageable)
            .map(topicAllowedGroupsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicAllowedGroupsDTO> findOne(Long id) {
        log.debug("Request to get TopicAllowedGroups : {}", id);
        return topicAllowedGroupsRepository.findById(id)
            .map(topicAllowedGroupsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicAllowedGroups : {}", id);
        topicAllowedGroupsRepository.deleteById(id);
    }
}
