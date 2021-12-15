/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicTimersService;
import com.infy.domain.TopicTimers;
import com.infy.repository.TopicTimersRepository;
import com.infy.service.dto.TopicTimersDTO;
import com.infy.service.mapper.TopicTimersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicTimers}.
 */
@Service
@Transactional
public class TopicTimersServiceImpl implements TopicTimersService {

    private final Logger log = LoggerFactory.getLogger(TopicTimersServiceImpl.class);

    private final TopicTimersRepository topicTimersRepository;

    private final TopicTimersMapper topicTimersMapper;

    public TopicTimersServiceImpl(TopicTimersRepository topicTimersRepository, TopicTimersMapper topicTimersMapper) {
        this.topicTimersRepository = topicTimersRepository;
        this.topicTimersMapper = topicTimersMapper;
    }

    @Override
    public TopicTimersDTO save(TopicTimersDTO topicTimersDTO) {
        log.debug("Request to save TopicTimers : {}", topicTimersDTO);
        TopicTimers topicTimers = topicTimersMapper.toEntity(topicTimersDTO);
        topicTimers = topicTimersRepository.save(topicTimers);
        return topicTimersMapper.toDto(topicTimers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicTimersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicTimers");
        return topicTimersRepository.findAll(pageable)
            .map(topicTimersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicTimersDTO> findOne(Long id) {
        log.debug("Request to get TopicTimers : {}", id);
        return topicTimersRepository.findById(id)
            .map(topicTimersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicTimers : {}", id);
        topicTimersRepository.deleteById(id);
    }
}
