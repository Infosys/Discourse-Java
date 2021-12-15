/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.GroupTagNotificationDefaultsService;
import com.infy.domain.GroupTagNotificationDefaults;
import com.infy.repository.GroupTagNotificationDefaultsRepository;
import com.infy.service.dto.GroupTagNotificationDefaultsDTO;
import com.infy.service.mapper.GroupTagNotificationDefaultsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupTagNotificationDefaults}.
 */
@Service
@Transactional
public class GroupTagNotificationDefaultsServiceImpl implements GroupTagNotificationDefaultsService {

    private final Logger log = LoggerFactory.getLogger(GroupTagNotificationDefaultsServiceImpl.class);

    private final GroupTagNotificationDefaultsRepository groupTagNotificationDefaultsRepository;

    private final GroupTagNotificationDefaultsMapper groupTagNotificationDefaultsMapper;

    public GroupTagNotificationDefaultsServiceImpl(GroupTagNotificationDefaultsRepository groupTagNotificationDefaultsRepository, GroupTagNotificationDefaultsMapper groupTagNotificationDefaultsMapper) {
        this.groupTagNotificationDefaultsRepository = groupTagNotificationDefaultsRepository;
        this.groupTagNotificationDefaultsMapper = groupTagNotificationDefaultsMapper;
    }

    @Override
    public GroupTagNotificationDefaultsDTO save(GroupTagNotificationDefaultsDTO groupTagNotificationDefaultsDTO) {
        log.debug("Request to save GroupTagNotificationDefaults : {}", groupTagNotificationDefaultsDTO);
        GroupTagNotificationDefaults groupTagNotificationDefaults = groupTagNotificationDefaultsMapper.toEntity(groupTagNotificationDefaultsDTO);
        groupTagNotificationDefaults = groupTagNotificationDefaultsRepository.save(groupTagNotificationDefaults);
        return groupTagNotificationDefaultsMapper.toDto(groupTagNotificationDefaults);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupTagNotificationDefaultsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupTagNotificationDefaults");
        return groupTagNotificationDefaultsRepository.findAll(pageable)
            .map(groupTagNotificationDefaultsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GroupTagNotificationDefaultsDTO> findOne(Long id) {
        log.debug("Request to get GroupTagNotificationDefaults : {}", id);
        return groupTagNotificationDefaultsRepository.findById(id)
            .map(groupTagNotificationDefaultsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupTagNotificationDefaults : {}", id);
        groupTagNotificationDefaultsRepository.deleteById(id);
    }
}
