/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicAllowedUsersService;
import com.infy.domain.TopicAllowedUsers;
import com.infy.repository.TopicAllowedUsersRepository;
import com.infy.service.dto.TopicAllowedUsersDTO;
import com.infy.service.mapper.TopicAllowedUsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicAllowedUsers}.
 */
@Service
@Transactional
public class TopicAllowedUsersServiceImpl implements TopicAllowedUsersService {

    private final Logger log = LoggerFactory.getLogger(TopicAllowedUsersServiceImpl.class);

    private final TopicAllowedUsersRepository topicAllowedUsersRepository;

    private final TopicAllowedUsersMapper topicAllowedUsersMapper;

    public TopicAllowedUsersServiceImpl(TopicAllowedUsersRepository topicAllowedUsersRepository, TopicAllowedUsersMapper topicAllowedUsersMapper) {
        this.topicAllowedUsersRepository = topicAllowedUsersRepository;
        this.topicAllowedUsersMapper = topicAllowedUsersMapper;
    }

    @Override
    public TopicAllowedUsersDTO save(TopicAllowedUsersDTO topicAllowedUsersDTO) {
        log.debug("Request to save TopicAllowedUsers : {}", topicAllowedUsersDTO);
        TopicAllowedUsers topicAllowedUsers = topicAllowedUsersMapper.toEntity(topicAllowedUsersDTO);
        topicAllowedUsers = topicAllowedUsersRepository.save(topicAllowedUsers);
        return topicAllowedUsersMapper.toDto(topicAllowedUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicAllowedUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicAllowedUsers");
        return topicAllowedUsersRepository.findAll(pageable)
            .map(topicAllowedUsersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicAllowedUsersDTO> findOne(Long id) {
        log.debug("Request to get TopicAllowedUsers : {}", id);
        return topicAllowedUsersRepository.findById(id)
            .map(topicAllowedUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicAllowedUsers : {}", id);
        topicAllowedUsersRepository.deleteById(id);
    }
}
