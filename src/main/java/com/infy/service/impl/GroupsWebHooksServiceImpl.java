/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.GroupsWebHooksService;
import com.infy.domain.GroupsWebHooks;
import com.infy.repository.GroupsWebHooksRepository;
import com.infy.service.dto.GroupsWebHooksDTO;
import com.infy.service.mapper.GroupsWebHooksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupsWebHooks}.
 */
@Service
@Transactional
public class GroupsWebHooksServiceImpl implements GroupsWebHooksService {

    private final Logger log = LoggerFactory.getLogger(GroupsWebHooksServiceImpl.class);

    private final GroupsWebHooksRepository groupsWebHooksRepository;

    private final GroupsWebHooksMapper groupsWebHooksMapper;

    public GroupsWebHooksServiceImpl(GroupsWebHooksRepository groupsWebHooksRepository, GroupsWebHooksMapper groupsWebHooksMapper) {
        this.groupsWebHooksRepository = groupsWebHooksRepository;
        this.groupsWebHooksMapper = groupsWebHooksMapper;
    }

    @Override
    public GroupsWebHooksDTO save(GroupsWebHooksDTO groupsWebHooksDTO) {
        log.debug("Request to save GroupsWebHooks : {}", groupsWebHooksDTO);
        GroupsWebHooks groupsWebHooks = groupsWebHooksMapper.toEntity(groupsWebHooksDTO);
        groupsWebHooks = groupsWebHooksRepository.save(groupsWebHooks);
        return groupsWebHooksMapper.toDto(groupsWebHooks);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupsWebHooksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupsWebHooks");
        return groupsWebHooksRepository.findAll(pageable)
            .map(groupsWebHooksMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GroupsWebHooksDTO> findOne(Long id) {
        log.debug("Request to get GroupsWebHooks : {}", id);
        return groupsWebHooksRepository.findById(id)
            .map(groupsWebHooksMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupsWebHooks : {}", id);
        groupsWebHooksRepository.deleteById(id);
    }
}
