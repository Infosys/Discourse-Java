/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.GroupCategoryNotificationDefaultsService;
import com.infy.domain.GroupCategoryNotificationDefaults;
import com.infy.repository.GroupCategoryNotificationDefaultsRepository;
import com.infy.service.dto.GroupCategoryNotificationDefaultsDTO;
import com.infy.service.mapper.GroupCategoryNotificationDefaultsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupCategoryNotificationDefaults}.
 */
@Service
@Transactional
public class GroupCategoryNotificationDefaultsServiceImpl implements GroupCategoryNotificationDefaultsService {

    private final Logger log = LoggerFactory.getLogger(GroupCategoryNotificationDefaultsServiceImpl.class);

    private final GroupCategoryNotificationDefaultsRepository groupCategoryNotificationDefaultsRepository;

    private final GroupCategoryNotificationDefaultsMapper groupCategoryNotificationDefaultsMapper;

    public GroupCategoryNotificationDefaultsServiceImpl(GroupCategoryNotificationDefaultsRepository groupCategoryNotificationDefaultsRepository, GroupCategoryNotificationDefaultsMapper groupCategoryNotificationDefaultsMapper) {
        this.groupCategoryNotificationDefaultsRepository = groupCategoryNotificationDefaultsRepository;
        this.groupCategoryNotificationDefaultsMapper = groupCategoryNotificationDefaultsMapper;
    }

    @Override
    public GroupCategoryNotificationDefaultsDTO save(GroupCategoryNotificationDefaultsDTO groupCategoryNotificationDefaultsDTO) {
        log.debug("Request to save GroupCategoryNotificationDefaults : {}", groupCategoryNotificationDefaultsDTO);
        GroupCategoryNotificationDefaults groupCategoryNotificationDefaults = groupCategoryNotificationDefaultsMapper.toEntity(groupCategoryNotificationDefaultsDTO);
        groupCategoryNotificationDefaults = groupCategoryNotificationDefaultsRepository.save(groupCategoryNotificationDefaults);
        return groupCategoryNotificationDefaultsMapper.toDto(groupCategoryNotificationDefaults);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupCategoryNotificationDefaultsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupCategoryNotificationDefaults");
        return groupCategoryNotificationDefaultsRepository.findAll(pageable)
            .map(groupCategoryNotificationDefaultsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GroupCategoryNotificationDefaultsDTO> findOne(Long id) {
        log.debug("Request to get GroupCategoryNotificationDefaults : {}", id);
        return groupCategoryNotificationDefaultsRepository.findById(id)
            .map(groupCategoryNotificationDefaultsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupCategoryNotificationDefaults : {}", id);
        groupCategoryNotificationDefaultsRepository.deleteById(id);
    }
}
