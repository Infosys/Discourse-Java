/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicUsersService;
import com.infy.domain.TopicUsers;
import com.infy.repository.TopicUsersRepository;
import com.infy.service.dto.TopicUsersDTO;
import com.infy.service.mapper.TopicUsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicUsers}.
 */
@Service
@Transactional
public class TopicUsersServiceImpl implements TopicUsersService {

    private final Logger log = LoggerFactory.getLogger(TopicUsersServiceImpl.class);

    private final TopicUsersRepository topicUsersRepository;

    private final TopicUsersMapper topicUsersMapper;

    public TopicUsersServiceImpl(TopicUsersRepository topicUsersRepository, TopicUsersMapper topicUsersMapper) {
        this.topicUsersRepository = topicUsersRepository;
        this.topicUsersMapper = topicUsersMapper;
    }

    @Override
    public TopicUsersDTO save(TopicUsersDTO topicUsersDTO) {
        log.debug("Request to save TopicUsers : {}", topicUsersDTO);
        TopicUsers topicUsers = topicUsersMapper.toEntity(topicUsersDTO);
        topicUsers = topicUsersRepository.save(topicUsers);
        return topicUsersMapper.toDto(topicUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicUsers");
        return topicUsersRepository.findAll(pageable)
            .map(topicUsersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicUsersDTO> findOne(Long id) {
        log.debug("Request to get TopicUsers : {}", id);
        return topicUsersRepository.findById(id)
            .map(topicUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicUsers : {}", id);
        topicUsersRepository.deleteById(id);
    }
}
