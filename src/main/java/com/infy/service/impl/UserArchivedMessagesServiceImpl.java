/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserArchivedMessagesService;
import com.infy.domain.UserArchivedMessages;
import com.infy.repository.UserArchivedMessagesRepository;
import com.infy.service.dto.UserArchivedMessagesDTO;
import com.infy.service.mapper.UserArchivedMessagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserArchivedMessages}.
 */
@Service
@Transactional
public class UserArchivedMessagesServiceImpl implements UserArchivedMessagesService {

    private final Logger log = LoggerFactory.getLogger(UserArchivedMessagesServiceImpl.class);

    private final UserArchivedMessagesRepository userArchivedMessagesRepository;

    private final UserArchivedMessagesMapper userArchivedMessagesMapper;

    public UserArchivedMessagesServiceImpl(UserArchivedMessagesRepository userArchivedMessagesRepository, UserArchivedMessagesMapper userArchivedMessagesMapper) {
        this.userArchivedMessagesRepository = userArchivedMessagesRepository;
        this.userArchivedMessagesMapper = userArchivedMessagesMapper;
    }

    @Override
    public UserArchivedMessagesDTO save(UserArchivedMessagesDTO userArchivedMessagesDTO) {
        log.debug("Request to save UserArchivedMessages : {}", userArchivedMessagesDTO);
        UserArchivedMessages userArchivedMessages = userArchivedMessagesMapper.toEntity(userArchivedMessagesDTO);
        userArchivedMessages = userArchivedMessagesRepository.save(userArchivedMessages);
        return userArchivedMessagesMapper.toDto(userArchivedMessages);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserArchivedMessagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserArchivedMessages");
        return userArchivedMessagesRepository.findAll(pageable)
            .map(userArchivedMessagesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserArchivedMessagesDTO> findOne(Long id) {
        log.debug("Request to get UserArchivedMessages : {}", id);
        return userArchivedMessagesRepository.findById(id)
            .map(userArchivedMessagesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserArchivedMessages : {}", id);
        userArchivedMessagesRepository.deleteById(id);
    }
}
