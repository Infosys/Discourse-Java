/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.GroupMentionsService;
import com.infy.domain.GroupMentions;
import com.infy.repository.GroupMentionsRepository;
import com.infy.service.dto.GroupMentionsDTO;
import com.infy.service.mapper.GroupMentionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupMentions}.
 */
@Service
@Transactional
public class GroupMentionsServiceImpl implements GroupMentionsService {

    private final Logger log = LoggerFactory.getLogger(GroupMentionsServiceImpl.class);

    private final GroupMentionsRepository groupMentionsRepository;

    private final GroupMentionsMapper groupMentionsMapper;

    public GroupMentionsServiceImpl(GroupMentionsRepository groupMentionsRepository, GroupMentionsMapper groupMentionsMapper) {
        this.groupMentionsRepository = groupMentionsRepository;
        this.groupMentionsMapper = groupMentionsMapper;
    }

    @Override
    public GroupMentionsDTO save(GroupMentionsDTO groupMentionsDTO) {
        log.debug("Request to save GroupMentions : {}", groupMentionsDTO);
        GroupMentions groupMentions = groupMentionsMapper.toEntity(groupMentionsDTO);
        groupMentions = groupMentionsRepository.save(groupMentions);
        return groupMentionsMapper.toDto(groupMentions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupMentionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupMentions");
        return groupMentionsRepository.findAll(pageable)
            .map(groupMentionsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GroupMentionsDTO> findOne(Long id) {
        log.debug("Request to get GroupMentions : {}", id);
        return groupMentionsRepository.findById(id)
            .map(groupMentionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupMentions : {}", id);
        groupMentionsRepository.deleteById(id);
    }
}
