/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicInvitesService;
import com.infy.domain.TopicInvites;
import com.infy.repository.TopicInvitesRepository;
import com.infy.service.dto.TopicInvitesDTO;
import com.infy.service.mapper.TopicInvitesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicInvites}.
 */
@Service
@Transactional
public class TopicInvitesServiceImpl implements TopicInvitesService {

    private final Logger log = LoggerFactory.getLogger(TopicInvitesServiceImpl.class);

    private final TopicInvitesRepository topicInvitesRepository;

    private final TopicInvitesMapper topicInvitesMapper;

    public TopicInvitesServiceImpl(TopicInvitesRepository topicInvitesRepository, TopicInvitesMapper topicInvitesMapper) {
        this.topicInvitesRepository = topicInvitesRepository;
        this.topicInvitesMapper = topicInvitesMapper;
    }

    @Override
    public TopicInvitesDTO save(TopicInvitesDTO topicInvitesDTO) {
        log.debug("Request to save TopicInvites : {}", topicInvitesDTO);
        TopicInvites topicInvites = topicInvitesMapper.toEntity(topicInvitesDTO);
        topicInvites = topicInvitesRepository.save(topicInvites);
        return topicInvitesMapper.toDto(topicInvites);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicInvitesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicInvites");
        return topicInvitesRepository.findAll(pageable)
            .map(topicInvitesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicInvitesDTO> findOne(Long id) {
        log.debug("Request to get TopicInvites : {}", id);
        return topicInvitesRepository.findById(id)
            .map(topicInvitesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicInvites : {}", id);
        topicInvitesRepository.deleteById(id);
    }
}
