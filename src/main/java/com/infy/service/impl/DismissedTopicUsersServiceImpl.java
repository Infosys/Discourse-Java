/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.DismissedTopicUsersService;
import com.infy.domain.DismissedTopicUsers;
import com.infy.repository.DismissedTopicUsersRepository;
import com.infy.service.dto.DismissedTopicUsersDTO;
import com.infy.service.mapper.DismissedTopicUsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DismissedTopicUsers}.
 */
@Service
@Transactional
public class DismissedTopicUsersServiceImpl implements DismissedTopicUsersService {

    private final Logger log = LoggerFactory.getLogger(DismissedTopicUsersServiceImpl.class);

    private final DismissedTopicUsersRepository dismissedTopicUsersRepository;

    private final DismissedTopicUsersMapper dismissedTopicUsersMapper;

    public DismissedTopicUsersServiceImpl(DismissedTopicUsersRepository dismissedTopicUsersRepository, DismissedTopicUsersMapper dismissedTopicUsersMapper) {
        this.dismissedTopicUsersRepository = dismissedTopicUsersRepository;
        this.dismissedTopicUsersMapper = dismissedTopicUsersMapper;
    }

    @Override
    public DismissedTopicUsersDTO save(DismissedTopicUsersDTO dismissedTopicUsersDTO) {
        log.debug("Request to save DismissedTopicUsers : {}", dismissedTopicUsersDTO);
        DismissedTopicUsers dismissedTopicUsers = dismissedTopicUsersMapper.toEntity(dismissedTopicUsersDTO);
        dismissedTopicUsers = dismissedTopicUsersRepository.save(dismissedTopicUsers);
        return dismissedTopicUsersMapper.toDto(dismissedTopicUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DismissedTopicUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DismissedTopicUsers");
        return dismissedTopicUsersRepository.findAll(pageable)
            .map(dismissedTopicUsersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DismissedTopicUsersDTO> findOne(Long id) {
        log.debug("Request to get DismissedTopicUsers : {}", id);
        return dismissedTopicUsersRepository.findById(id)
            .map(dismissedTopicUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DismissedTopicUsers : {}", id);
        dismissedTopicUsersRepository.deleteById(id);
    }
}
